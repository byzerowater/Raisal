package me.fourground.raisal.ui.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.main.MainActivity;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SignInActivity extends BaseActivity implements SignInMvpView {

    private static final int REQUEST_SIGN_IN = 0x15;

    private static final String LOGIN_TYPE_GOOGLE = "G";
    private static final String LOGIN_TYPE_FACEBOOK = "F";

    @BindView(R.id.sib_google)
    Button mSibGoogle;

    @BindView(R.id.sib_facebook)
    Button mSibFacebook;

    @Inject
    SignInPresenter mSignInPresenter;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private String mLoginType = null;

    /**
     * SignInActivity 가져오기
     *
     * @param context Context
     * @return SignInActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, SignInActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSignInPresenter.attachView(this);
        initGoogleLogin();
        initFacebookLogin();
        initFirebaseAuth();
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Timber.i("user " + user.getProviderId());
                // User is signed in
                mSignInPresenter.login(user, mLoginType);
            } else {
                // User is signed out
                Timber.i("onAuthStateChanged:signed_out");
                showProgress(false);
            }

        };
    }

    private void initGoogleLogin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, connectionResult -> {
                    Timber.d("onConnectionFailed:" + connectionResult);
                    Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initFacebookLogin() {

        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.d("facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Timber.d("facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Timber.d("facebook:onError", error);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSignInPresenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == REQUEST_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                Timber.d("facebook:onCancel");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Timber.i("firebaseAuthWithGoogle:" + acct.getId());
        showProgress(true);

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            mLoginType = LOGIN_TYPE_GOOGLE;
                            Timber.d("signInWithCredential:onComplete:" + task.isSuccessful());
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Timber.w("signInWithCredential", task.getException());

                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        }
                );
    }

    private void signInGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, REQUEST_SIGN_IN);
    }

    private void signInFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this,
                Arrays.asList("email", "public_profile"));
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Timber.d("handleFacebookAccessToken:" + token);
        showProgress(true);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                            mLoginType = LOGIN_TYPE_FACEBOOK;
                            Timber.d("signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Timber.w("signInWithCredential", task.getException());
                                Toast.makeText(SignInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                showProgress(false);
                            }
                        }
                );
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    @OnClick({R.id.sib_google, R.id.sib_facebook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sib_google:
                signInGoogle();
                break;
            case R.id.sib_facebook:
                signInFacebook();
                break;
        }
    }

    @Override
    public void onSignIn() {
        startActivity(MainActivity.getStartIntent(SignInActivity.this));
        finish();
    }
}
