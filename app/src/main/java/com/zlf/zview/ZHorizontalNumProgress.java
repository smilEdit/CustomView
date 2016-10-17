package com.zlf.zview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * @创建者 zlf
 * @创建时间 2016/10/17 15:38
 */


public class ZHorizontalNumProgress extends ProgressBar {
    private static final int DEFAULT_TEXT_SIZE                     = 10;
    private static final int DEFAULT_TEXT_COLOR                    = Color.rgb(66, 145, 241);
    private static final int DEFAULT_COLOR_UNREACHED_COLOR         = Color.rgb(204, 204, 204);
    private static final int DEFAULT_COLOR_REACHED_COLOR           = Color.rgb(66, 145, 241);
    private static final int DEFAULT_HEIGHT_REACHED_PROGRESS_BAR   = 4;
    private static final int DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR = 4;
    private static final int DEFAULT_SIZE_TEXT_OFFSET              = 10;
    private static final int PROGRESS_TEXT_VISIBLE                 = 0;

    private int   mTextColor;
    private int   mUnreachColor;
    private int   mReachColor;
    private float mUnreachBarHeight;
    private float mReachBarHeight;
    private float mTextSize;
    private float mTextOffset;
    private Paint mReachedBarPaint;
    private Paint mUnreachedBarPaint;
    private Paint mTextPaint;
    private float mRealWidth;

    private boolean mIfDrawText         = true;
    private boolean mIfDrawUnreachedBar = true;

    public ZHorizontalNumProgress(Context context) {
        this(context, null);
    }

    public ZHorizontalNumProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZHorizontalNumProgress(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZHorizontalNumProgress, defStyle, 0);
        //属性获取
        mTextColor = a.getColor(R.styleable.ZHorizontalNumProgress_progress_text_color, DEFAULT_TEXT_COLOR);
        mTextSize = a.getDimension(R.styleable.ZHorizontalNumProgress_progress_text_size, DEFAULT_TEXT_SIZE);
        mTextOffset = a.getDimension(R.styleable.ZHorizontalNumProgress_progress_text_offset, DEFAULT_SIZE_TEXT_OFFSET);
        mUnreachColor = a.getColor(R.styleable.ZHorizontalNumProgress_progress_unreached_color, DEFAULT_COLOR_UNREACHED_COLOR);
        mUnreachBarHeight = a.getDimension(R.styleable.ZHorizontalNumProgress_progress_reached_bar_height, DEFAULT_HEIGHT_UNREACHED_PROGRESS_BAR);
        mReachColor = a.getColor(R.styleable.ZHorizontalNumProgress_progress_reached_color, DEFAULT_COLOR_REACHED_COLOR);
        mReachBarHeight = a.getDimension(R.styleable.ZHorizontalNumProgress_progress_unreached_bar_height, DEFAULT_HEIGHT_REACHED_PROGRESS_BAR);
        int textVisible = a.getInt(R.styleable.ZHorizontalNumProgress_progress_text_visibility, VISIBLE);
        if (textVisible != VISIBLE) {
            mIfDrawText = false;
        }
        a.recycle();
        initPainters();
    }

    private void initPainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
//    }
//
//    //当用户没有明确指定宽高的时候 根据padding和进度条宽度算出
//    private int measure(int measureSpec, boolean isWidth) {
//        int result;
//        int mode = MeasureSpec.getMode(measureSpec);
//        int size = MeasureSpec.getSize(measureSpec);
//        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
//        if (mode == MeasureSpec.EXACTLY) {
//            result = size;
//        } else {
//            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
//            result += padding;
//            if (mode == MeasureSpec.AT_MOST) {
//                if (isWidth) {
//                    result = Math.max(result, size);
//                } else {
//                    result = Math.min(result, size);
//                }
//            }
//        }
//        return result;
//    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec)
    {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode != MeasureSpec.EXACTLY)
        {

            float textHeight = (mTextPaint.descent() + mTextPaint.ascent());
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + Math
                    .max(Math.max(mReachBarHeight,
                            mUnreachBarHeight), Math.abs(textHeight)));

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
//
//    @Override
//    protected int getSuggestedMinimumWidth() {
//        return (int) mTextSize;
//    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) mTextSize, Math.max((int) mReachBarHeight, (int) mUnreachBarHeight));
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        //平移画笔到指定位置
        canvas.translate(getPaddingLeft(), getHeight() / 2);

        //当前进度条的比例
        float radio = getProgress() * 1.0f / getMax();

        //当前进度条宽度
        float progressWidth = (int) (mRealWidth * radio);

        //绘制的文字
        String text = getProgress() + "%";

        //获取字体的宽高
        float textWidth = mTextPaint.measureText(text);
        float textHeight = (mTextPaint.descent() + mTextPaint.ascent()) / 2;

        //绘制已到达进度条
        if (progressWidth + textWidth / 2 > 0) {
            mReachedBarPaint.setColor(mTextColor);
            mReachedBarPaint.setStrokeWidth(mReachBarHeight);
            canvas.drawLine(0, 0, progressWidth - textWidth / 2, 0, mReachedBarPaint);
        }

        //绘制文字
        if (mIfDrawText) {
            mTextPaint.setColor(mTextColor);
            canvas.drawText(text, progressWidth - textWidth / 2, -textHeight, mTextPaint);
        }

        //如果到达最后就不会绘制未到达进度条
        if (progressWidth + textWidth > mRealWidth) {
            progressWidth = mRealWidth - textWidth;
            mIfDrawUnreachedBar = false;
        }

        //绘制未到达的进度条
        if (mIfDrawUnreachedBar) {
            float start = progressWidth + mTextOffset / 2 + textWidth;
            mUnreachedBarPaint.setColor(mUnreachColor);
            mUnreachedBarPaint.setStrokeWidth(mUnreachBarHeight);
            canvas.drawLine(start-textWidth/2, 0, mRealWidth, 0, mUnreachedBarPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mRealWidth = w - getPaddingRight() - getPaddingLeft();

    }
}
