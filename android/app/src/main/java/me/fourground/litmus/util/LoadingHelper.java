package me.fourground.litmus.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by YoungSoo Kim on 2016-05-24.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 리사이클뷰 로드 매니저
 */
public class LoadingHelper {

    private static int VISIBLE_THRESHOLD = 5;

    /**
     * 로딩 상태 리스너
     */
    public interface OnLoadingListener {
        /**
         * 로딩 시작
         */
        void onLoadMore();
    }

    /**
     * 로딩 여부
     */
    private boolean mIsLoading = false;
    /**
     * 다음 페이지
     */
    private String mNextPage = null;
    /**
     * 마지막 뷰 아이템 위치
     */
    private int mLastVisibleItem;
    /**
     * 리스트 아이템 수
     */
    private int mTotalItemCount;

    /**
     * RecyclerView
     */
    private RecyclerView mRecyclerView = null;
    /**
     * RecyclerView.LayoutManager
     */
    private RecyclerView.LayoutManager mLayoutManager = null;
    /**
     * 로딩 상태 리스너
     */
    private OnLoadingListener mOnLoadingListener = null;

    /**
     * 리사이클뷰 로드 매니저
     */
    public LoadingHelper() {
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mLayoutManager = mRecyclerView.getLayoutManager();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoading) {
                    mTotalItemCount = mLayoutManager.getItemCount();
                    if (mLayoutManager instanceof LinearLayoutManager) {
                        mLastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    } else if (mLayoutManager instanceof GridLayoutManager) {
                        mLastVisibleItem = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                    }

                    if (mTotalItemCount <= mLastVisibleItem + VISIBLE_THRESHOLD && !StringUtil.isEmpty(mNextPage)) {
                        if (mOnLoadingListener != null) {
                            mOnLoadingListener.onLoadMore();
                        }
                        mIsLoading = true;
                    }
                }
            }
        });
    }

    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        mOnLoadingListener = onLoadingListener;
    }


    public void setLoading(boolean loading) {
        mIsLoading = loading;
    }

    public String getNextPage() {
        return mNextPage;
    }

    public void setNextPage(String nextPage) {
        mNextPage = nextPage;
    }
}
