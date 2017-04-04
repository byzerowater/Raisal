package me.fourground.raisal.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.review.ReviewListFragment;
import me.fourground.raisal.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.raisal.ui.write.review.WriteReviewActivity;
import me.fourground.raisal.util.FragmentHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

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

        mNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentHelper.addFragment(R.id.fl_content, MainActivity.this, ReviewListFragment.newInstance());
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(WriteAppAppraisalActivity.getStartIntent(MainActivity.this));
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        });
    }
}
