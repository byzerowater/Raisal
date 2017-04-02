package me.fourground.raisal.ui.content;


import java.util.List;

import me.fourground.raisal.data.model.ContentData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.MvpView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public interface ContentMvpView extends MvpView {
    void onContent(ContentData contentData);
    void onReviewList(List<ReviewData> reviewDatas);

}
