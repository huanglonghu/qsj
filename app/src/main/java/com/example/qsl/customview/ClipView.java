package com.example.qsl.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ClipView extends View {
    private Paint borderPaint;
    private int clipBorderWidth;
    private int clipRadiusWidth;
    private ClipType clipType;
    private int clipWidth;
    private float mHorizontalPadding;
    private Paint paint;
    private Xfermode xfermode;

    public enum ClipType {
        CIRCLE,
        RECTANGLE
    }

    public ClipView(Context context) {
        this(context, null);
    }

    public ClipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.paint = new Paint();
        this.borderPaint = new Paint();
        this.clipType = ClipType.CIRCLE;
        this.paint.setAntiAlias(true);
        this.borderPaint.setStyle(Style.STROKE);
        this.borderPaint.setColor(-1);
        this.borderPaint.setStrokeWidth((float) this.clipBorderWidth);
        this.borderPaint.setAntiAlias(true);
        this.xfermode = new PorterDuffXfermode(Mode.DST_OUT);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawColor(Color.parseColor("#a8000000"));
        this.paint.setXfermode(this.xfermode);
        if (this.clipType == ClipType.CIRCLE) {
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) this.clipRadiusWidth, this.paint);
            canvas.drawCircle((float) (getWidth() / 2), (float) (getHeight() / 2), (float) this.clipRadiusWidth, this.borderPaint);
        } else if (this.clipType == ClipType.RECTANGLE) {
            canvas.drawRect(this.mHorizontalPadding, (float) ((getHeight() / 2) - (this.clipWidth / 2)), ((float) getWidth()) - this.mHorizontalPadding, (float) ((getHeight() / 2) + (this.clipWidth / 2)), this.paint);
            canvas.drawRect(this.mHorizontalPadding, (float) ((getHeight() / 2) - (this.clipWidth / 2)), ((float) getWidth()) - this.mHorizontalPadding, (float) ((getHeight() / 2) + (this.clipWidth / 2)), this.borderPaint);
        }
        canvas.restore();
    }

    public Rect getClipRect() {
        Rect rect = new Rect();
        rect.left = (getWidth() / 2) - this.clipRadiusWidth;
        rect.right = (getWidth() / 2) + this.clipRadiusWidth;
        rect.top = (getHeight() / 2) - this.clipRadiusWidth;
        rect.bottom = (getHeight() / 2) + this.clipRadiusWidth;
        return rect;
    }

    public void setClipBorderWidth(int clipBorderWidth) {
        this.clipBorderWidth = clipBorderWidth;
        this.borderPaint.setStrokeWidth((float) clipBorderWidth);
        invalidate();
    }

    public void setmHorizontalPadding(float mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
        this.clipRadiusWidth = ((int) (((float) getScreenWidth(getContext())) - (2.0f * mHorizontalPadding))) / 2;
        this.clipWidth = this.clipRadiusWidth * 2;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void setClipType(ClipType clipType) {
        this.clipType = clipType;
    }
}
