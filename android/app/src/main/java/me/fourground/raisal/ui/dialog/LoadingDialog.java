package me.fourground.raisal.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.ui.base.BaseDialog;

/**
 * Created by YoungSoo Kim on 2017-02-09.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 로딩 다이얼로그
 */
public class LoadingDialog extends BaseDialog {

    /**
     * 로딩 ProgressBar
     */
    @BindView(R.id.pb_loading)
    ProgressBar mPbLoading;

    /**
     * 로딩 다이얼로그
     *
     * @param context Context
     */
    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getWindow() != null) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        setContentView(R.layout.dialog_loading);
        ButterKnife.bind(this);
        setCancelable(false);
    }

    @Override
    public void show() {
        try {
            super.show();
            mPbLoading.setIndeterminate(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
            if (mPbLoading != null) {
                mPbLoading.setIndeterminate(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
