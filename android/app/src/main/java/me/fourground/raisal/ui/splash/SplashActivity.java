package me.fourground.raisal.ui.splash;

import android.os.Bundle;

import com.zerowater.environment.R;
import me.fourground.raisal.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

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

        mSplashPresenter.getAccessToken();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSplashPresenter.detachView();
    }

    @Override
    public void showUpdateDialog(String version) {

    }
}
