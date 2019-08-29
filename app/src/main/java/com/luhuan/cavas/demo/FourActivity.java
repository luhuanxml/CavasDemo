package com.luhuan.cavas.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.luhuan.cavas.demo.bezier.WaterView;

public class FourActivity extends AppCompatActivity {
    WaterView waterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        waterView=findViewById(R.id.waterView);
        waterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                waterView.startAnimator();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        waterView.startAnimator();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
