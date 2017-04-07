package me.fourground.raisal.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.review.ReviewListFragment;
import me.fourground.raisal.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.raisal.util.FragmentHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MainActivity extends BaseActivity {


    /**
     * MainActivity 가져오기
     *
     * @param context Context
     * @return MainActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_home, R.id.btn_write, R.id.btn_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                FragmentHelper.addFragment(R.id.fl_content, MainActivity.this, ReviewListFragment.newInstance());
                break;
            case R.id.btn_write:
                startActivity(WriteAppAppraisalActivity.getStartIntent(MainActivity.this));
                break;
            case R.id.btn_my:
                break;
        }
    }
}
