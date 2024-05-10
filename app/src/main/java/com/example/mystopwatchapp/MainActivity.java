package com.example.mystopwatchapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private Button buttonStart, buttonStop, buttonHold;

    private boolean isRunning;
    private long startTime, elapsedTime, storedTime;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonHold = findViewById(R.id.buttonHold);

        handler = new Handler();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });

        buttonHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holdTimer();
            }
        });
    }

    private void startTimer() {
        isRunning = true;
        startTime = System.currentTimeMillis();
        handler.postDelayed(updateTimerRunnable, 0);
        buttonStart.setEnabled(false);
        buttonStop.setEnabled(true);
        buttonHold.setEnabled(true);
    }
    private void stopTimer() {
        isRunning = false;
        elapsedTime = 0; // Reset the elapsed time
        storedTime = 0;
        handler.removeCallbacks(updateTimerRunnable);
        textViewTimer.setText("00:00:00"); // Reset the timer display
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(false);
        buttonHold.setEnabled(true);
    }

    private void holdTimer() {
        isRunning = false; // Pause the timer
        storedTime = elapsedTime;
        handler.removeCallbacks(updateTimerRunnable);
        buttonStart.setEnabled(true);
        buttonStop.setEnabled(true);
        buttonHold.setEnabled(false);
    }



    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                elapsedTime = System.currentTimeMillis() - startTime + storedTime;
                int seconds = (int) (elapsedTime / 1000);
                int minutes = seconds / 60;
                int hours = minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;

                String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
                textViewTimer.setText(time);
                handler.postDelayed(this, 1000);
            }
        }
    };
}
