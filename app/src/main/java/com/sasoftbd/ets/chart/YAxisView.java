package com.sasoftbd.ets.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class YAxisView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int numberOfTicks = 4;
    private float maxValue = 200f;

    public YAxisView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float chartHeight = getHeight() - 50;
        float baseY = chartHeight;

        paint.setColor(Color.DKGRAY);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.RIGHT);

        for (int i = 0; i <= numberOfTicks; i++) {
            float value = i * 50;
            float yPos = baseY - (value / maxValue) * chartHeight;
            canvas.drawText(String.valueOf((int) value), getWidth() - 10, yPos + 10, paint);
        }
    }
}
