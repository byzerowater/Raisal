package me.fourground.litmus.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.ui.app.AppListFragment;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.mypage.MyPageFragment;
import me.fourground.litmus.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.litmus.util.FragmentHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MainActivity extends BaseActivity {

    private static final int MENU_HOME = 0;
    private static final int MENU_WRITE = 1;
    private static final int MENU_MY = 2;


    @BindViews({R.id.btn_home, R.id.btn_write, R.id.btn_my})
    View[] mBtnMenu;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    /**
     * MainActivity 가져오기
     *
     * @param context Context
     * @return MainActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        selectButton(MENU_HOME);
    }

    private void selectButton(int position) {

        if (position != MENU_WRITE) {
            int size = mBtnMenu.length;

            for (int i = 0; i < size; i++) {
                mBtnMenu[i].setSelected(position == i);
            }
        }

        switch (position) {
            case MENU_HOME:
                FragmentHelper.addFragment(R.id.fl_content, MainActivity.this, AppListFragment.newInstance());
                mTvTitle.setText(getString(R.string.app_name));
                break;
            case MENU_WRITE:
                startActivity(WriteAppAppraisalActivity.getStartIntent(MainActivity.this));
                break;
            case MENU_MY:
                FragmentHelper.addFragment(R.id.fl_content, MainActivity.this, MyPageFragment.newInstance());
                mTvTitle.setText(getString(R.string.action_my_info));
                break;
        }
    }

    private int getSelectedButtonId() {
        int id = 0;

        for (View dayView : mBtnMenu) {
            if (dayView.isSelected()) {
                id = dayView.getId();
                break;
            }
        }
        return id;
    }

    @OnClick({R.id.btn_home, R.id.btn_write, R.id.btn_my})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_home:
                selectButton(MENU_HOME);
                break;
            case R.id.btn_write:
                selectButton(MENU_WRITE);
                break;
            case R.id.btn_my:
                selectButton(MENU_MY);
                break;
        }
    }
}
