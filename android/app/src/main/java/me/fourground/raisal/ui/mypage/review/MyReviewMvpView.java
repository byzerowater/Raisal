package me.fourground.raisal.ui.mypage.review;


import java.util.List;

import me.fourground.raisal.data.model.MyReviewData;
import me.fourground.raisal.ui.base.MvpView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public interface MyReviewMvpView extends MvpView {

    void onReviewList(List<MyReviewData> datas);
}
