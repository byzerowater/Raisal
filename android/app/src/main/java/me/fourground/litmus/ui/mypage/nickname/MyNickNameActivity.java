package me.fourground.litmus.ui.mypage.nickname;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.fourground.litmus.R;
import me.fourground.litmus.ui.base.BaseActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.write.Checker;
import me.fourground.litmus.util.StringUtil;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class MyNickNameActivity extends BaseActivity implements MyNickNameMvpView, Checker {

    private static final String VALID_NICK_NAME_REGEX = "^[a-zA-Z0-9ㄱ-ㅎ가-힣]*$";


    @Inject
    MyNickNamePresenter mMyNickNamePresenter;
    @Inject
    LoadingDialog mLoadingDialog;
    @BindView(R.id.btn_back)
    ImageButton mBtnBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.et_nickname)
    TextInputEditText mEtNickname;
    @BindView(R.id.til_nickname)
    TextInputLayout mTilNickname;

    /**
     * MyAppActivity 가져오기
     *
     * @param context Context
     * @return MyAppActivity Intent
     */
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MyNickNameActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_my_nickname);
        ButterKnife.bind(this);
        mMyNickNamePresenter.attachView(this);
        mTvTitle.setText(getString(R.string.action_change_nickname));
        mBtnBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyNickNamePresenter.detachView();
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {

    }

    @OnClick({R.id.btn_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_confirm:
                if (checkInputText()) {
                    mMyNickNamePresenter.updateNickName(mEtNickname.getText().toString());
                }

                break;
        }
    }

    @Override
    public boolean checkInputText() {


        boolean isPass = true;
        String nickName = mEtNickname.getText().toString();

        Matcher matcher = Pattern.compile(VALID_NICK_NAME_REGEX).matcher(nickName);

        if (StringUtil.isEmpty(nickName) || !matcher.find()) {
            isPass = false;
            mTilNickname.setErrorEnabled(true);
            mTilNickname.setError(getString(R.string.text_error_nickname));
        } else {
            mTilNickname.setError("");
            mTilNickname.setErrorEnabled(false);
        }



        return isPass;
    }

    @Override
    public void onComplete() {
        finish();
    }
}
