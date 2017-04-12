package me.fourground.litmus.ui.write.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.common.Const;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.main.MainActivity;
import me.fourground.litmus.util.DateUtil;
import me.fourground.litmus.util.StringUtil;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WriteAppAppraisalCompleteActivity extends BaseActivity {

    private static final String EXTRA_APPRAISAL_DATE = "extra_appraisal_date";
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_date)
    TextView mTvDate;

    /**
     * AppAppraisalCompleteActivity 가져오기
     *
     * @param context Context
     * @param date    평가기간
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

        mTvTitle.setText(getString(R.string.text_title_register_complete));

        String date = getIntent().getStringExtra(EXTRA_APPRAISAL_DATE);
        Calendar currentCalendar = Calendar.getInstance();
        String startDate = DateUtil.getDateFormat(currentCalendar.getTime(), Const.DATE_FORMAT_VIEW);
        currentCalendar.add(Calendar.DATE, StringUtil.getInt(date));
        String endDate = DateUtil.getDateFormat(currentCalendar.getTime(), Const.DATE_FORMAT_VIEW);
        mTvDate.setText(getString(R.string._text_review_date, startDate, endDate));
    }

    @OnClick({R.id.btn_write, R.id.btn_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_write:
                startActivity(WriteAppAppraisalActivity.getStartIntent(WriteAppAppraisalCompleteActivity.this));
                break;
            case R.id.btn_main:
                startActivity(MainActivity.getStartIntent(WriteAppAppraisalCompleteActivity.this));
                break;
        }
        finish();
    }
}
