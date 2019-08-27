package com.luhuan.cavas.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PentagonView extends View {
    private int mWidth;
    private int mHeight;
    private Paint mPaint; //绘制点
    private Paint lintPaint;
    private Path linePath;//绘制线
    private Path mPath;

    float circleX = 0;
    float circleY = 0;
    float radius = 0;

    public PentagonView(Context context) {
        this(context, null);
    }

    public PentagonView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public PentagonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        linePath = new Path();
        lintPaint = new Paint();
        lintPaint.setStyle(Paint.Style.STROKE);
        lintPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mWidth = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        circleX = mWidth / 2;
        circleY = mWidth / 2;
        radius = (float) (mWidth / 2 * 0.9);
        for (int i = 0; i < 4; i++) {
            if (i > 0) {
                radius -= 90;
            }
            drawPentagon(radius);
        }
        canvas.drawPath(linePath, lintPaint);
        canvas.drawPath(mPath, mPaint);
    }

    public void drawPentagon(float radius) {
        // 绘制圆心
        mPath.moveTo(circleX, circleY);
        mPath.addCircle(circleX, circleY, 10, Path.Direction.CW);
        mPath.moveTo(circleX, circleY);
        float angle = -90;
        List<Pair<Float, Float>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mPath.moveTo(circleX, circleY);
            angle += 72;
            float x = circleX + (float) (Math.cos(angle * Math.PI / 180) * radius);
            float y = circleY + (float) (Math.sin(angle * Math.PI / 180) * radius);
            mPath.addCircle(x, y, 10, Path.Direction.CW);
            if (i == 0) {
                linePath.moveTo(x, y);
            } else {
                linePath.lineTo(x, y);
            }
            list.add(new Pair<>(x, y));
        }
        Log.d("AAAAAAAA", list.toString());
        linePath.close();
        mPath.moveTo(circleX, circleY);

    }

}
