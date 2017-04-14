package me.fourground.litmus.ui.write.review;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.data.model.RegisterReviewRequest;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.util.DialogFactory;
import me.fourground.litmus.util.FragmentHelper;
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
    ImageButton mBtnBack;
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

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            if (mAppInfoData.getRegId().equals(currentUser.getUid())) {
                Dialog errorAlert = DialogFactory.createDialog(WriteReviewActivity.this,
                        getString(R.string.text_error_no_write_review),
                        getString(R.string.action_confirm),
                        (dialog, which) -> {
                            finish();
                        });
                errorAlert.setCancelable(false);
                errorAlert.show();
            }
        }

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
        finish();
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
