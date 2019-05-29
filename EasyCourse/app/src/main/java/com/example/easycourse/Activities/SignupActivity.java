package com.example.easycourse.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.easycourse.Fragments.RoleFragment;
import com.example.easycourse.Fragments.StudentSignUpFragment;
import com.example.easycourse.R;

public class SignupActivity extends AppCompatActivity {

    StudentSignUpFragment mSignUpFragment;
    RoleFragment mRoleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loadFragment();
    }

    private void loadFragment(){

        if (mRoleFragment == null) {

            mRoleFragment = new RoleFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragmentFrame, mRoleFragment, RoleFragment.TAG
        ).commit();
    }
}