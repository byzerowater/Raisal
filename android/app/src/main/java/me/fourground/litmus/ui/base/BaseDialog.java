package me.fourground.litmus.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import me.fourground.litmus.R;


/**
 * Created by YoungSoo Kim on 2017-02-18.
 * 4ground Ltd
 * byzerowater@gmail.com
 * BaseDialog
 * 투명한 배경 적용
 */
public class BaseDialog extends Dialog {

    /**
     * BaseDialog
     * 투명한 배경 적용
     *
     * @param context Context
     */
    protected BaseDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.color.transparent_000000_00);
        }
    }
}
