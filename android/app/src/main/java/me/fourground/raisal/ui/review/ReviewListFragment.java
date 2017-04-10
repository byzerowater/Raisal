package me.fourground.raisal.ui.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fourground.raisal.R;
import me.fourground.raisal.data.BusEvent;
import me.fourground.raisal.data.model.AppInfoData;
import me.fourground.raisal.data.model.ReviewData;
import me.fourground.raisal.ui.base.BaseFragment;
import me.fourground.raisal.ui.common.AppAdapter;
import me.fourground.raisal.ui.content.ContentActivity;
import me.fourground.raisal.ui.dialog.LoadingDialog;
import me.fourground.raisal.ui.views.LinearRecyclerView;
import me.fourground.raisal.ui.write.review.WriteReviewActivity;
import me.fourground.raisal.util.LoadingHelper;
import timber.log.Timber;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ReviewListFragment extends BaseFragment implements ReviewListMvpView {

    @Inject
    Bus mEventBus;
    @Inject
    ReviewListPresenter mReviewListPresenter;
    @Inject
    AppAdapter mAppAdapter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    LoadingHelper mLoadingHelper;

    @BindView(R.id.rv_app)
    LinearRecyclerView mRvApp;
    Unbinder unbinder;

    public static Fragment newInstance() {
        Fragment fragment = new ReviewListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        fragmentComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        unbinder = ButterKnife.bind(this, view);
        mEventBus.register(this);
        mReviewListPresenter.attachView(this);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRvApp.setAdapter(mAppAdapter);

        mAppAdapter.setOnOrderItemClickListener(new AppAdapter.OnAppItemClickListener() {
            @Override
            public void onAppItemClick(AppInfoData appItem) {
                startActivity(ContentActivity.getStartIntent(getActivity(), appItem.getAppId()));
            }

            @Override
            public void onWriteItemClick(AppInfoData appItem) {
                startActivity(WriteReviewActivity.getStartIntent(getActivity(), appItem));
            }
        });

        mLoadingHelper.setOnLoadingListener(() -> {
            mReviewListPresenter.getAppList(false);
        });

        mLoadingHelper.setRecyclerView(mRvApp);

        mReviewListPresenter.getAppList(true);
    }

    @Override
    public void showProgress(boolean isShow) {
        if (isShow) {
            mLoadingDialog.show();
        } else {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onError() {

    }

    @Subscribe
    public void onRegisterCompleted(BusEvent.RegisterAppCompleted event) {
        AppInfoData appInfoData = event.getAppInfoData();

        Timber.i("onRegisterCompleted " + appInfoData.toString());
        mAppAdapter.addAppData(appInfoData);
        mAppAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onRegisterReviewCompleted(BusEvent.RegisterReviewCompleted event) {
        ReviewData reviewData = event.getReviewData();

        int appInfoPosition = mAppAdapter.getAppInfoPosition(reviewData.getAppId());
        if (appInfoPosition != -1) {
            AppInfoData appInfo = mAppAdapter.getItem(appInfoPosition);
            appInfo.setNPartyUserCount(appInfo.getNPartyUserCount() + 1);
            mAppAdapter.notifyItemChanged(appInfoPosition);
        }
    }

    @Override
    public void onAppList(List<AppInfoData> datas) {
        mLoadingHelper.setNextPage(mReviewListPresenter.getNextUrl());
        mLoadingHelper.setLoading(false);

        mAppAdapter.addAppDatas(datas);
        mAppAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mReviewListPresenter.detachView();
        mEventBus.unregister(this);
    }
}
