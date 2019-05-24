package com.example.easycourse.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.easycourse.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class WizardStep2Fragment extends Fragment {

    private TextView mTvDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_wizard_step2, container, false);

        mTvDesc = rootView.findViewById(R.id.tv_desc);

        mTvDesc.setText(
                "Easy Course is an application on which you can take courses on a lot of subjects. \n "
        );

        return rootView;
    }
}