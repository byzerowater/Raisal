package me.fourground.raisal.ui.write.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteWriteAppAppraisalActivity extends BaseActivity implements WriteAppAppraisalMvpView {


    @Inject
    WriteAppAppraisalPresenter mWriteAppAppraisalPresenter;

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
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    @OnClick({R.id.btn_pre, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pre:
                break;
            case R.id.btn_cancel:
                break;
            case R.id.btn_confirm:
                break;
        }
    }
}
