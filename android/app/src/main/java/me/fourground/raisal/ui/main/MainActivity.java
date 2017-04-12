package me.fourground.raisal.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseActivity;
import me.fourground.raisal.ui.mypage.MyPageFragment;
import me.fourground.raisal.ui.app.AppListFragment;
import me.fourground.raisal.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.raisal.util.FragmentHelper;

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
    Button[] mBtnMenu;

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

        int size = mBtnMenu.length;

        for (int i = 0; i < size; i++) {
            mBtnMenu[i].setSelected(position == i);
        }

        switch (position) {
            case MENU_HOME:
                FragmentHelper.addFragment(R.id.fl_content, MainActivity.this, AppListFragment.newInstance());
                break;
            case MENU_WRITE:
                startActivity(WriteAppAppraisalActivity.getStartIntent(MainActivity.this));
                break;
            case MENU_MY:
                FragmentHelper.addFragment(R.id.fl_content, MainActivity.this, MyPageFragment.newInstance());
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
