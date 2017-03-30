package me.fourground.raisal.ui.splash;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.common.Const;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.signin.SignInActivity;
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
            mSplashPresenter.delaySplash();
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
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.hide();
        }
    }

    @Override
    public void onError() {
        finish();
    }

    @Override
    public void onSignIn() {
        mSplashPresenter.delaySplash();
    }

    @Override
    public void onGoMain() {
        startActivity(SignInActivity.getStartIntent(this));
        finish();
    }

    private void signIn(FirebaseUser user) {
        String loginType = Const.LOGIN_TYPE_GOOGLE;
        for (UserInfo userInfo : user.getProviderData()) {
            Timber.i("user " + userInfo.getProviderId());
            if ("facebook.com".equals(userInfo.getProviderId())) {
                loginType = Const.LOGIN_TYPE_FACEBOOK;
                break;
            }
        }
        // User is signed in
        mSplashPresenter.signIn(user, loginType);
    }

}
