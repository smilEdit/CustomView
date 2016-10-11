package com.zlf.zview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * @创建者 zlf
 * @创建时间 2016/10/11 17:08
 */

public class ZVolumControlBar extends View {

    private Bitmap mBg;
    private int    mSplitSize;
    private int    mKuaiCount;
    private int    mFirstColor;
    private int    mSecondColor;
    private int    mCircleWidth;
    private Paint  mPaint;
    private Rect   mRect;
    private int mCurrentCount = 4;

    public ZVolumControlBar(Context context) {
        this(context, null);
    }

    public ZVolumControlBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZVolumControlBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //又是获取属性 再释放资源
        Log.i("vcsvcsvcsvcs","yyyyyyyyyyyyy");
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZVolumControlBar, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ZVolumControlBar_bg:
                    mBg = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.ZVolumControlBar_splitSize:
                    mSplitSize = typedArray.getInt(attr, 20);
                    break;
                case R.styleable.ZVolumControlBar_firstColor:
                    mFirstColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.ZVolumControlBar_secondColor:
                    mSecondColor = typedArray.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.ZVolumControlBar_circleWidth:
                    mCircleWidth = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.ZVolumControlBar_kuaiCount:
                    mKuaiCount = typedArray.getInt(attr, 20);
                    break;
            }
        }
        //释放
        typedArray.recycle();

        mPaint = new Paint();

        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i("onDraw","xxxxxxxxxxxxxxxxxxxxxx");
        //消除锯齿
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mCircleWidth);
        //线段头是圆的
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        //画笔设置成空心的
        mPaint.setStyle(Paint.Style.STROKE);
        //圆心x轴坐标和半径
        int centre = getWidth() / 2;
        int radius = centre - mCircleWidth / 2;
        //画一个圆
        drawOval(canvas, centre, radius);

        /**
         * 计算内切正方形的位置
         */
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径
        /**
         * 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        /**
         * 内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
         */
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);

        /**
         * 如果图片比较小，那么根据图片的尺寸放置到正中心
         */
        if (mBg.getWidth() < Math.sqrt(2) * relRadius) {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mBg.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mBg.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mBg.getWidth());
            mRect.bottom = (int) (mRect.top + mBg.getHeight());

        }
        // 绘图
        canvas.drawBitmap(mBg, null, mRect, mPaint);
    }

    /**
     * 画间断的圆
     *
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, int radius) {

        //根据块数画圆弧
        float itemSize = (360 * 1.0f - mKuaiCount * mSplitSize) / mKuaiCount;

        //定义的圆弧的形状和大小的界限
        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius);

        mPaint.setColor(mFirstColor);

        for (int i = 0; i < mKuaiCount; i++) {
            // 根据块数的大小画小块
            canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }

        mPaint.setColor(mSecondColor);

        for (int i = 0; i < mCurrentCount; i++) {
            canvas.drawArc(oval, i * (itemSize + mSplitSize), itemSize, false, mPaint);
        }
    }

    /**
     * 当前数量+1
     */
    public void up() {
        mCurrentCount++;
        postInvalidate();
    }

    /**
     * 当前数量-1
     */
    public void down() {
        mCurrentCount--;
        postInvalidate();
    }

    private int xDown, xUp;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getY();
                break;

            case MotionEvent.ACTION_UP:
                xUp = (int) event.getY();
                // 下滑
                if (xUp > xDown) {
                    down();
                } else {
                    up();
                }
                break;
        }

        return true;
    }
}
