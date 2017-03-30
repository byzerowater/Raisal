package me.fourground.raisal.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.common.AppAdapter;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MainActivity extends BaseActivity implements MainMvpView {


    @Inject
    MainPresenter mMainPresenter;
    @Inject
    AppAdapter mAppAdapter;
    @Inject
    LoadingDialog mLoadingDialog;

    @BindView(R.id.rv_app)
    LinearRecyclerView mRvApp;

    /**
     * MainActivity 가져오기
     *
     * @param context Context
     * @return MainActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.detachView();
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

    }
}
