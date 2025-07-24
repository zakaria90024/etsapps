package com.sasoftbd.ets.stadium;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sasoftbd.ets.R;

public class TicketActivity extends AppCompatActivity {

    GridLayout gridLayout;
    int totalSeats = 20; // 5 cols * 4 rows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ticket);
        gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < totalSeats; i++) {
            final TextView seat = new TextView(this);
            seat.setText(String.valueOf(i + 1));
            seat.setTextColor(Color.WHITE);
            seat.setGravity(View.TEXT_ALIGNMENT_CENTER);
            seat.setBackgroundColor(Color.GRAY);
            seat.setPadding(20, 20, 20, 20);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(16, 16, 16, 16);
            seat.setLayoutParams(params);

            // Tag to track selection
            seat.setTag(false); // false means not selected

            seat.setOnClickListener(v -> {
                boolean selected = (boolean) seat.getTag();
                if (!selected) {
                    seat.setBackgroundColor(Color.parseColor("#00aa66")); // selected
                } else {
                    seat.setBackgroundColor(Color.GRAY); // unselected
                }
                seat.setTag(!selected);
            });

            gridLayout.addView(seat);
        }


    }
}