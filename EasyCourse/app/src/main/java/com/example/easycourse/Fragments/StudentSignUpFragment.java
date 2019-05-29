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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easycourse.R;
import com.example.easycourse.model.SchoolLevel;
import com.example.easycourse.model.Student;
import com.example.easycourse.model.User;
import com.example.easycourse.network.EasyCourseAPI;
import com.example.easycourse.network.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.easycourse.utils.Validation.validateEmail;
import static com.example.easycourse.utils.Validation.validateFields;

public class StudentSignUpFragment extends Fragment {
    public static final String TAG = StudentSignUpFragment.class.getSimpleName();

    // Init service
    private CompositeDisposable compositeDisposable =  new CompositeDisposable();
    private EasyCourseAPI easyCourseAPI;
    private String formula;

    private Button mBtRegister;
    private EditText mEtLastname;
    private EditText mEtFirsname;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private RadioButton mRbFormation;
    private RadioGroup mRgFormation;
    private Spinner mSpScoolLevel;
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

        Button mBtGoToLogin = view.findViewById(R.id.btn_go_to_login);
        mBtRegister = view.findViewById(R.id.button_signup);
        mEtFirsname = view.findViewById(R.id.et_firstname);
        mEtLastname = view.findViewById(R.id.et_lastname);
        mEtEmail = view.findViewById(R.id.et_email);
        mEtPassword = view.findViewById(R.id.et_password);
        mRgFormation = view.findViewById(R.id.radio_group_formation);
        mSpScoolLevel = view.findViewById(R.id.sp_school_level);
        mTiLastname = view.findViewById(R.id.ti_lastname);
        mTiFirstname = view.findViewById(R.id.ti_firstname);
        mTiEmail= view.findViewById(R.id.ti_email);
        mTiPassword = view.findViewById(R.id.ti_password);

        ArrayAdapter<SchoolLevel> adapter = new ArrayAdapter<SchoolLevel>(
                getActivity(), android.R.layout.simple_spinner_item, SchoolLevel.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpScoolLevel.setAdapter(adapter);

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

        mRbFormation = view.findViewById(mRgFormation.getCheckedRadioButtonId());
        formula = mRbFormation.getText().toString();

        mRgFormation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rg, int checkedId) {
                switch(checkedId) {
                    case R.id.first:
                        formula = "progression";
                        break;
                    case R.id.second:
                        formula = "support";
                        break;
                }
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
        String schoolLevel = mSpScoolLevel.getSelectedItem().toString();

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

            Student newUser = new Student();
            newUser.setRole("Student");
            newUser.setLastname(lastname);
            newUser.setFirstname(firstname);
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setSchoolLevel(schoolLevel);
            newUser.setFormula(formula);

            compositeDisposable.add(easyCourseAPI.register(newUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Student>() {
                        @Override
                        public void accept(final Student userResponse) throws Exception {

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
