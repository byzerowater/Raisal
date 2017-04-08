package me.fourground.raisal.ui.write.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteReviewCompleteActivity extends BaseActivity {

    private static final String EXTRA_APP_NAME = "extra_app_name";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_app_name)
    TextView mTvAppName;

    /**
     * AppAppraisalCompleteActivity 가져오기
     *
     * @param context Context
     * @param date    평가기간
     * @return MainActivity Intent
     */
    public static Intent getStartIntent(Context context, String date) {
        Intent intent = new Intent(context, WriteReviewCompleteActivity.class);
        intent.putExtra(EXTRA_APP_NAME, date);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write_review_complete);
        ButterKnife.bind(this);

        mTvTitle.setText(getString(R.string.text_title_register_review_complete));
        String name = getIntent().getStringExtra(EXTRA_APP_NAME);

        mTvAppName.setText(name);
    }

    @OnClick({R.id.btn_my_review, R.id.btn_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_my_review:
                break;
            case R.id.btn_main:
                break;
        }

        finish();
    }
}
