package me.fourground.raisal.ui.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.mypage.app.MyAppActivity;
import me.fourground.raisal.ui.mypage.review.MyReviewActivity;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyPageActivity extends BaseActivity implements MyPageMvpView {


    @Inject
    MyPagePresenter mMyPagePresenter;

    @BindView(R.id.rv_app)
    RecyclerView mRvReview;
    @BindView(R.id.tv_nickname)
    TextView mTvNickname;

    /**
     * MyPageActivity 가져오기
     *
     * @param context Context
     * @return MyPageActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyPageActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_my_page);
        ButterKnife.bind(this);
        mMyPagePresenter.attachView(this);
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            showProgressDialog();
        } else {
            hideProgressDialog();
        }
    }

    @OnClick({R.id.btn_change_nickname, R.id.rl_btn_my_app, R.id.rl_btn_my_review})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_nickname:
                break;
            case R.id.rl_btn_my_app:
                startActivity(MyAppActivity.getStartIntent(MyPageActivity.this));
                break;
            case R.id.rl_btn_my_review:
                startActivity(MyReviewActivity.getStartIntent(MyPageActivity.this));
                break;
        }
    }
}
