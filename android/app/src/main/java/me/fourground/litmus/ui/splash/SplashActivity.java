package me.fourground.litmus.ui.splash;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.signin.SignInActivity;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashPresenter mSplashPresenter;
    @Inject
    LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mSplashPresenter.attachView(this);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
//            mSplashPresenter.delaySplash();
            startActivity(SignInActivity.getStartIntent(SplashActivity.this));
            finish();
        } else {
            signIn(currentUser);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPresenter.detachView();
    }

    @Override
    public void showProgress(boolean isShow) {
//        if (isShow) {
//            mLoadingDialog.show();
//        } else {
//            mLoadingDialog.dismiss();
//        }
    }

    @Override
    public void onError() {
        finish();
    }

    @Override
    public void onSignIn() {
        mSplashPresenter.delaySplash();
    }

    public void onGoMain() {
        startActivity(SignInActivity.getStartIntent(this));
        finish();
    }

    private void signIn(FirebaseUser user) {
        String loginType = Const.LOGIN_TYPE_GOOGLE;
        if (user.getProviderData() != null) {
            for (UserInfo userInfo : user.getProviderData()) {
                Timber.i("user " + userInfo.getProviderId());
                if ("facebook.com".equals(userInfo.getProviderId())) {
                    loginType = Const.LOGIN_TYPE_FACEBOOK;
                    break;
                }
            }
        }
        // User is signed in
        mSplashPresenter.signIn(user, loginType);
    }

}
