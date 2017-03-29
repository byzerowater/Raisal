package me.fourground.raisal.ui.write.review;

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
public class WriteWriteReviewActivity extends BaseActivity implements WriteReviewMvpView {


    @Inject
    WriteReviewPresenter mWriteReviewPresenter;


    /**
     * WriteWriteReviewActivity 가져오기
     *
     * @param context Context
     * @return WriteWriteReviewActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WriteWriteReviewActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write_review);
        ButterKnife.bind(this);
        mWriteReviewPresenter.attachView(this);
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
