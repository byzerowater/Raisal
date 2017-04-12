package me.fourground.litmus.ui.write.review;

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
import me.fourground.litmus.R;
import me.fourground.litmus.data.model.PointData;
import me.fourground.litmus.data.model.PointViewData;
import me.fourground.litmus.util.ListUtil;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 점수 Adapter
 */
public class WritePointAdapter extends RecyclerView.Adapter<WritePointAdapter.PointHolder> {

    /**
     * 점수 데이터들
     */
    private List<PointViewData> mPointDatas;

    /**
     * 점수 Adapter
     */
    @Inject
    public WritePointAdapter() {
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
        PointViewData pointData = mPointDatas.get(position);

        holder.mTvTitle.setText(pointData.getTitle());
        holder.mRbPointDesign.setRating(pointData.getPoint());

        holder.mRbPointDesign.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            pointData.setPoint(rating);
        });

    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mPointDatas);
    }

    public PointViewData getItem(int position) {
        int itemCount = getItemCount();
        PointViewData item = null;

        if (itemCount > position && !(position < 0)) {
            item = mPointDatas.get(position);
        }

        return item;
    }

    public void addPointDatas(List<PointViewData> pointDatas) {
        mPointDatas.addAll(pointDatas);
    }

    public PointData getPoint() {
        PointData pointData = new PointData();
        pointData.setDesign(getItem(0).getPoint());
        pointData.setUseful(getItem(1).getPoint());
        pointData.setContents(getItem(2).getPoint());
        pointData.setSatisfaction(getItem(3).getPoint());

        return pointData;
    }


    /**
     * 점수 Holder
     */
    static class PointHolder extends ViewHolder {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.rb_point)
        RatingBar mRbPointDesign;

        public PointHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}