package me.fourground.raisal.ui.write.review;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.fourground.raisal.R;
import me.fourground.raisal.util.ListUtil;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 점수 Adapter
 */
public class PointAdapter extends RecyclerView.Adapter<PointAdapter.PointHolder> {

    /**
     * 리뷰 데이터들
     */
    private List<String> mPointDatas;

    /**
     * 리뷰 Adapter
     */
    @Inject
    public PointAdapter() {
        this.mPointDatas = new ArrayList<>();
    }


    @Override
    public PointHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PointHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_point, parent, false));
    }

    @Override
    public void onBindViewHolder(PointHolder holder, int position) {
        Context context = holder.itemView.getContext();
        String s = mPointDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mPointDatas);
    }

    public String getItem(int position) {
        int itemCount = getItemCount();
        String item = null;

        if (itemCount > position && position < 0) {
            item = mPointDatas.get(position);
        }

        return item;
    }

    public void setPointDatas(List<String> pointDatas) {
        mPointDatas = pointDatas;
    }


    /**
     * 점수 Holder
     */
    class PointHolder extends ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_explanation)
        TextView mTvExplanation;
        @BindView(R.id.rb_point_design)
        RatingBar mRbPointDesign;

        public PointHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}