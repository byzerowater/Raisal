package me.fourground.litmus.ui.content;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.data.BusEvent;
import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.data.model.ContentData;
import me.fourground.litmus.data.model.PointData;
import me.fourground.litmus.data.model.ReviewData;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.views.LinearRecyclerView;
import me.fourground.litmus.ui.write.review.WriteReviewActivity;
import me.fourground.litmus.util.LoadingHelper;
import me.fourground.litmus.util.StringUtil;

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
    ImageButton mBtnBack;

    private AppInfoData mAppInfoData;
    private String mDownUrl = null;

    private String mUid;


    /**
     * ContentActivity 가져오기
     *
     * @param context Context
     * @param appId   앱 아이디
     * @return ContentActivity Intent
     */
    public static Intent getStartIntent(Context context, String appId) {
        Intent intent = new Intent(context, ContentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            mUid = currentUser.getUid();
        }


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
        finish();
    }

    @Override
    public void onContent(ContentData contentData) {

        AppInfoData appInfo = contentData.getAppInfo();

        setVisibleWriteReviewBtn(appInfo);
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

    @Subscribe
    public void onSignInCompleted(BusEvent.SignInCompleted event) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            mUid = currentUser.getUid();
        }

        ContentData contentData = mContentAdapter.getContentData();
        setVisibleWriteReviewBtn(contentData.getAppInfo());

    }

    @Subscribe
    public void onSignOutCompleted(BusEvent.SignOutCompleted event) {
        mUid = null;
        ContentData contentData = mContentAdapter.getContentData();
        setVisibleWriteReviewBtn(contentData.getAppInfo());
    }

    private void setVisibleWriteReviewBtn(AppInfoData appInfoData) {

        boolean isEnd = Const.APPRAISAL_TYPE_FINISH.equals(appInfoData.getAppStatus());
        if (isEnd || (!StringUtil.isEmpty(mUid) && mUid.equals(appInfoData.getRegId()))) {
            mBtnWriteReview.setVisibility(View.GONE);
        } else {
            mBtnWriteReview.setVisibility(View.VISIBLE);
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
