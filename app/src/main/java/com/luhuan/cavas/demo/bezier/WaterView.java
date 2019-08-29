package com.luhuan.cavas.demo.bezier;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class WaterView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mWidth;
    private int mHeight;

    private float water;//一上一下一个波纹长
    private float disX;//偏移距离

    public WaterView(Context context) {
        this(context, null);
    }

    public WaterView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public WaterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLUE);
        mPath = new Path();
        water = getContext().getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        water = mWidth / 7 * 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-water + disX, (float) (mHeight / 2));
        float i = -water;
        while (i < mWidth + water) {
            mPath.rQuadTo(water / 4, -50, water / 2f, 0);
            mPath.rQuadTo(water / 4, 50, water / 2f, 0);
            i += water;
        }
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    public void startAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animator -> {
            float factor= (float) animator.getAnimatedValue();
            disX = factor*water;
            invalidate();
        });
        valueAnimator.setTarget(this);
        valueAnimator.start();
    }

}
