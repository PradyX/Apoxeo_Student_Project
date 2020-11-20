package com.prady.apoxeostudentproject.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prady.apoxeostudentproject.activities.MainActivity;
import com.prady.apoxeostudentproject.R;
import com.prady.apoxeostudentproject.db.DBAdapter;
import com.prady.apoxeostudentproject.db.DBViewHolder;

public class HomeFragment extends Fragment {

    String name;
    ProgressBar progressBar;
    private FirebaseDatabase firebaseDatabase;
    private FloatingActionButton mSearchBtn;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerLayout;
    private DatabaseReference databaseReference;
    private DBAdapter dbAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        progressBar = root.findViewById(R.id.prog);
        dbAdapter = new DBAdapter();

        ((MainActivity) getActivity()).showFloatingActionButton();
        recyclerLayout = root.findViewById(R.id.recyclerlayout);
        recyclerLayout.setHasFixedSize(true);
        recyclerLayout.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = firebaseDatabase.getInstance().getReference().child("details");

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                myRefresh();
//            }
//        }, 2000);
        firebaseData();
        return root;
    }

    public void firebaseData() {
        FirebaseRecyclerOptions<DBAdapter> options = new FirebaseRecyclerOptions.Builder<DBAdapter>().setQuery(databaseReference, DBAdapter.class).build();
        FirebaseRecyclerAdapter<DBAdapter, DBViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DBAdapter, DBViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DBViewHolder dbViewHolder, int i, @NonNull DBAdapter dbAdapter) {
                progressBar.setVisibility(View.GONE);
                dbViewHolder.setData(getContext(), dbAdapter.getName(), dbAdapter.getCountry());
                dbViewHolder.setOnClickListener(new DBViewHolder.clickListener() {
                    @Override
                    public void onItemLongClick(View view, int pos) {
                        name = getItem(pos).getName();
                        deleteDialog(name);

                    }
                });
            }

            @NonNull
            @Override
            public DBViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
                return new DBViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerLayout.setAdapter(firebaseRecyclerAdapter);
    }

    private void deleteDialog(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query query = databaseReference.orderByChild("name").equalTo(name);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                        }
                        Toast.makeText(getContext(), "Data deleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public static void hideSoftKeyboard (Activity activity, View view)
//    {
//        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
//    }
//    public void myRefresh() {
//        try {
//            circle_recycler();
//        } catch (Exception e) {
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
}
