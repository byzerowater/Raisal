package me.fourground.litmus.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import me.fourground.litmus.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by YoungSoo Kim on 2016-06-17.
 * 4ground Ltd
 * byzerowater@gmail.com
 * 이미지 로드 유틸
 */
@Singleton
public class ImageLoader {
    /**
     * Instance
     */
    private static ImageLoader ourInstance = null;
    /**
     * Context
     */
    private Context mContext = null;

    /**
     * 이미지 로드 유틸
     *
     * @param context Context
     */
    @Inject
    public ImageLoader(@ApplicationContext Context context) {
        mContext = context;
    }

    /**
     * 이미지 로드
     *
     * @param url       경로
     * @param imageView 보여질 뷰
     */
    public void load(String url, ImageView imageView) {
        Glide.with(mContext).load(url).into(imageView);
    }

    /**
     * 이미지 로드
     *
     * @param url            경로
     * @param imageView      보여질 뷰
     * @param transformation 이미지 변경
     * Crop
     * CropTransformation, CropCircleTransformation, CropSquareTransformation, RoundedCornersTransformation
     * Color
     * ColorFilterTransformation, GrayscaleTransformation
     * Blur
     * BlurTransformation
     * Mask
     * MaskTransformation
     */
    public void load(String url, ImageView imageView, Transformation transformation) {
        Glide.with(mContext).load(url).bitmapTransform(transformation).into(imageView);
    }

}
