package com.prady.apoxeostudentproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prady.apoxeostudentproject.activities.MainActivity;
import com.prady.apoxeostudentproject.R;
import com.prady.apoxeostudentproject.db.DBAdapter;

public class EditFragment extends Fragment {

    TextInputEditText et_name, et_country;
    MaterialButton button;
    private FirebaseDatabase firebaseDatabase;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerLayout;
    private DatabaseReference databaseReference;
    private DBAdapter dbAdapter;

    public EditFragment() {
        // Required empty public constructor
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit, container, false);

        button = root.findViewById(R.id.editBtn);
        et_name = root.findViewById(R.id.editTextName2);
        et_country = root.findViewById(R.id.editTextCountry2);
        dbAdapter = new DBAdapter();
        coordinatorLayout = root.findViewById(R.id.coor_edit);

        ((MainActivity) getActivity()).hideFloatingActionButton();
//        recyclerLayout = root.findViewById(R.id.recyclerlayout);
//        recyclerLayout.setHasFixedSize(true);
//        recyclerLayout.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = firebaseDatabase.getInstance().getReference().child("details");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUserName = et_name.getText().toString();
                String strCountry = et_country.getText().toString();
                if (TextUtils.isEmpty(strUserName)) {
                    et_name.setError("Error, no input found");
                    return;
                }
                if (TextUtils.isEmpty(strCountry)) {
                    et_country.setError("Error, no input found");
                    return;
                }

                dbAdapter.setName(et_name.getText().toString());
                dbAdapter.setCountry(et_country.getText().toString());

                String id = databaseReference.push().getKey();
                databaseReference.child(id).setValue(dbAdapter);
                Toast.makeText(getContext(), "Data added", Toast.LENGTH_LONG).show();

//                Fragment newCase=new HomeFragment();
//                FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                transaction.replace(R.id.container,newCase); // give your fragment container id in first parameter
////                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                transaction.commit();

                hideSoftKeyboard(getActivity(), v);
            }
        });

        et_name.requestFocus();
        if (et_name.requestFocus()) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(
                    InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY
            );
        }

        et_name.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == 66) {
                    et_name.requestFocus();
                }
                return false;
            }
        });
        return root;
    }
}
