package com.prady.apoxeostudentproject.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prady.apoxeostudentproject.R;
import com.prady.apoxeostudentproject.fragments.EditFragment;
import com.prady.apoxeostudentproject.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    private BottomAppBar toolbar;
    //    private MaterialButton floatingActionButton;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.bottom_bar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(R.anim.down_to_up,R.anim.slide_out_left,R.anim.up_to_down,R.anim.slide_out_to_right)
                    .replace(R.id.container, new HomeFragment()).commit();
        }
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.down_to_up, R.anim.slide_out_left, R.anim.up_to_down, R.anim.slide_out_to_right)
                        .replace(R.id.container, new EditFragment()).addToBackStack(null).commit();
            }
        });
    }

    public void showFloatingActionButton() {
        floatingActionButton.show();
        toolbar.setVisibility(View.VISIBLE);
    }

    ;

    public void hideFloatingActionButton() {
        floatingActionButton.hide();
        toolbar.setVisibility(View.INVISIBLE);
    }

    ;
}