package me.fourground.raisal.ui.splash;

import android.os.Bundle;

import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.fourground.raisal.ui.signin.SignInActivity;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * youngsoo.kim@yap.net
 */
public class SplashActivity extends BaseActivity implements SplashMvpView {

    @Inject
    SplashPresenter mSplashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        mSplashPresenter.attachView(this);

        startActivity(SignInActivity.getStartIntent(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPresenter.detachView();
    }

    @Override
    public void showProgress(boolean isShow) {

    }
}
