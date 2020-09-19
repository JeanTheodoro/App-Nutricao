package com.example.appnutrio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomProgressBar extends View {
    private final float stroke = 40.0f;
    private final RectF backgroundArc = new RectF();
    private final Paint bgPaint = new Paint();

    private final Paint paint = new Paint();

    private int progressBarColor;
    private int progressBarBgColor;
    private int progressBarValue = 75;
    private final Rect bounds = new Rect();
    private final RectF barArc = new RectF();

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);

       progressBarValue =  typedArray.getInt(R.styleable.CustomProgressBar_my_progress,0);
       progressBarColor = typedArray.getColor(R.styleable.CustomProgressBar_my_progress_color, 0);
       progressBarBgColor = typedArray.getColor(R.styleable.CustomProgressBar_my_progress_bg_color, Color.LTGRAY);

       typedArray.recycle();
    }


    public void setValue(int progressBarValue) {
        this.progressBarValue = progressBarValue;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float stroke = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.stroke,metrics);

        backgroundArc.set(stroke, stroke, getWidth() - stroke, getHeight() - stroke);

        bgPaint.setColor(progressBarBgColor);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setStrokeWidth(60.0f);
        bgPaint.setAntiAlias(true);
        canvas.drawArc(backgroundArc, 0.0f, 360.0f, false, bgPaint);

        paint.setColor(progressBarColor);
        paint.setStrokeWidth(stroke - 40.0f);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);

        float progress = (360.0f / 100 ) * progressBarValue;
        canvas.getClipBounds(bounds);

        barArc.set(stroke, stroke, bounds.right - stroke, bounds.bottom - stroke );
        canvas.drawArc(barArc, 270.0f, progress, false, paint);
    }
}
