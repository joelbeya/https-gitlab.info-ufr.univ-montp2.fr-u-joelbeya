package com.example.easycourse.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.easycourse.Fragments.LoginFragment;
import com.example.easycourse.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class AuthenticationActivity extends FragmentActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (savedInstanceState == null) {

            loadFragment();
        }
    }

    private void loadFragment(){

        if (mLoginFragment == null) {

            mLoginFragment = new LoginFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragmentFrame, mLoginFragment, LoginFragment.TAG
        ).commit();
    }


    private void showSnackBarMessage(String message) {

        Snackbar.make(
                findViewById(R.id.activity_main),message, Snackbar.LENGTH_SHORT
        ).show();

    }
}