package com.sasoftbd.ets.chart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import android.view.*;
import android.widget.*;

import com.sasoftbd.ets.R;

public class DonutChartView extends View {
    private int present = 339;
    private int absent = 21;
    private int leave = 15;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private PopupWindow popupWindow;

    public DonutChartView(Context context) {
        super(context);
    }

    public DonutChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DonutChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(int present, int absent, int leave) {
        this.present = present;
        this.absent = absent;
        this.leave = leave;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int total = present + absent + leave;
        float startAngle = -90f;

        int width = getWidth();
        int height = getHeight();
        float strokeWidth = Math.min(width, height) * 0.15f;

        @SuppressLint("DrawAllocation") RectF oval = new RectF(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.BUTT);

        // Present
        paint.setColor(Color.parseColor("#50F5E0"));
        float sweepPresent = 360f * present / total;
        canvas.drawArc(oval, startAngle, sweepPresent, false, paint);
        startAngle += sweepPresent;

        // Absent
        paint.setColor(Color.parseColor("#FF6B6B"));
        float sweepAbsent = 360f * absent / total;
        canvas.drawArc(oval, startAngle, sweepAbsent, false, paint);
        startAngle += sweepAbsent;

        // Leave
        paint.setColor(Color.parseColor("#4B4B4B"));
        float sweepLeave = 360f * leave / total;
        canvas.drawArc(oval, startAngle, sweepLeave, false, paint);

        // Center Text
        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);

        float size = Math.min(width, height);
        textPaint.setTextSize(size * 0.12f);
        canvas.drawText("Total", width / 2f, height / 2f - size * 0.04f, textPaint);
        textPaint.setTextSize(size * 0.16f);
        canvas.drawText(String.valueOf(total), width / 2f, height / 2f + size * 0.10f, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return true;

        float x = event.getX();
        float y = event.getY();

        int width = getWidth();
        int height = getHeight();
        float centerX = width / 2f;
        float centerY = height / 2f;
        float dx = x - centerX;
        float dy = y - centerY;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        float radius = Math.min(width, height) / 2f;
        float holeRadius = radius * 0.10f;

        if (distance < holeRadius || distance > radius) return true;

        float angle = (float) Math.toDegrees(Math.atan2(dy, dx)) + 360f;
        angle = (angle + 90f) % 360f;

        float total = present + absent + leave;
        float presentAngle = 360f * present / total;
        float absentAngle = 360f * absent / total;

        String label;
        int bgColor;
        if (angle < presentAngle) {
            label = "Present: " + present;
            bgColor = Color.parseColor("#50F5E0"); // mint
        } else if (angle < presentAngle + absentAngle) {
            label = "Absent: " + absent;
            bgColor = Color.parseColor("#FF6B6B"); // red
        } else {
            label = "Leave: " + leave;
            bgColor = Color.parseColor("#4B4B4B"); // dark
        }

        showTooltip(label, (int) x, (int) y, bgColor);

        return true;
    }


    private void showTooltip(String text, int x, int y, int color) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        View popupView = LayoutInflater.from(getContext()).inflate(R.layout.tooltip_popup, null);
        TextView tooltipText = popupView.findViewById(R.id.tooltipText);
        tooltipText.setText(text);

        // Set dynamic background color
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(60); // Rounded background
        tooltipText.setBackground(bg);

        popupWindow = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, false);

        popupWindow.setOutsideTouchable(true);
        popupWindow.setElevation(8);
        popupWindow.showAtLocation(this, Gravity.NO_GRAVITY, x + getLeft(), y + getTop() - 80);

        postDelayed(() -> {
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }, 1700);
    }

}

