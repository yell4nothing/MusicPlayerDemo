package com.dd.musicplayerdemo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by softpo on 2016/7/3.
 */
public class CircleImage extends ImageView {
//  图片的宽高
    private int width,height;

    //    设置bitmap方法
    private Bitmap src;

    public CircleImage(Context context) {
        this(context,null);
    }

    public CircleImage(Context context, AttributeSet attrs) {
        this(context,attrs,0);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
        if (bitmapDrawable != null) {
            src =  bitmapDrawable.getBitmap();
        }
    }

    public CircleImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
//  重写onMeasure获取宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

////        获取模式
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if(widthMode==MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
//            width = MeasureSpec.getSize(widthMeasureSpec);
//            height = MeasureSpec.getSize(heightMeasureSpec);
//        }else {
//            width = 300;
//            height =300;
//        }
//        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        去宽高最小值作为直径
        int min = Math.min(getWidth(), getHeight());

        if (src != null) {
//            对图片进行缩放
//            参数四filter：如果是放大图片，代表是否平滑（有助于提高图片质量）；如果缩小图片没有影响
            src = Bitmap.createScaledBitmap(src,min,min,true);

            if(getWidth()>=getHeight()){//宽大与高
                canvas.drawBitmap(createCircleBitmap(src,min), (getWidth()-getHeight())/2,0,null);
            }else {
                canvas.drawBitmap(createCircleBitmap(src,min),0,(getHeight()-getWidth())/2,null);

            }

        }else {
            super.onDraw(canvas);
        }
    }


    //    生成圆形图片
    private Bitmap createCircleBitmap(Bitmap src,int min) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);
//        创建Bitmap作为返回结果
        Bitmap target = Bitmap.createBitmap(min,min, Bitmap.Config.ARGB_4444);
//        根据target产生相同大小的画布
        Canvas canvas = new Canvas(target);
//        绘制圆
        canvas.drawCircle(min/2,min/2,min/2,paint);
//        PorterDuff.Mode.SRC_IN
//        取两层绘制交集。显示上层。
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

//        绘制图片
        canvas.drawBitmap(src,0,0,paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(min/2,min/2,min/2+1,paint);
//        src.recycle();
        canvas = null;
        paint = null;
        return target;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        src = bm;
    }

    @Override
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        src = ((BitmapDrawable) getContext().getResources().getDrawable(resId)).getBitmap();
        invalidate();
    }
}
