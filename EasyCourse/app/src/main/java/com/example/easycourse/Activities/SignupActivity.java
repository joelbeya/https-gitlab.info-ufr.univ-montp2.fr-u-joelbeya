package com.example.easycourse.Activities;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
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

import com.example.easycourse.Fragments.LoginFragment;
import com.example.easycourse.Fragments.SignupFragment;
import com.example.easycourse.R;
import com.example.easycourse.model.User;
import com.example.easycourse.network.EasyCourseAPI;
import com.example.easycourse.network.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.TimeUnit;

import static com.example.easycourse.utils.Validation.validateEmail;
import static com.example.easycourse.utils.Validation.validateFields;

public class SignupActivity extends AppCompatActivity {

    SignupFragment mSignUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        loadFragment();
    }

    private void loadFragment(){

        if (mSignUpFragment == null) {

            mSignUpFragment = new SignupFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.fragmentFrame, mSignUpFragment, SignupFragment.TAG
        ).commit();
    }
}