package me.fourground.litmus.ui.write.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.data.model.RegisterAppRequest;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.write.Checker;
import me.fourground.litmus.util.FragmentHelper;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteAppAppraisalActivity extends BaseActivity implements WriteAppAppraisalMvpView {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_back)
    ImageButton mBtnBack;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;


    @Inject
    WriteAppAppraisalPresenter mWriteAppAppraisalPresenter;
    @Inject
    LoadingDialog mLoadingDialog;

    RegisterAppRequest mRegisterAppRequest = new RegisterAppRequest();
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;

    /**
     * WriteWriteAppAppraisalActivity 가져오기
     *
     * @param context Context
     * @return WriteWriteAppAppraisalActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WriteAppAppraisalActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write_app_appraisal);
        ButterKnife.bind(this);
        mWriteAppAppraisalPresenter.attachView(this);

        mTvTitle.setText(R.string.text_title_register);
        mBtnCancel.setVisibility(View.VISIBLE);
        FragmentHelper.addFragment(R.id.fl_content, this, WriteNameFragment.newInstance());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mBtnBack.setVisibility(View.GONE);
        mBtnConfirm.setText(getString(R.string.action_next));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWriteAppAppraisalPresenter.detachView();
    }

    @Override
    public void showProgress(boolean isShow) {

        Timber.i("showProgress %s", isShow);
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {
    }

    public RegisterAppRequest getRegisterAppRequest() {
        return mRegisterAppRequest;
    }

    @OnClick({R.id.btn_back, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_confirm:
                Fragment fragment = FragmentHelper.getFragment(R.id.fl_content, WriteAppAppraisalActivity.this);

                if (fragment != null) {
                    if (fragment instanceof Checker
                            && ((Checker) fragment).checkInputText()) {
                        if (fragment instanceof WriteNameFragment) {
                            mBtnBack.setVisibility(View.VISIBLE);
                            mBtnConfirm.setText(getString(R.string.action_register));
                            FragmentHelper.addToBackStackFragment(
                                    R.id.fl_content,
                                    WriteAppAppraisalActivity.this,
                                    WriteDescriptionFragment.newInstance());
                        } else {
                            mWriteAppAppraisalPresenter.registerApp(mRegisterAppRequest);
                        }
                    }

                    break;
                }
        }
    }


    @Override
    public void onRegister() {
        startActivity(WriteAppAppraisalCompleteActivity.getStartIntent(this, mRegisterAppRequest.getReqTerm()));
        finish();
    }
}
