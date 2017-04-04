package me.fourground.raisal.ui.mypage.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
public class MyAppActivity extends BaseActivity implements MyAppMvpView {


    @Inject
    MyAppPresenter mMyAppPresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    AppAdapter mAppAdapter;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_app)
    LinearRecyclerView mRvApp;

    /**
     * MyAppActivity 가져오기
     *
     * @param context Context
     * @return MyAppActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyAppActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_my_app);
        ButterKnife.bind(this);
        mMyAppPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAppPresenter.detachView();
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

    }

    @OnClick(R.id.btn_back)
    public void onViewClicked() {
    }
}
