package me.fourground.raisal.ui.write.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.RegisterReviewRequest;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.util.FragmentHelper;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteReviewActivity extends BaseActivity implements WriteReviewMvpView {

    private static final String EXTRA_APP_DATA = "EXTRA_APP_DATA";


    @Inject
    WriteReviewPresenter mWriteReviewPresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @BindView(R.id.btn_back)
    Button mBtnBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;

    private AppInfoData mAppInfoData;

    private RegisterReviewRequest mRegisterReviewRequest = new RegisterReviewRequest();

    /**
     * WriteWriteReviewActivity 가져오기
     *
     * @param context Context
     * @return WriteWriteReviewActivity Intent
     */
    public static Intent getStartIntent(Context context, AppInfoData appInfoData) {
        Intent intent = new Intent(context, WriteReviewActivity.class);
        intent.putExtra(EXTRA_APP_DATA, appInfoData);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write_review);
        ButterKnife.bind(this);
        mWriteReviewPresenter.attachView(this);

        mBtnCancel.setVisibility(View.VISIBLE);
        mAppInfoData = getIntent().getParcelableExtra(EXTRA_APP_DATA);

        mTvTitle.setText(R.string.text_title_write_review);
        FragmentHelper.addFragment(R.id.fl_content, WriteReviewActivity.this, WriteUsedFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBtnBack.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWriteReviewPresenter.detachView();
    }

    @Override
    public void showProgress(boolean isShow) {

        Timber.i("showProgress %s ", isShow);
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {

    }

    public void registerApp() {
        mWriteReviewPresenter.registerApp(mAppInfoData.getAppId(), mRegisterReviewRequest);
    }

    public AppInfoData getAppInfoData() {
        return mAppInfoData;
    }

    public RegisterReviewRequest getRegisterReviewRequest() {
        return mRegisterReviewRequest;
    }

    @OnClick({R.id.btn_back, R.id.btn_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_cancel:
                finish();
                break;
        }
    }

    public void goWritePoint() {
        mBtnBack.setVisibility(View.VISIBLE);
        FragmentHelper.addToBackStackFragment(R.id.fl_content, WriteReviewActivity.this, WritePointFragment.newInstance());
    }

    @Override
    public void onRegister() {
        startActivity(
                WriteReviewCompleteActivity.getStartIntent(
                        WriteReviewActivity.this,
                        mAppInfoData.getAppId(),
                        mAppInfoData.getAppName()));
        finish();
    }
}
