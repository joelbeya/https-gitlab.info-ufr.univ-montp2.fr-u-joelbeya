package com.example.easycourse.Fragments;

        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.easycourse.Activities.DashboardActivity;
        import com.example.easycourse.R;
        import com.example.easycourse.network.EasyCourseAPI;
        import com.example.easycourse.network.RetrofitClient;
        import com.facebook.AccessToken;
        import com.facebook.CallbackManager;
        import com.facebook.FacebookCallback;
        import com.facebook.FacebookException;
        import com.facebook.GraphRequest;
        import com.facebook.GraphResponse;
        import com.facebook.login.LoginResult;
        import com.facebook.login.widget.LoginButton;
        import com.google.android.material.textfield.TextInputLayout;

        import java.util.Arrays;

        import androidx.fragment.app.Fragment;
        import androidx.fragment.app.FragmentTransaction;
        import io.reactivex.android.schedulers.AndroidSchedulers;
        import io.reactivex.disposables.CompositeDisposable;
        import io.reactivex.functions.Consumer;
        import io.reactivex.schedulers.Schedulers;
        import retrofit2.Retrofit;

        import static com.example.easycourse.utils.Validation.validateEmail;
        import static com.example.easycourse.utils.Validation.validateFields;

        import com.example.easycourse.model.User;

        import org.json.JSONException;
        import org.json.JSONObject;

public class LoginFragment extends Fragment {

    public static final String TAG = LoginFragment.class.getSimpleName();

    CompositeDisposable compositeDisposable =  new CompositeDisposable();
    EasyCourseAPI easyCourseAPI;

    private CallbackManager mCallbackManager;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtLogin;
    private Button mBtGoToRegister;
    private LoginButton mBtFacebook;
    private TextInputLayout mTiEmail;
    private TextInputLayout mTiPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Init service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        easyCourseAPI = retrofitClient.create(EasyCourseAPI.class);
        mCallbackManager = CallbackManager.Factory.create();

        initViews(view);

        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        mBtFacebook.setReadPermissions(Arrays.asList("email", "public_profile"));
        mBtFacebook.setFragment(this);
        mBtFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                if (isLoggedIn) {
                    useLoginInformation(accessToken);
                }

                Intent intent = new Intent(getActivity(), DashboardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        mBtGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        return view;
    }

    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");

                    Toast.makeText(
                            getActivity(), "Welcome, " + name, Toast.LENGTH_SHORT
                    ).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void initViews(View v) {
        mEtEmail = v.findViewById(R.id.et_email);
        mEtPassword = v.findViewById(R.id.et_password);
        mBtLogin = v.findViewById(R.id.btn_login);
        mBtFacebook = v.findViewById(R.id.login_button_facebook);
        mBtGoToRegister = v.findViewById(R.id.btn_go_to_register);
        mTiEmail= v.findViewById(R.id.ti_email);
        mTiPassword = v.findViewById(R.id.ti_password);
    }

    private void goToRegister() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        SignupFragment fragment = new SignupFragment();
        ft.replace(R.id.fragmentFrame, fragment, LoginFragment.TAG);
        ft.commit();
    }

    public void login(View view) {
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {
            err++;
            mTiEmail.setError("Email should be valid !");
        }

        if (!validateFields(password)) {
            err++;
            mTiPassword.setError("Password should not be empty !");
        }

        if (err == 0) {
            compositeDisposable.add(easyCourseAPI.login(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<User>() {
                        @Override
                        public void accept(User userResponse) throws Exception {

                            try {
                                if (userResponse.getEmail() == null) {
                                    Toast.makeText(
                                            getActivity(),
                                            userResponse.getMsg(),
                                            Toast.LENGTH_SHORT
                                    ).show();
                                } else {
                                    Toast.makeText(
                                            getActivity(),
                                            "Welcome, " + userResponse.getFirstname(),
                                            Toast.LENGTH_SHORT
                                    ).show();

                                    SharedPreferences settings = getActivity().getSharedPreferences(
                                            "UserInfo", 0
                                    );

                                    SharedPreferences.Editor editor = settings.edit();
                                    editor.putString("Username",
                                            userResponse.getFirstname() + " " + userResponse.getLastname());
                                    editor.putString("Email", userResponse.getEmail());
                                    editor.commit();

                                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                                    intent.putExtra("currentUser", userResponse.getFirstname());
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }));
        }
    }
}
