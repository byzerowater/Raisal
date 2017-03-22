package me.fourground.raisal.util;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import me.fourground.raisal.R;


/**
 * Created by YoungSoo Kim on 2016-06-17.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 이미지 로드 유틸
 */
public class DialogFactory {

    public static Dialog createSimpleOkErrorDialog(Context context, String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setNeutralButton(R.string.app_name, null);
        return alertDialog.create();
    }

    public static Dialog createSimpleOkErrorDialog(Context context,
                                                   @StringRes int titleResource,
                                                   @StringRes int messageResource) {

        return createSimpleOkErrorDialog(context,
                context.getString(titleResource),
                context.getString(messageResource));
    }

    public static Dialog createSimpleOkErrorDialog(Context context, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(message)
                .setNeutralButton(R.string.app_name, null);
        return alertDialog.create();
    }

    public static Dialog createSimpleOkErrorDialog(Context context,
                                                   @StringRes int messageResource) {

        return createSimpleOkErrorDialog(context, context.getString(messageResource));
    }

}
