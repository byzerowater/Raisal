package me.fourground.litmus.ui.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.auth.UserInfo;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.main.MainActivity;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SignInActivity extends BaseActivity implements SignInMvpView {

    private static final int REQUEST_SIGN_IN = 0x15;

    private static final String EXTRA_IS_AUTHENTICATION_ERROR = "extra_is_authentication_error";

    @BindView(R.id.sib_google)
    View mSibGoogle;

    @BindView(R.id.sib_facebook)
    View mSibFacebook;

    @Inject
    SignInPresenter mSignInPresenter;
    @Inject
    LoadingDialog mLoadingDialog;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mCallbackManager;

    private boolean isAuthenticationError = false;

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

    /**
     * SignInActivity 가져오기
     *
     * @param context               Context
     * @param isAuthenticationError Context
     * @return SignInActivity Intent
     */
    public static Intent getStartIntent(Context context, boolean isAuthenticationError) {
        Intent intent = new Intent(context, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRA_IS_AUTHENTICATION_ERROR, isAuthenticationError);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSignInPresenter.attachView(this);

        isAuthenticationError = getIntent().getBooleanExtra(EXTRA_IS_AUTHENTICATION_ERROR, false);
        initGoogleLogin();
        initFacebookLogin();
        initFirebaseAuth();
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                String loginType = Const.LOGIN_TYPE_GOOGLE;
                for (UserInfo userInfo : user.getProviderData()) {
                    Timber.i("user " + userInfo.getProviderId());
                    if ("facebook.com".equals(userInfo.getProviderId())) {
                        loginType = Const.LOGIN_TYPE_FACEBOOK;
                        break;
                    }
                }
                // User is signed in
                mSignInPresenter.signIn(user, loginType);
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
            mLoadingDialog.show();
        } else {

            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {
        finish();
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
        if (!isAuthenticationError) {
            startActivity(MainActivity.getStartIntent(SignInActivity.this));
        }
        finish();
    }
}
