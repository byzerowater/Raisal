package me.fourground.raisal.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.global.yap.tablet.R;
import com.global.yap.tablet.common.Const;
import com.global.yap.tablet.data.model.OrderData;
import com.global.yap.tablet.ui.order.OnOrderItemClickListener;
import com.global.yap.tablet.util.DateUtil;
import com.global.yap.tablet.util.ListUtil;
import com.global.yap.tablet.util.StringUtil;
import com.global.yap.tablet.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YoungSoo Kim on 2016-08-11.
 * company Ltd
 * youngsoo.kim@yap.net
 * 주문판 Adapter
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.OrderHolder> {

    /**
     * 주문 데이터들
     */
    private List<OrderData> mOrderDatas;
    /**
     * 주문 아이템 클릭 리스너
     */
    private OnOrderItemClickListener mOnOrderItemClickListener;

    /**
     * 주문판 Adapter
     */
    @Inject
    public ReviewAdapter() {
        this.mOrderDatas = new ArrayList<>();
    }

    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_sheet, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderHolder holder, final int position) {

        Context context = holder.itemView.getContext();
        OrderData orderData = mOrderDatas.get(position);

        String ordrKindCd = orderData.getOrdrKindCd();
        String ordrStusCd = orderData.getOrdrStusCd();

        if (!StringUtil.isEmpty(ordrKindCd)) {
            holder.mTvOrderType.setVisibility(View.VISIBLE);
            Util.setOrderTypeView(context, ordrKindCd, holder.mTvOrderType);
        } else {
            holder.mTvOrderType.setVisibility(View.GONE);
        }

        if (ordrKindCd.contains(Const.OrderType.ORDER_MIDFIX_RESERVE_CD)) {
            holder.mTvOrderReceiveStartTime.setText(context.getString(R.string.order_immediately));
            holder.mLlOrderReceiveEndTime.setVisibility(View.GONE);
        } else {
            holder.mLlOrderReceiveEndTime.setVisibility(View.VISIBLE);
            holder.mTvOrderReceiveStartTime.setText(
                    DateUtil.convertDateFormat(orderData.getExptRcvStaDtm(),
                            Const.SERVER_DATE_FORMAT,
                            Const.TIME_WITH_MERIDIEM_FORMAT));

            holder.mTvOrderReceiveEndTime.setText(
                    DateUtil.convertDateFormat(orderData.getExptRcvDtm(),
                            Const.SERVER_DATE_FORMAT,
                            Const.TIME_WITH_MERIDIEM_FORMAT));
        }

        Util.setOrderStateView(context, orderData.getOrdrStusCd(), holder.mTvOrderState);

        if (Const.ORDER_ACCEPT_STATE.contains(ordrStusCd)) {
            holder.mTvOrderState.setBackgroundResource(R.drawable.btn_popup_orange_normal);
        } else {
            holder.mTvOrderState.setBackgroundResource(R.drawable.gray_bg);
        }

        if (!StringUtil.isEmpty(ordrKindCd)) {
            if (ordrKindCd.startsWith(Const.OrderType.GENERAL)) {
                holder.mTvOrderNo.setTextColor(context.getResources().getColor(R.color.blue_4a90e2_ff));
            } else if (ordrKindCd.startsWith(Const.OrderType.PICKUP)) {
                holder.mTvOrderNo.setTextColor(context.getResources().getColor(R.color.orange_ff5833_ff));
            } else if (ordrKindCd.startsWith(Const.OrderType.DELIVERY)) {
                holder.mTvOrderNo.setTextColor(context.getResources().getColor(R.color.blue_4a90e2_ff));
            }
        }

        holder.mTvOrderNo.setText(orderData.getOrdrSimpleId());

        int ordrCnt = StringUtil.getInt(orderData.getOrdrCnt());
        if (ordrCnt > 1) {
            holder.mTvOrderProduct.setText(
                    StringUtil.fromHtml(context.getString(R.string._order_name_with_count_color, orderData.getRepPrdNm(), ordrCnt - 1)));
        } else {
            holder.mTvOrderProduct.setText(orderData.getRepPrdNm());
        }

        holder.mTvRecipient.setText(orderData.getRcverNm());
        holder.mTvPhone.setText(Util.formatPhoneNumber(context, orderData.getRcverCnct()));

        int passMinute = DateUtil.getPassMinute(orderData.getExptRcvDtm(), Const.SERVER_DATE_FORMAT);

        if (Arrays.asList(Const.OrderPickUpCode.PICK_UP_STAND_BY_STATE).contains(ordrStusCd)
                && passMinute > 0) {
            holder.mTvNoticeTime.setVisibility(View.VISIBLE);
            holder.mTvNoticeTime.setText(context.getString(R.string._order_pick_up_notice_minute, passMinute));

            if (ordrKindCd.startsWith(Const.OrderType.GENERAL)) {

            } else if (ordrKindCd.startsWith(Const.OrderType.PICKUP)) {
                holder.mTvNoticeTime.setText(context.getString(R.string._order_pick_up_notice_minute, passMinute));
            } else if (ordrKindCd.startsWith(Const.OrderType.DELIVERY)) {
                holder.mTvNoticeTime.setText(context.getString(R.string._order_delivery_notice_minute, passMinute));
            }


            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_up_down);
            holder.mTvNoticeTime.startAnimation(animation);
        } else {
            holder.mTvNoticeTime.setVisibility(View.GONE);
            holder.mTvNoticeTime.clearAnimation();
        }

        holder.itemView.setOnClickListener(
                view -> mOnOrderItemClickListener.onOrderItemClick(orderData));
    }

    @Override
    public int getItemCount() {
        return ListUtil.getListCount(mOrderDatas);
    }

    /**
     * 주문 데이터들 설정
     *
     * @param orderDatas 주문 데이터들
     */
    public void setOrderDatas(List<OrderData> orderDatas) {
        mOrderDatas = orderDatas;
    }

    /**
     * 주문 데이터 지우기
     */
    public void clearOrderDatas() {
        mOrderDatas.clear();
    }

    /**
     * 주문 아이템 클릭 리스너 설정
     *
     * @param onOrderItemClickListener 주문 아이템 클릭 리스너
     */
    public void setOnOrderItemClickListener(OnOrderItemClickListener onOrderItemClickListener) {
        mOnOrderItemClickListener = onOrderItemClickListener;
    }

    /**
     * 주문 상태 변경
     *
     * @param data 변경 된 주문 데이터
     */
    public void changeOrderState(OrderData data) {
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            OrderData orderData = mOrderDatas.get(i);
            if (orderData.getOrdrId().equals(data.getOrdrId())) {
                if (Const.ORDER_CANCEL_STATE.equals(data.getOrdrStusCd())
                        || Const.ORDER_COMPLETE_STATE.contains(data.getOrdrStusCd())
                        || Const.OrderPickUpCode.PICK_UP_UNCOMPLETED.equals(data.getOrdrStusCd())
                        || Const.OrderDeliveryCode.DELIVERY_UNCOMPLETED.equals(data.getOrdrStusCd())) {
                    mOrderDatas.remove(i);
                    notifyDataSetChanged();
                } else {
                    mOrderDatas.set(i, data);
                    notifyItemChanged(i);
                }
                break;
            }
        }
    }

    public int getAcceptCount() {
        int acceptCount = 0;

        if (mOrderDatas != null) {
            for (OrderData orderData : mOrderDatas) {
                if (Const.ORDER_ACCEPT_STATE.contains(orderData.getOrdrStusCd())) {
                    acceptCount++;
                }
            }
        }

        return acceptCount;
    }

    public int getProgressCount() {
        return getItemCount() - getAcceptCount();
    }

    /**
     * 주문 Holder
     */
    class OrderHolder extends RecyclerView.ViewHolder {

        /**
         * 주문 타입 TextView
         */
        @BindView(R.id.tv_order_type)
        TextView mTvOrderType;
        /**
         * 주문 번호 TextView
         */
        @BindView(R.id.tv_order_no)
        TextView mTvOrderNo;
        /**
         * 픽업,배달 예정일자 TextView
         */
        @BindView(R.id.tv_order_receive_start_time)
        TextView mTvOrderReceiveStartTime;
        /**
         * 픽업,배달 종료일자 LinearLayout
         */
        @BindView(R.id.ll_order_receive_end_time)
        LinearLayout mLlOrderReceiveEndTime;
        /**
         * 픽업,배달 종료일자 TextView
         */
        @BindView(R.id.tv_order_receive_end_time)
        TextView mTvOrderReceiveEndTime;
        /**
         * 주문 상품 TextView
         */
        @BindView(R.id.tv_order_product)
        TextView mTvOrderProduct;
        /**
         * 수령자 TextView
         */
        @BindView(R.id.tv_recipient)
        TextView mTvRecipient;
        /**
         * 수령자 전화 번호 TextView
         */
        @BindView(R.id.tv_phone)
        TextView mTvPhone;
        /**
         * 주문 상태 TextView
         */
        @BindView(R.id.tv_order_state)
        TextView mTvOrderState;
        /**
         * 상품 픽업 지난 시간 TextView
         */
        @BindView(R.id.tv_notice_time)
        TextView mTvNoticeTime;

        /**
         * 주문 Holder
         *
         * @param itemView view
         */
        public OrderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}