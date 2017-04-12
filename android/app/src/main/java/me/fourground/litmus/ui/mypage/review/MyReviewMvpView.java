package me.fourground.litmus.ui.mypage.review;


import java.util.List;

import me.fourground.litmus.data.model.MyReviewData;
import me.fourground.litmus.ui.base.MvpView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public interface MyReviewMvpView extends MvpView {

    void onReviewList(List<MyReviewData> datas);
}
