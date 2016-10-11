package com.zlf.zview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @创建者 zlf
 * @创建时间 2016/10/11 14:30
 */

public class ZProgressBar extends View {

    /**
     * 第一圈的颜色
     */
    private int mFirstColor;
    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;

    /**
     * 当前进度
     */
    private int mProgress;

    /**
     * 速度
     */
    private int mSpeed;

    /**
     * 画笔
     */
    private Paint mPaint;

    private boolean isNext = false;
    private Paint mTextPain;


    public ZProgressBar(Context context) {
        this(context, null);
    }

    public ZProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZProgressBar, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ZProgressBar_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.ZProgressBar_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.ZProgressBar_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 24, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ZProgressBar_speed:
                    mSpeed = typedArray.getInt(attr, 20);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();

        mTextPain = new Paint();
        mTextPain.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics()));

        //绘制线程
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        isNext = !isNext;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(mSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //圆心x轴坐标
        int centre = getWidth() / 2;
        //半径
        int radius = centre - mCircleWidth / 2;
        //设置圆环的宽度
        mPaint.setStrokeWidth(mCircleWidth);
        //消除锯齿
        mPaint.setAntiAlias(true);
        //空心
        mPaint.setStyle(Paint.Style.STROKE);
        // 定义圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);
        if (!isNext) {
            //第一圈转完 第二圈转

            mPaint.setColor(mFirstColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mSecondColor);
            //画圆弧
            mTextPain.setColor(mSecondColor);
            canvas.drawText(((int) (mProgress / 3.6)) + "%", centre - 36, centre + 20, mTextPain);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        } else {
            mPaint.setColor(mSecondColor);
            canvas.drawCircle(centre, centre, radius, mPaint);
            mPaint.setColor(mFirstColor);
            //画圆弧
            //            Log.i("文字",(int)mProgress+"");
            mTextPain.setColor(mFirstColor);
            canvas.drawText(((int) (mProgress / 3.6)) + "%", centre - 36, centre + 20, mTextPain);
            canvas.drawArc(oval, -90, mProgress, false, mPaint);
        }
    }
}
