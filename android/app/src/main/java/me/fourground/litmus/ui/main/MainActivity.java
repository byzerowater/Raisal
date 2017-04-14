package me.fourground.litmus.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.ui.app.AppListFragment;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.mypage.MyPageFragment;
import me.fourground.litmus.ui.signin.SignInActivity;
import me.fourground.litmus.ui.write.app.WriteAppAppraisalActivity;
import me.fourground.litmus.util.FragmentHelper;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MainActivity extends BaseActivity {

    private static final int MENU_HOME = 0;
    private static final int MENU_WRITE = 1;
    private static final int MENU_MY = 2;

    private static final int REQUEST_CODE_MY_PAGE = 0x10;
    private static final int REQUEST_CODE_WRITE = 0x11;


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
        selectHomeMenu();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.i("onActivityResult %s %s", resultCode, requestCode);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_WRITE:
                    selectButton(MENU_WRITE);
                    break;
                case REQUEST_CODE_MY_PAGE:
                    selectButton(MENU_MY);
                    break;
            }
        }
    }

    public void selectHomeMenu() {
        selectButton(MENU_HOME);
    }


    private void selectButton(int menuType) {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            switch (menuType) {
                case MENU_WRITE:
                    startActivityForResult(SignInActivity.getStartIntent(MainActivity.this, true), REQUEST_CODE_WRITE);
                    return;
                case MENU_MY:
                    startActivityForResult(SignInActivity.getStartIntent(MainActivity.this, true), REQUEST_CODE_MY_PAGE);
                    return;
            }

        }

        if (menuType != MENU_WRITE) {
            int size = mBtnMenu.length;

            for (int i = 0; i < size; i++) {
                mBtnMenu[i].setSelected(menuType == i);
            }
        }


        switch (menuType) {
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
