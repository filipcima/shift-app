package com.example.cimafilip.shiftapp.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cimafilip.shiftapp.R;

public class DetailDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fe485a")));

        setContentView(R.layout.activity_detail_day);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int day = intent.getIntExtra("day", 0);

        getSupportActionBar().setTitle("Směny pro den: " + String.valueOf(day)+ ". "+String.valueOf(month)+ ". " + String.valueOf(year));
    }
}
