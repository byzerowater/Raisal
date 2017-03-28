package me.fourground.raisal.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import me.fourground.raisal.R;


/**
 * Created by YoungSoo Kim on 2016-08-26.
 * company Ltd
 * youngsoo.kim@yap.net
 * LinearRecyclerView
 */
public class LinearRecyclerView extends RecyclerView {
    /**
     * LinearRecyclerView
     *
     * @param context Context
     */
    public LinearRecyclerView(Context context) {
        this(context, null);
    }

    /**
     * LinearRecyclerView
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    public LinearRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * LinearRecyclerView
     *
     * @param context  Context
     * @param attrs    AttributeSet
     * @param defStyle defStyle
     */
    public LinearRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    /**
     * 레이아웃 초기 설정
     *
     * @param context Context
     * @param attrs   AttributeSet
     */
    private void init(Context context, AttributeSet attrs) {
        int orientation = context.getResources().getInteger(R.integer.recycler_linear_vertical);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LinearRecyclerView,
                    0, 0);

            try {
                orientation = a.getInteger(R.styleable.LinearRecyclerView_orientation, orientation);
            } finally {
                a.recycle();
            }
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        if (orientation == context.getResources().getInteger(R.integer.recycler_linear_horizontal)) {
            linearLayoutManager.setOrientation(HORIZONTAL);
        }
        setLayoutManager(linearLayoutManager);
    }

}
