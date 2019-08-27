package com.luhuan.cavas.demo.menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MenuView extends View {

    private int mWidth;
    private int mHeight;
    int circleX = 0;
    int circleY = 0;
    int radiusInner = 80;
    int radiusOuter = 200;
    int distance = 5;
    Paint mPaint;

    private List<Region> regions;

    public MenuView(Context context) {
        this(context, null);
    }

    public MenuView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL);
        regions = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        circleX = mWidth / 2;
        circleY = mHeight / 2;
        radiusOuter=mWidth/2;
        radiusInner = mWidth/2*80/200;
        RectF innerRectF = new RectF(
                circleX - radiusInner,
                circleY - radiusInner,
                circleX + radiusInner,
                circleY + radiusInner);
        RectF outerRectF = new RectF(
                circleX - radiusOuter,
                circleY - radiusOuter,
                circleX + radiusOuter,
                circleY + radiusOuter);
        Path path = new Path();
        //内圆
        path.moveTo(circleX, circleY);
        path.addCircle(circleX, circleY, radiusInner - distance * 2, Path.Direction.CW);
        Region region = new Region();
        region.setPath(path, new Region(
                circleX - radiusOuter,
                circleY - radiusOuter,
                circleX + radiusOuter,
                circleY + radiusOuter));
        regions.add(region);
        //外圆
        path.reset();
        //间距内弧偏转角
        float disAngleInner = (float) (distance / (2 * Math.PI * radiusInner / 360));
        //间距外弧偏转角
        float disAngleOuter = (float) (distance / (2 * Math.PI * radiusOuter / 360));
        //控件弧度
        float sweepAngleInner = 90 - disAngleInner * 2;
        float sweepAngleOuter = 90 - disAngleOuter * 2;

        path.addArc(innerRectF, -135 + disAngleInner, sweepAngleInner);
        path.lineTo((float) (circleX + radiusOuter * Math.sin(sweepAngleOuter / 2 * Math.PI / 180)),
                (float) (circleY - radiusOuter * Math.cos(sweepAngleOuter / 2 * Math.PI / 180)));

        path.addArc(outerRectF, -45 - disAngleOuter, -sweepAngleOuter);
        path.lineTo((float) (circleX - radiusInner * Math.sin(sweepAngleInner / 2 * Math.PI / 180)),
                (float) (circleY - radiusInner * Math.cos(sweepAngleInner / 2 * Math.PI / 180)));

        Region region2 = new Region();
        region2.setPath(path, new Region(
                circleX - radiusOuter,
                circleY - radiusOuter,
                circleX + radiusOuter,
                circleY + radiusOuter));
        regions.add(region2);

        for (int i = 0; i < 3; i++) {
            Matrix matrix = new Matrix();
            matrix.setRotate(90, circleX, circleY);
            path.transform(matrix);
            Region region3 = new Region();
            region3.setPath(path, new Region(
                    circleX - radiusOuter,
                    circleY - radiusOuter,
                    circleX + radiusOuter,
                    circleY + radiusOuter));
            regions.add(region3);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Region region : regions) {
            Path path = region.getBoundaryPath();
            canvas.drawPath(path, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_UP && action != MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);//如果不是这两个动作就按正常的走
        }
        float actionX = event.getX();
        float actionY = event.getY();
        for (int i = 0; i < regions.size(); i++) {
            Region region = regions.get(i);
            if (region.contains((int) actionX, (int) actionY)) {
                this.setTag(this.getId(), i);
                return super.onTouchEvent(event);
            }
        }
        return false;
    }
}
