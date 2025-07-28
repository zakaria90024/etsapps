package com.sasoftbd.ets.chart;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sasoftbd.ets.R;

public class ChartActivity extends AppCompatActivity  {

    YAxisView axisView;
    SalesChartView salesChartView;
    DonutChartView donutChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chart);

        donutChart = findViewById(R.id.donutChart);
        salesChartView = findViewById(R.id.salesChartView);
        axisView = findViewById(R.id.yAxisView);

        axisView.setMaxValue(salesChartView.getMaxValue());


        // Optionally update data dynamically
        donutChart.setData(339, 21, 15);

        // Optional: Set custom data dynamically
        float[] sales = {120, 180, 150, 90, 100};
        float[] corrections = {150, 130, 170, 100, 50};
        float[] targets = {200, 190, 160, 140, 130};
        String[] days = {"Sat", "Sun", "Mon", "Tue", "Wed"};

        salesChartView.setData(sales, corrections, targets, days);
    }
}