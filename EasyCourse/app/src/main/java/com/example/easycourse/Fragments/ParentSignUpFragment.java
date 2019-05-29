package com.example.easycourse.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easycourse.R;
import com.example.easycourse.model.User;
import com.example.easycourse.network.EasyCourseAPI;
import com.example.easycourse.network.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.easycourse.utils.Validation.validateEmail;
import static com.example.easycourse.utils.Validation.validateFields;

public class ParentSignUpFragment extends Fragment {
    public static final String TAG = StudentSignUpFragment.class.getSimpleName();

    // Init service
    private CompositeDisposable compositeDisposable =  new CompositeDisposable();
    private EasyCourseAPI easyCourseAPI;

    private EditText mEtLastname;
    private EditText mEtFirsname;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtRegister;
    private Button mBtGoToLogin;
    private TextInputLayout mTiLastname;
    private TextInputLayout mTiFirstname;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_sign_up, container, false);
        // Init service

        Retrofit retrofitClient = RetrofitClient.getInstance();
        easyCourseAPI = retrofitClient.create(EasyCourseAPI.class);

        mEtFirsname = view.findViewById(R.id.et_firstname);
        mEtLastname = view.findViewById(R.id.et_lastname);
        mEtEmail = view.findViewById(R.id.et_email);
        mEtPassword = view.findViewById(R.id.et_password);
        mBtGoToLogin = view.findViewById(R.id.btn_go_to_login);
        mBtRegister = view.findViewById(R.id.button_signup);
        mTiLastname = view.findViewById(R.id.ti_lastname);
        mTiFirstname = view.findViewById(R.id.ti_firstname);
        mTiEmail= view.findViewById(R.id.ti_email);
        mTiPassword = view.findViewById(R.id.ti_password);

        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
        mBtGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

        return view;
    }

    public void goToLogin() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        LoginFragment fragment = new LoginFragment();
        ft.replace(R.id.fragmentFrame, fragment, StudentSignUpFragment.TAG);
        ft.commit();
    }

    public void register(View view) {

        String lastname = mEtLastname.getText().toString();
        String firstname = mEtFirsname.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateFields(lastname)) {
            err++;
            mTiLastname.setError("Lastname should not be empty !");
        }

        if (!validateFields(firstname)) {
            err++;
            mTiFirstname.setError("Firstname should not be empty !");
        }

        if (!validateEmail(email)) {
            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {
            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (err == 0) {

            User newUser = new User();
            newUser.setRole("Parent");
            newUser.setLastname(lastname);
            newUser.setFirstname(firstname);
            newUser.setEmail(email);
            newUser.setPassword(password);

            compositeDisposable.add(easyCourseAPI.register(newUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<User>() {
                        @Override
                        public void accept(final User userResponse) throws Exception {

                            try {
                                if (userResponse.getEmail() == null) {
                                    Toast.makeText(
                                            getActivity(),
                                            "Email is already used by a user",
                                            Toast.LENGTH_SHORT
                                    ).show();
                                } else {
                                    Snackbar.make(mBtRegister,
                                            "A verification email has been sent to you at " +
                                                    userResponse.getEmail(),
                                            Snackbar.LENGTH_SHORT
                                    ).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    })
            );
        }
    }

}
