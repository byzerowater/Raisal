package me.fourground.raisal.ui.mypage.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.data.BusEvent;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.content.ContentActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;
import me.fourground.raisal.util.LoadingHelper;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyAppActivity extends BaseActivity implements MyAppMvpView {

    @Inject
    Bus mEventBus;
    @Inject
    MyAppPresenter mMyAppPresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    MyAppAdapter mAppAdapter;
    @Inject
    LoadingHelper mLoadingHelper;

    @BindView(R.id.btn_back)
    Button mBtnBack;
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
        mEventBus.register(this);
        mMyAppPresenter.attachView(this);

        mBtnBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(getString(R.string.text_my_app));

        mRvApp.setAdapter(mAppAdapter);

        mAppAdapter.setOnAppItemClickListener(appItem -> {
            startActivity(ContentActivity.getStartIntent(MyAppActivity.this, appItem.getAppId()));
        });

        mLoadingHelper.setOnLoadingListener(() -> {
            mMyAppPresenter.getMyAppList(false);
        });

        mLoadingHelper.setRecyclerView(mRvApp);

        mMyAppPresenter.getMyAppList(true);
    }

    @Subscribe
    public void onRegisterCompleted(BusEvent.RegisterAppCompleted event) {
        AppInfoData appInfoData = event.getAppInfoData();

        Timber.i("onRegisterCompleted " + appInfoData.toString());
        mAppAdapter.addAppData(appInfoData);
        mAppAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onRegisterReviewCompleted(BusEvent.RegisterReviewCompleted event) {
        ReviewData reviewData = event.getReviewData();

        int appInfoPosition = mAppAdapter.getAppInfoPosition(reviewData.getAppId());
        if (appInfoPosition != -1) {
            AppInfoData appInfo = mAppAdapter.getItem(appInfoPosition);
            appInfo.setNPartyUserCount(appInfo.getNPartyUserCount() + 1);
            mAppAdapter.notifyItemChanged(appInfoPosition);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAppPresenter.detachView();
        mEventBus.unregister(this);

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
        finish();
    }

    @Override
    public void onAppList(List<AppInfoData> datas) {
        mLoadingHelper.setNextPage(mMyAppPresenter.getNextUrl());
        mLoadingHelper.setLoading(false);

        mAppAdapter.addAppDatas(datas);
        mAppAdapter.notifyDataSetChanged();
    }
}
