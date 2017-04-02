package me.fourground.raisal.ui.write.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.data.model.RegisterAppRequest;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.write.Checker;
import me.fourground.raisal.util.ViewHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteWriteAppAppraisalActivity extends BaseActivity implements WriteAppAppraisalMvpView {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.btn_pre)
    Button mBtnPre;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;


    @Inject
    WriteAppAppraisalPresenter mWriteAppAppraisalPresenter;
    @Inject
    LoadingDialog mLoadingDialog;

    RegisterAppRequest mRegisterAppRequest = new RegisterAppRequest();

    /**
     * WriteWriteAppAppraisalActivity 가져오기
     *
     * @param context Context
     * @return WriteWriteAppAppraisalActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WriteWriteAppAppraisalActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write_app_appraisal);
        ButterKnife.bind(this);
        mWriteAppAppraisalPresenter.attachView(this);

        mTvTitle.setText(R.string.text_register);
        mBtnPre.setVisibility(View.GONE);
        ViewHelper.addFragment(R.id.fl_content, this, WriteNameFragment.newInstance());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWriteAppAppraisalPresenter.detachView();
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

    public RegisterAppRequest getRegisterAppRequest() {
        return mRegisterAppRequest;
    }

    @OnClick({R.id.btn_pre, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pre:
                mBtnPre.setVisibility(View.GONE);
                onBackPressed();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_confirm:
                Fragment fragment = ViewHelper.getFragment(R.id.fl_content, WriteWriteAppAppraisalActivity.this);

                if (fragment != null) {
                    if (fragment instanceof Checker) {
                        if (((Checker) fragment).checkInputText()) {
                            if (fragment instanceof WriteNameFragment) {
                                ViewHelper.addToBackStackFragment(R.id.fl_content, WriteWriteAppAppraisalActivity.this, WriteDescriptionFragment.newInstance());
                            } else {
                                mWriteAppAppraisalPresenter.registerApp(mRegisterAppRequest);
                            }
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
