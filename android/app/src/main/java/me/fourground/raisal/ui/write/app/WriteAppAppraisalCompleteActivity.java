package me.fourground.raisal.ui.write.app;

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
public class WriteAppAppraisalCompleteActivity extends BaseActivity {

    private static final String EXTRA_APPRAISAL_DATE = "extra_appraisal_date";


    @BindView(R.id.tv_date)
    TextView mTvDate;

    /**
     * AppAppraisalCompleteActivity 가져오기
     *
     * @param context Context
     * @param date 평가기간
     * @return MainActivity Intent
     */
    public static Intent getStartIntent(Context context, String date) {
        Intent intent = new Intent(context, WriteAppAppraisalCompleteActivity.class);
        intent.putExtra(EXTRA_APPRAISAL_DATE, date);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_write_app_appraisal_complete);
        ButterKnife.bind(this);

        String date = getIntent().getStringExtra(EXTRA_APPRAISAL_DATE);
        mTvDate.setText(date);
    }


    @OnClick({R.id.btn_write, R.id.btn_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_write:
                break;
            case R.id.btn_main:
                break;
        }
    }
}
