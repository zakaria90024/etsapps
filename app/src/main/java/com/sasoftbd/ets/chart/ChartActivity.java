package com.sasoftbd.ets.chart;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sasoftbd.ets.R;

public class ChartActivity extends AppCompatActivity  {

    DonutChartView donutChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chart);

        donutChart = findViewById(R.id.donutChart);

        // Optionally update data dynamically
        donutChart.setData(339, 21, 15);
    }
}