package me.fourground.raisal.ui.mypage.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
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
import me.fourground.raisal.data.model.MyReviewData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.content.ContentActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;
import me.fourground.raisal.util.LoadingHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyReviewActivity extends BaseActivity implements MyReviewMvpView {

    @Inject
    Bus mEventBus;
    @Inject
    MyReviewPresenter mMyAppPresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    MyReviewAdapter mAppAdapter;
    @Inject
    LoadingHelper mLoadingHelper;

    @BindView(R.id.btn_back)
    ImageButton mBtnBack;
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
        Intent intent = new Intent(context, MyReviewActivity.class);
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
        mTvTitle.setText(getString(R.string.text_my_review));

        mRvApp.setAdapter(mAppAdapter);

        mAppAdapter.setOnReviewItemClickListener(myReviewData -> {
            AppInfoData appInfoData = myReviewData.getAppInfo();
            startActivity(ContentActivity.getStartIntent(MyReviewActivity.this, appInfoData.getAppId()));
        });

        mLoadingHelper.setOnLoadingListener(() -> {
            mMyAppPresenter.getMyReviewList(false);
        });

        mLoadingHelper.setRecyclerView(mRvApp);

        mMyAppPresenter.getMyReviewList(true);
    }

    @Subscribe
    public void onRegisterReviewCompleted(BusEvent.RegisterReviewCompleted event) {
        ReviewData reviewData = event.getReviewData();

        int appInfoPosition = mAppAdapter.getAppInfoPosition(reviewData.getAppId());
        if (appInfoPosition != -1) {
            MyReviewData myReviewData = mAppAdapter.getItem(appInfoPosition);

            MyReviewData newReviewData = new MyReviewData();

            newReviewData.setAppInfo(myReviewData.getAppInfo());
            newReviewData.setAppElement(reviewData.getAppElement());
            newReviewData.setAppComment(reviewData.getAppComment());
            newReviewData.setUserName(reviewData.getUserName());

            mAppAdapter.addReviewData(newReviewData);
            mAppAdapter.notifyDataSetChanged();
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
    public void onReviewList(List<MyReviewData> datas) {
        mLoadingHelper.setNextPage(mMyAppPresenter.getNextUrl());
        mLoadingHelper.setLoading(false);

        mAppAdapter.addReviewDatas(datas);
        mAppAdapter.notifyDataSetChanged();
    }
}
