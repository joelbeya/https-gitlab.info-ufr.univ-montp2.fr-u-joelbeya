package com.example.easycourse.Activities;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.easycourse.R;
import com.example.easycourse.model.User;
import com.example.easycourse.network.EasyCourseAPI;
import com.example.easycourse.network.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import static com.example.easycourse.utils.Validation.validateEmail;
import static com.example.easycourse.utils.Validation.validateFields;

public class SignupActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable =  new CompositeDisposable();
    EasyCourseAPI easyCourseAPI;

    private RadioGroup mRoleGroup;
    private RadioButton mRole;
    private EditText mEtLastname;
    private EditText mEtFirsname;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtRegister;
    private TextInputLayout mTiLastname;
    private TextInputLayout mTiFirstname;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        easyCourseAPI = retrofitClient.create(EasyCourseAPI.class);

        mRoleGroup = findViewById(R.id.radio_role_group);
        mEtFirsname = findViewById(R.id.et_firstname);
        mEtLastname = findViewById(R.id.et_lastname);
        mEtEmail = findViewById(R.id.et_email);
        mEtPassword = findViewById(R.id.et_password);
        mBtRegister = findViewById(R.id.button_signup);
        mTiLastname = findViewById(R.id.ti_lastname);
        mTiFirstname = findViewById(R.id.ti_firstname);
        mTiEmail= findViewById(R.id.ti_email);
        mTiPassword = findViewById(R.id.ti_password);

        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    public void launchLoginActivity(View view) {
        Intent intent = new Intent(this, AuthenticationActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        int selectedId = mRoleGroup.getCheckedRadioButtonId();
        mRole = findViewById(selectedId);

        String role = mRole.getText().toString();
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

            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating account...");
            progressDialog.show();


            User newUser = new User();
            newUser.setRole(role);
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

                            new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        Toast.makeText(
                                                SignupActivity.this,
                                                "Welcome, " + userResponse.getLastname() + " "
                                                        + userResponse.getFirstname(),
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        progressDialog.dismiss();
                                    }
                                }, 3000);
                        }
                    }));
        }
    }
}
