package com.example.easycourse.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.easycourse.Activities.AuthenticationActivity;
import com.example.easycourse.Activities.SignupActivity;
import com.example.easycourse.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WizardStep3Fragment extends Fragment {


    private Button mBtGoToRegister;
    private Button mBtGoToLogin;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_wizard_step3, container, false);


        mBtGoToRegister = rootView.findViewById(R.id.btn_go_to_register);

        mBtGoToLogin = rootView.findViewById(R.id.btn_go_to_login);

        mBtGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignUpActivity(v);
            }
        });

        mBtGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLoginActivity(v);
            }
        });
        return rootView;
    }

    public void launchLoginActivity(View view) {
        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
        startActivity(intent);
    }

    public void launchSignUpActivity(View view) {
        Intent intent = new Intent(getActivity(), SignupActivity.class);
        startActivity(intent);
    }
}