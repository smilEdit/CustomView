package com.zlf.zview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * @创建者 zlf
 * @创建时间 2016/10/11 13:48
 */

public class ZImageView extends View {

    private Bitmap mImage;
    private int    mImageScale;
    private String mTitle;
    private int    mTextColor;
    private Rect   mRect;
    private Paint  mPaint;
    private Rect   mTextBound;
    private int    mTextSize;

    private int mWidth;
    private int mHeight;

    public ZImageView(Context context) {
        this(context, null);
    }

    public ZImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //都是先获取属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ZImageView, defStyle, 0);
        int count = typedArray.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ZImageView_image:
                    mImage = BitmapFactory.decodeResource(getResources(), typedArray.getResourceId(attr, 0));
                    break;
                case R.styleable.ZImageView_imageScaleType:
                    mImageScale = typedArray.getInt(attr, 0);
                    break;
                case R.styleable.ZImageView_titleText:
                    mTitle = typedArray.getString(attr);
                    break;
                case R.styleable.ZImageView_titleTextColor:
                    mTextColor = typedArray.getColor(attr, Color.GREEN);
                    break;
                case R.styleable.ZImageView_titleTextSize:
                    mTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        //释放
        typedArray.recycle();
        /**
         * 获取绘制宽 和 高
         */

        mRect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();
        mPaint.setTextSize(mTextSize);

        //计算了解绘制字体需要的范围
        mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        {
            // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            /**
             * 设置宽度
             */
            int specMode = MeasureSpec.getMode(widthMeasureSpec);
            int specSize = MeasureSpec.getSize(widthMeasureSpec);

            if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
            {
                mWidth = specSize;
            } else {
                // 由图片决定的宽
                int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
                // 由字体决定的宽
                int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();
                // wrap_content
                if (specMode == MeasureSpec.AT_MOST) {
                    int desire = Math.max(desireByImg, desireByTitle);
                    mWidth = Math.min(desire, specSize);

                }
            }
            /***
             * 设置高度
             */
            specMode = MeasureSpec.getMode(heightMeasureSpec);
            specSize = MeasureSpec.getSize(heightMeasureSpec);
            // match_parent , accurate
            if (specMode == MeasureSpec.EXACTLY) {
                mHeight = specSize;
            } else {
                int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
                // wrap_content
                if (specMode == MeasureSpec.AT_MOST) {
                    mHeight = Math.min(desire, specSize);
                }
            }
            setMeasuredDimension(mWidth, mHeight);

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        {
            // super.onDraw(canvas);
            /**
             * 边框
             */
            mPaint.setStrokeWidth(4);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.CYAN);
            canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

            mRect.left = getPaddingLeft();
            mRect.right = mWidth - getPaddingRight();
            mRect.top = getPaddingTop();
            mRect.bottom = mHeight - getPaddingBottom();

            mPaint.setColor(mTextColor);
            mPaint.setStyle(Paint.Style.FILL);
            /**
             * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
             */
            if (mTextBound.width() > mWidth) {
                TextPaint paint = new TextPaint(mPaint);
                String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                        TextUtils.TruncateAt.END).toString();
                canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

            } else {
                //正常情况，将字体居中
                canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
            }

            //取消使用掉的快
            mRect.bottom -= mTextBound.height();
            //IMAGE_SCALE_FITXY
            if (mImageScale == MeasureSpec.AT_MOST) {
                canvas.drawBitmap(mImage, null, mRect, mPaint);
            } else {
                //计算居中的矩形范围
                mRect.left = mWidth / 2 - mImage.getWidth() / 2;
                mRect.right = mWidth / 2 + mImage.getWidth() / 2;
                mRect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
                mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;

                canvas.drawBitmap(mImage, null, mRect, mPaint);
            }

        }
    }
}
