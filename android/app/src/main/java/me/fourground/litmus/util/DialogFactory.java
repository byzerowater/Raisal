package me.fourground.litmus.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by YoungSoo Kim on 2016-06-17.
 * 4ground Ltd
 * byzerowater@gmail.com
 * DialogFactory
 * Dialog 관리
 */
public class DialogFactory {

    /**
     * AlertDialog 생성
     *
     * @param context Context
     * @param message 메세지
     * @return AlertDialog
     */
    public static Dialog createDialog(Context context,
                                      CharSequence message) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .create();
    }

    /**
     * AlertDialog 생성
     *
     * @param context          context
     * @param message          메세지
     * @param positiveText     확인 버튼 문구
     * @param positiveListener 확인 버튼 클릭 리스너
     * @return AlertDialog
     */
    public static Dialog createDialog(Context context,
                                      CharSequence message,
                                      CharSequence positiveText,
                                      DialogInterface.OnClickListener positiveListener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveText, positiveListener)
                .create();
    }

    /**
     * AlertDialog 생성
     *
     * @param context          Context
     * @param message          메세지
     * @param negativeText     취소 버튼 문구
     * @param positiveText     확인 버튼 문구
     * @param negativeListener 취소 버튼 클릭 리스너
     * @param positiveListener 확인 버튼 클릭 리스너
     * @return AlertDialog
     */
    public static Dialog createDialog(Context context,
                                      CharSequence message,
                                      CharSequence negativeText,
                                      CharSequence positiveText,
                                      DialogInterface.OnClickListener negativeListener,
                                      DialogInterface.OnClickListener positiveListener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setNegativeButton(negativeText, negativeListener)
                .setPositiveButton(positiveText, positiveListener)
                .create();
    }
}
