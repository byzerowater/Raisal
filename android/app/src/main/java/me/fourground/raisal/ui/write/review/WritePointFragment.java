package me.fourground.raisal.ui.write.review;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.fourground.raisal.R;
import me.fourground.raisal.data.model.PointData;
import me.fourground.raisal.data.model.PointViewData;
import me.fourground.raisal.ui.base.BaseFragment;
import me.fourground.raisal.ui.views.LinearRecyclerView;

/**
 * Created by YoungSoo Kim on 2017-03-26.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class WritePointFragment extends BaseFragment {


    @BindView(R.id.rv_points)
    LinearRecyclerView mRvPoints;

    @Inject
    WritePointAdapter mWritePointAdapter;

    Unbinder unbinder;
    @BindView(R.id.et_impression)
    TextInputEditText mEtImpression;

    public static Fragment newInstance() {
        Fragment fragment = new WritePointFragment();
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
        View view = inflater.inflate(R.layout.fragment_wirte_review_point, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<PointViewData> pointViewDatas = new ArrayList<>();

        pointViewDatas.add(
                new PointViewData(getString(R.string.text_design_question)));
        pointViewDatas.add(
                new PointViewData(getString(R.string.text_useful_question)));
        pointViewDatas.add(
                new PointViewData(getString(R.string.text_contents_question)));
        pointViewDatas.add(
                new PointViewData(getString(R.string.text_satisfaction_question)));


        mWritePointAdapter.addPointDatas(pointViewDatas);

        mRvPoints.setAdapter(mWritePointAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {

        PointData point = mWritePointAdapter.getPoint();

        if (point.getContents() == 0
                || point.getDesign() == 0
                || point.getSatisfaction() == 0
                || point.getUseful() == 0) {

            return;
        }

        WriteReviewActivity activity = (WriteReviewActivity) getActivity();
        activity.getRegisterReviewRequest().setAppElement(point);
        activity.getRegisterReviewRequest().setComment(mEtImpression.getText().toString());
        activity.registerApp();
    }
}
