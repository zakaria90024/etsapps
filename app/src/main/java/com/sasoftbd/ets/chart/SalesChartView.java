package com.sasoftbd.ets.chart;//package com.sasoftbd.ets.chart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class SalesChartView extends View {

    private float[] sales = {100, 200, 150, 180, 220};
    private float[] corrections = {10, 20, 30, 25, 15};
    private float[] targets = {150, 250, 200, 210, 240};
    private String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int selectedGroupIndex = -1;
    private int selectedBarIndex = -1;
    private Runnable tooltipClearRunnable;
    private final int tooltipDuration = 2000;

    public SalesChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(float[] sales, float[] corrections, float[] targets, String[] days) {
        this.sales = sales;
        this.corrections = corrections;
        this.targets = targets;
        this.days = days;
        invalidate();
    }

    float getMaxValue() {
        float max = 0;
        for (int i = 0; i < sales.length; i++) {
            max = Math.max(max, Math.max(sales[i], Math.max(corrections[i], targets[i])));
        }
        return max;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int groupCount = days.length;
        int barsPerGroup = 3;
        float groupSpacing = 80f;
        float barWidth = 30f;
        float barSpacing = 15f;
        float startX = 100f;
        float chartHeight = getHeight() - 50;
        float baseY = chartHeight;
        float maxValue = getMaxValue() * 1.0f;

        paint.setTextSize(26);
        paint.setTextAlign(Paint.Align.CENTER);

        for (int i = 0; i < groupCount; i++) {
            float groupX = startX + i * (barsPerGroup * (barWidth + barSpacing) + groupSpacing);
            float[] values = {sales[i], corrections[i], targets[i]};
            int[] colors = {Color.BLUE, Color.RED, Color.GREEN};

            for (int j = 0; j < barsPerGroup; j++) {
                paint.setColor(colors[j]);
                float barLeft = groupX + j * (barWidth + barSpacing);
                float barTop = baseY - (values[j] / maxValue) * (chartHeight - 100);
                float barRight = barLeft + barWidth;
                canvas.drawRect(barLeft, barTop, barRight, baseY, paint);
            }

            // Draw day label
            paint.setColor(Color.BLACK);
            float labelX = groupX + (barsPerGroup * (barWidth + barSpacing)) / 2 - barSpacing;
            canvas.drawText(days[i], labelX, baseY + 40, paint);
        }

        // Draw tooltip if needed
        if (selectedGroupIndex >= 0 && selectedBarIndex >= 0) {
            float groupX = startX + selectedGroupIndex * (barsPerGroup * (barWidth + barSpacing) + groupSpacing);
            float[] values = {sales[selectedGroupIndex], corrections[selectedGroupIndex], targets[selectedGroupIndex]};
            int[] colors = {Color.BLUE, Color.RED, Color.GREEN};
            String[] labels = {"Sales: ", "Collection: ", "Target: "};

            float selectedValue = values[selectedBarIndex];
            float barLeft = groupX + selectedBarIndex * (barWidth + barSpacing);
            float barTop = baseY - (selectedValue / maxValue) * (chartHeight - 100);

            String text = labels[selectedBarIndex] + selectedValue;
            paint.setColor(Color.BLACK);
            paint.setTextSize(28);
            float textWidth = paint.measureText(text);
            float tooltipX = barLeft + barWidth / 2 - textWidth / 2;
            float tooltipY = barTop - 30;

            paint.setColor(Color.WHITE);
            canvas.drawRect(tooltipX - 10, tooltipY - 40, tooltipX + textWidth + 10, tooltipY, paint);
            paint.setColor(colors[selectedBarIndex]);
            canvas.drawText(text, tooltipX + textWidth / 2, tooltipY - 10, paint);
        }



//        float height = getHeight();
//        float maxVal = 250f;
//        // Draw Y-axis labels
//        paint.setColor(Color.DKGRAY);
//        paint.setTextSize(30);
//        for (int i = 0; i <= 5; i++) {
//            float val = i * 50;
//            float y = baseY - (val / maxVal) * (height - 200);
//            canvas.drawText(String.valueOf((int) val), 20, y + 10, paint);
//        }

//        // Y-axis labels (static and visible)
//        float height = getHeight();
//        float maxVal = 250f;  // This could be dynamically calculated as well
//        float marginTop = 150f;  // Adjust this margin to fit your layout
//        float labelSpacing = (height - marginTop - 100) / 5;  // 5 is the number of ticks you want
//
//// Draw Y-axis labels
//        paint.setColor(Color.DKGRAY);
//        paint.setTextSize(30);
//        for (int i = 0; i <= 5; i++) {
//            float val = i * 50;
//            float y = marginTop + i * labelSpacing;
//            canvas.drawText(String.valueOf((int) val), 20, y + 10, paint);
//        }

//        // Draw Y-axis labels (Fixed and not affected by scrolling)
//        paint.setColor(Color.DKGRAY);
//        paint.setTextSize(30);
//
//        // Draw Y-axis labels at a fixed position on the left side (not affected by scrolling)
//        // **CHANGE: The X-position for the Y-axis labels is fixed at 20, so they stay in place**
//        int numberOfTicks = 5;
//        for (int i = 0; i <= numberOfTicks; i++) {
//            float value = i * 50; // Set intervals for Y-axis (50, 100, 150, etc.)
//            // **CHANGE: Y-axis labels are drawn relative to chart height but remain fixed at X = 20**
//            float yPos = baseY - (value / maxValue) * chartHeight;  // Position of the Y-axis label
//            canvas.drawText(String.valueOf((int) value), 20, yPos + 10, paint); // X = 20 is fixed for all labels
//        }

    }

    @Override
    public boolean onTouchEvent(android.view.MotionEvent event) {
        if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            int groupCount = days.length;
            int barsPerGroup = 3;
            float groupSpacing = 80f;
            float barWidth = 30f;
            float barSpacing = 15f;
            float startX = 100f;
            float chartHeight = getHeight() - 150;
            float baseY = chartHeight;
            float maxValue = getMaxValue() * 1.1f;

            for (int i = 0; i < groupCount; i++) {
                float groupX = startX + i * (barsPerGroup * (barWidth + barSpacing) + groupSpacing);
                float[] values = {sales[i], corrections[i], targets[i]};

                for (int j = 0; j < barsPerGroup; j++) {
                    float barLeft = groupX + j * (barWidth + barSpacing);
                    float barRight = barLeft + barWidth;
                    float barTop = baseY - (values[j] / maxValue) * (chartHeight - 100);

                    if (x >= barLeft && x <= barRight && y >= barTop && y <= baseY) {
                        selectedGroupIndex = i;
                        selectedBarIndex = j;

                        if (tooltipClearRunnable != null)
                            removeCallbacks(tooltipClearRunnable);

                        tooltipClearRunnable = () -> {
                            selectedGroupIndex = -1;
                            selectedBarIndex = -1;
                            invalidate();
                        };
                        postDelayed(tooltipClearRunnable, tooltipDuration);
                        invalidate();
                        return true;
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) (100 + days.length * (3 * (30 + 15) + 80)); // based on bar/group spacing
        int desiredHeight = 500;

        int width = resolveSize(desiredWidth, widthMeasureSpec);
        int height = resolveSize(desiredHeight, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

}




