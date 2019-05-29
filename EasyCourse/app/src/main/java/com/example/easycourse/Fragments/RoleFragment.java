package com.example.easycourse.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.easycourse.R;

public class RoleFragment extends Fragment {
    public static final String TAG = RoleFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_role, container, false);
        // Inflate the layout for this fragment

        Button mBtParentSignUp = view.findViewById(R.id.btn_parent);
        Button mBtStudentSignUp = view.findViewById(R.id.btn_student);

        mBtParentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToParentSignUp();
            }
        });
        mBtStudentSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStudentSignUp();
            }
        });

        return view;
    }

    private void goToParentSignUp() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ParentSignUpFragment fragment = new ParentSignUpFragment();
        ft.replace(R.id.fragmentFrame, fragment, RoleFragment.TAG);
        ft.commit();
    }

    private void goToStudentSignUp() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        StudentSignUpFragment fragment = new StudentSignUpFragment();
        ft.replace(R.id.fragmentFrame, fragment, RoleFragment.TAG);
        ft.commit();
    }

}
