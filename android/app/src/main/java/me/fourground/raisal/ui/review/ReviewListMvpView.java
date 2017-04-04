package me.fourground.raisal.ui.review;


import java.util.List;

import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.ui.base.MvpView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public interface ReviewListMvpView extends MvpView {

    void onAppList(List<AppInfoData> datas);
}
