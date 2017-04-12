package me.fourground.litmus.ui.app;


import java.util.List;

import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.ui.base.MvpView;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public interface AppListMvpView extends MvpView {

    void onAppList(List<AppInfoData> datas);
}
