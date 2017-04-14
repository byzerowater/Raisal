package me.fourground.litmus.ui.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.fourground.litmus.R;
import me.fourground.litmus.data.BusEvent;
import me.fourground.litmus.data.model.AppInfoData;
import me.fourground.litmus.data.model.ReviewData;
import me.fourground.litmus.ui.base.BaseFragment;
import me.fourground.litmus.ui.content.ContentActivity;
import me.fourground.litmus.ui.dialog.LoadingDialog;
import me.fourground.litmus.ui.signin.SignInActivity;
import me.fourground.litmus.ui.views.LinearRecyclerView;
import me.fourground.litmus.ui.write.review.WriteReviewActivity;
import me.fourground.litmus.util.LoadingHelper;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class AppListFragment extends BaseFragment implements AppListMvpView {

    private static final int CLICK_CONTENT = 1;
    private static final int CLICK_WRITE = 2;

    private static final int REQUEST_CODE_CONTENT = 0x15;
    private static final int REQUEST_CODE_WRITE = 0x16;

    @Inject
    Bus mEventBus;
    @Inject
    AppListPresenter mReviewListPresenter;
    @Inject
    AppAdapter mAppAdapter;
    @Inject
    LoadingDialog mLoadingDialog;
    @Inject
    LoadingHelper mLoadingHelper;

    @BindView(R.id.rv_app)
    LinearRecyclerView mRvApp;
    Unbinder unbinder;

    private AppInfoData mClickAppInfoData = null;

    public static Fragment newInstance() {
        Fragment fragment = new AppListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        fragmentComponent().inject(this);
        mEventBus.register(this);
        mReviewListPresenter.attachView(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRvApp.setAdapter(mAppAdapter);

        mAppAdapter.setOnAppItemClickListener(new AppAdapter.OnAppItemClickListener() {
            @Override
            public void onAppItemClick(AppInfoData appItem) {
                clickButton(appItem, CLICK_CONTENT);
            }

            @Override
            public void onWriteItemClick(AppInfoData appItem) {
                clickButton(appItem, CLICK_WRITE);
            }
        });

        mLoadingHelper.setOnLoadingListener(() -> {
            mReviewListPresenter.getAppList(false);
        });

        mLoadingHelper.setRecyclerView(mRvApp);

        mReviewListPresenter.getAppList(true);
    }

    private void clickButton(AppInfoData appItem, int clickType) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mClickAppInfoData = appItem;

        if (currentUser == null) {
            switch (clickType) {
                case CLICK_CONTENT:
                    startActivityForResult(SignInActivity.getStartIntent(getActivity(), true), REQUEST_CODE_CONTENT);
                    break;
                case CLICK_WRITE:
                    startActivityForResult(SignInActivity.getStartIntent(getActivity(), true), REQUEST_CODE_WRITE);
                    break;
            }
        } else {
            switch (clickType) {
                case CLICK_CONTENT:
                    startActivity(ContentActivity.getStartIntent(getActivity(), appItem.getAppId()));
                    break;
                case CLICK_WRITE:
                    startActivity(WriteReviewActivity.getStartIntent(getActivity(), appItem));
                    break;
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_WRITE:
                    clickButton(mClickAppInfoData, CLICK_WRITE);
                    break;
                case REQUEST_CODE_CONTENT:
                    clickButton(mClickAppInfoData, CLICK_CONTENT);
                    break;
            }
        }
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
        getActivity().finish();
    }

    @Subscribe
    public void onRegisterCompleted(BusEvent.RegisterAppCompleted event) {
        AppInfoData appInfoData = event.getAppInfoData();

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

    @Subscribe
    public void onSignInCompleted(BusEvent.SignInCompleted event) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            mAppAdapter.setUid(currentUser.getUid());
            mAppAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onSignOutCompleted(BusEvent.SignOutCompleted event) {
        mAppAdapter.setUid(null);
        mAppAdapter.notifyDataSetChanged();
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
