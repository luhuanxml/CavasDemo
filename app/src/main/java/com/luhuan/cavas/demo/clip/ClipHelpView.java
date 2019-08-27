package com.luhuan.cavas.demo.clip;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * 简易封装裁剪圆形头像
 */
public class ClipHelpView extends View {
    private float clipSize; //裁剪图片大小
    private float clipStrockSize; //裁剪框边线宽度

    private float mWidth;//整个蒙板的宽度
    private float mHeight;//整个蒙板的高度
    private Paint mPaint;//画板
    private Path globalPath; //整个蒙版的path
    private Path clipPath; //裁剪框的path
    private float circleX;//圆心x坐标
    private float circleY;//圆心Y坐标

    private PorterDuffXfermode xfermode;

    public ClipHelpView(Context context) {
        this(context, null);
    }

    public ClipHelpView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClipHelpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clipStrockSize = 2;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        globalPath = new Path();
        clipPath = new Path();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        clipSize = mWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth / 2, mHeight / 2);
        mPaint.setColor(Color.parseColor("#333333"));
        mPaint.setAlpha(255 / 3 * 2);
        mPaint.setStyle(Paint.Style.FILL);
        globalPath.addRect(-mWidth / 2, -mHeight / 2, mWidth / 2, mHeight / 2, Path.Direction.CW);
        canvas.drawPath(globalPath, mPaint);
        mPaint.setXfermode(xfermode);
        clipPath.addCircle(0, 0, mWidth / 2 - 5, Path.Direction.CW);
        canvas.drawPath(clipPath, mPaint);
        mPaint.setXfermode(null);
        mPaint.setColor(Color.YELLOW);
        mPaint.setAlpha(255);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        canvas.drawPath(clipPath,mPaint);
    }
}
