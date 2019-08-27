package com.luhuan.cavas.demo.clip;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

public class ClipIconView extends AppCompatImageView {
    private float mWidth;//整个蒙板的宽度
    private float mHeight;//整个蒙板的高度
    private PointF startPoint = new PointF();
    private PointF midPoint;//两手指之间中心点
    private Matrix matrix = new Matrix();
    private Matrix currentMatrix = new Matrix();

    private int mode = 0;//记录模式
    private static final int DRAG = 1;//拖拽模式
    private static final int ZOOM = 2;//放大模式
    private float startDistance = 0; //两手指放上去拖动前的距离

    public ClipIconView(Context context) {
        this(context, null);
    }

    public ClipIconView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ClipIconView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                currentMatrix.set(this.getImageMatrix());
                startPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {
                    float dx = event.getX() - startPoint.x;//x轴移动距离
                    float dy = event.getY() - startPoint.y;//y轴移动距离
                    matrix.set(currentMatrix);//在当前位置基础上移动
                    matrix.postTranslate(dx, dy);
                } else if (mode == ZOOM) {
                    float endDistance = distance(event);
                    if (endDistance > 10f) {
                        if (startDistance > 0) {
                            float scale = endDistance / startDistance;
                            matrix.set(currentMatrix);
                            matrix.postScale(scale, scale, midPoint.x, midPoint.y);
                        }
                    }
                }
                break;
            //已经有一根手指放上去了，再放一根手指上去，变成了放大模式，计算两个手指之间的距离
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                startDistance = distance(event);
                if (startDistance > 10f) { //防止一根手上有两个老茧
                    midPoint = getMidPoint(event);
                    currentMatrix.set(this.getImageMatrix());
                }
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode = 0;
                break;
        }
        this.setImageMatrix(matrix);
        return true;
    }

    //计算两根手指之间的距离
    private float distance(MotionEvent motionEvent) {
        //两根手指之间的距离
        float dx = motionEvent.getX(1) - motionEvent.getX(0);
        float dy = motionEvent.getY(1) - motionEvent.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    //计算两根手指之间的中心点
    private PointF getMidPoint(MotionEvent motionEvent) {
        float dx = motionEvent.getX(1) + motionEvent.getX(0);
        float dy = motionEvent.getY(1) + motionEvent.getY(0);
        return new PointF(dx / 2, dy / 2);
    }

}
