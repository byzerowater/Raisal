package me.fourground.raisal.ui.content;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;

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
    ReviewAdapter mReviewAdapter;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_store)
    TextView mTvStore;
    @BindView(R.id.tv_state)
    TextView mTvState;
    @BindView(R.id.tv_date)
    TextView mTvDate;
    @BindView(R.id.rv_review)
    LinearRecyclerView mRvReview;

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

        mRvReview.setAdapter(mReviewAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContentPresenter.detachView();
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

    @Override
    public void onContent(ContentData contentData) {
        AppInfoData appInfoVo = contentData.getAppInfoVo();
        mTvName.setText(appInfoVo.getAppName());
        mTvStore.setText(appInfoVo.getTargetOsCode());
        mTvState.setText(appInfoVo.getStat());
        mTvDate.setText(contentData.getStartDtm() + contentData.getEndDtm());
    }

    @Override
    public void onReviewList(List<ReviewData> reviewDatas) {
        mReviewAdapter.addReviewDatas(reviewDatas);
        mReviewAdapter.notifyDataSetChanged();
    }
}
