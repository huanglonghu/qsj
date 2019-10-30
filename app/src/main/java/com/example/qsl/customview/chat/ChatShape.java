package com.example.qsl.customview.chat;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.shapes.Shape;

class ChatShape extends Shape {
    private String arrowDirection;
    private int arrowHeight;
    private int arrowUpDistance;
    private int arrowWidth;
    private int connerRadius;
    private int fillColor;
    Path fillPath = new Path();
    private boolean hasStroke;
    float height;
    float heightEnd;
    final float heightStart = 3.0f;
    private boolean isArrowCenter;
    final float reviseValue = 3.0f;
    private int strokeColor;
    Path strokePath = new Path();
    private int strokeWidth;
    float width;
    float widthEnd;
    final float widthStart = 3.0f;

    public ChatShape(int arrowWidth, int arrowHeight, boolean isArrowCenter, int strokeWidth, String arrowDirection, int arrowUpDistance, int connerRadius, int strokeColor, int fillColor) {
        this.arrowWidth = arrowWidth;
        this.arrowHeight = arrowHeight;
        this.isArrowCenter = isArrowCenter;
        this.strokeWidth = strokeWidth;
        this.arrowDirection = arrowDirection;
        this.arrowUpDistance = arrowUpDistance;
        this.connerRadius = connerRadius;
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    private void resizePath(float width, float height, Path path) {
        this.widthEnd = width - 3.0f;
        this.heightEnd = height - 3.0f;
        if (this.isArrowCenter) {
            this.arrowUpDistance = (int) ((height / 2.0f) - ((float) (this.arrowHeight / 2)));
        }
        RectF connerRect = new RectF();
        path.reset();
        path.moveTo(((float) this.arrowWidth) + 3.0f, (float) (this.arrowUpDistance + this.arrowHeight));
        path.lineTo(3.0f, (float) (this.arrowUpDistance + (this.arrowHeight / 2)));
        path.lineTo(((float) this.arrowWidth) + 3.0f, (float) this.arrowUpDistance);
        path.lineTo(((float) this.arrowWidth) + 3.0f, (float) this.connerRadius);
        connerRect.set(((float) this.arrowWidth) + 3.0f, 3.0f, (((float) this.arrowWidth) + 3.0f) + ((float) this.connerRadius), (float) this.connerRadius);
        path.arcTo(connerRect, 180.0f, 90.0f);
        path.lineTo(width - ((float) this.connerRadius), 3.0f);
        connerRect.set(width - ((float) this.connerRadius), 3.0f, width, (float) this.connerRadius);
        path.arcTo(connerRect, 270.0f, 90.0f);
        path.lineTo(width, height - ((float) this.connerRadius));
        connerRect.set(width - ((float) this.connerRadius), height - ((float) this.connerRadius), width, height);
        path.arcTo(connerRect, 3.0f, 90.0f);
        path.lineTo((((float) this.arrowWidth) + 3.0f) + ((float) this.connerRadius), height);
        connerRect.set(((float) this.arrowWidth) + 3.0f, height - ((float) this.connerRadius), (((float) this.arrowWidth) + 3.0f) + ((float) this.connerRadius), height);
        path.arcTo(connerRect, 90.0f, 90.0f);
        path.close();
    }

    protected void onResize(float width, float height) {
        super.onResize(width, height);
        this.width = width;
        this.height = height;
        resizePath(width - 3.0f, height - 3.0f, this.strokePath);
        resizePath(width - 3.0f, height - 3.0f, this.fillPath);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        if (this.arrowDirection.equals("right")) {
            canvas.scale(-1.0f, 1.0f, this.width / 2.0f, this.height / 2.0f);
        }
        drawFill(canvas, paint);
        drawStroke(canvas, paint);
        canvas.restore();
    }

    private void drawStroke(Canvas canvas, Paint paint) {
        paint.setColor(this.strokeColor);
        paint.setStyle(Style.STROKE);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeJoin(Join.ROUND);
        paint.setStrokeWidth((float) this.strokeWidth);
        canvas.drawPath(this.strokePath, paint);
    }

    private void drawFill(Canvas canvas, Paint paint) {
        paint.setColor(this.fillColor);
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth((float) this.strokeWidth);
        canvas.drawPath(this.fillPath, paint);
    }
}
