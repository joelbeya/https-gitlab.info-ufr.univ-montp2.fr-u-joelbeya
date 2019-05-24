package com.example.easycourse.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easycourse.R;

public class DashboardFragment extends Fragment {
    public static final String TAG = DashboardFragment.class.getSimpleName();

    private TextView mTvUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        String userName = getActivity().getIntent().getStringExtra("currentUser");

        mTvUser = view.findViewById(R.id.tv_username);

        mTvUser.setText(userName);

        // Inflate the layout for this fragment
        return view;
    }
}
