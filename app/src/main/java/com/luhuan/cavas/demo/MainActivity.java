package com.luhuan.cavas.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button01).setOnClickListener(this);
        findViewById(R.id.button02).setOnClickListener(this);
        findViewById(R.id.button03).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.button01:
                intent = new Intent(this, FirstActivity.class);
                break;
            case R.id.button02:
                intent = new Intent(this, SecondActivity.class);
                break;
            case R.id.button03:
                intent = new Intent(this, ThirdActivity.class);
                break;
        }
        startActivity(intent);
    }
}

