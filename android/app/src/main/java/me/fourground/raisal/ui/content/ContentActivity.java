package me.fourground.raisal.ui.content;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import me.fourground.raisal.common.Const;
import me.fourground.raisal.data.BusEvent;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.PointData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;
import me.fourground.raisal.ui.write.review.WriteReviewActivity;
import me.fourground.raisal.util.LoadingHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ContentActivity extends BaseActivity implements ContentMvpView {

    private static final String EXTRA_APP_ID = "extra_app_id";


    @Inject
    ContentPresenter mContentPresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    ContentAdapter mContentAdapter;
    @Inject
    Bus mEventBus;
    @Inject
    LoadingHelper mLoadingHelper;


    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_content)
    LinearRecyclerView mRvContent;
    @BindView(R.id.btn_write_review)
    Button mBtnWriteReview;
    @BindView(R.id.btn_back)
    Button mBtnBack;

    private AppInfoData mAppInfoData;
    private String mDownUrl = null;


    /**
     * ContentActivity 가져오기
     *
     * @param context Context
     * @param appId   앱 아이디
     * @return ContentActivity Intent
     */
    public static Intent getStartIntent(Context context, String appId) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.putExtra(EXTRA_APP_ID, appId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        mContentPresenter.attachView(this);
        mEventBus.register(this);

        mBtnBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(getString(R.string.text_title_content));

        mRvContent.setAdapter(mContentAdapter);
        mLoadingHelper.setRecyclerView(mRvContent);
        String appId = getIntent().getStringExtra(EXTRA_APP_ID);

        mLoadingHelper.setOnLoadingListener(() -> {
            mContentPresenter.getReviewList();
        });

        mContentPresenter.getContent(appId);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContentPresenter.detachView();
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

    @Override
    public void onContent(ContentData contentData) {

        AppInfoData appInfo = contentData.getAppInfo();

        boolean isEnd = Const.APPRAISAL_TYPE_FINISH.equals(appInfo.getAppStatus());
        if (isEnd) {
            mBtnWriteReview.setVisibility(View.GONE);
        } else {
            mBtnWriteReview.setVisibility(View.VISIBLE);
        }

        mContentAdapter.addContentData(contentData);
        mContentAdapter.notifyDataSetChanged();

        mDownUrl = contentData.getAppDownloadUrl();
        mAppInfoData = appInfo;
    }

    @Override
    public void onReviewList(List<ReviewData> reviewDatas) {
        mLoadingHelper.setNextPage(mContentPresenter.getNextUrl());
        mLoadingHelper.setLoading(false);

        mContentAdapter.addReviewDatas(reviewDatas);
        mContentAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onRegisterReviewCompleted(BusEvent.RegisterReviewCompleted event) {
        ReviewData reviewData = event.getReviewData();

        ContentData contentData = mContentAdapter.getContentData();
        AppInfoData appInfo = contentData.getAppInfo();

        if (reviewData.getAppId().equals(appInfo.getAppId())) {

            PointData appPoint = contentData.getAppElement();
            PointData reviewPoint = reviewData.getAppElement();

            int nPartyUserCount = appInfo.getNPartyUserCount();

            if (appPoint == null) {
                appPoint = new PointData();
                contentData.setAppElement(appPoint);
            }

            float totalContens = appPoint.getContents() * nPartyUserCount + reviewPoint.getContents();
            float totalDesign = appPoint.getDesign() * nPartyUserCount + reviewPoint.getDesign();
            float totalSatisfaction = appPoint.getSatisfaction() * nPartyUserCount + reviewPoint.getSatisfaction();
            float totalUseful = appPoint.getUseful() * nPartyUserCount + reviewPoint.getUseful();

            nPartyUserCount++;

            appPoint.setContents(totalContens / nPartyUserCount);
            appPoint.setDesign(totalDesign / nPartyUserCount);
            appPoint.setSatisfaction(totalSatisfaction / nPartyUserCount);
            appPoint.setUseful(totalUseful / nPartyUserCount);

            appInfo.setNPartyUserCount(nPartyUserCount);

            float avg = (totalContens + totalDesign + totalSatisfaction + totalUseful) / nPartyUserCount / 4;

            appInfo.setAppraisalAvg(avg);

            mContentAdapter.addReviewData(reviewData);
            mContentAdapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.btn_back, R.id.btn_app_down, R.id.btn_write_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_app_down:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mDownUrl)));
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_write_review:
                startActivity(WriteReviewActivity.getStartIntent(ContentActivity.this, mAppInfoData));
                break;
        }
    }
}
