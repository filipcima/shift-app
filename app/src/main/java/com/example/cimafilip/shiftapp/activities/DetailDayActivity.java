package com.example.cimafilip.shiftapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.adapters.ShiftsByDayViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.ShiftList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDayActivity extends AppCompatActivity {
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_day);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fe485a")));

        Intent intent = getIntent();
        year = intent.getIntExtra("year", 0);
        month = intent.getIntExtra("month", 0);
        day = intent.getIntExtra("day", 0);

        getSupportActionBar().setTitle("Shifts for day: " + String.valueOf(day)+ ". "+String.valueOf(month)+ ". " + String.valueOf(year));
        getData();
    }

    private void getData() {
        String date = String.valueOf(day) + '/' + month + '/' + String.valueOf(year).substring(2,4);
        String where ="{\"$and\": [{\"date_from\": {\"$gte\": \"" + date + " 00:00:00\"}}, {\"date_from\": {\"$lte\": \"" + date + " 23:59:59\"}}]}";
        String embedded = new RetrofitURLBuilder("embedded")
                .add("workers", "1")
                .build();
        IAPIEndpoints apiClient = APIClient.getApiService();
        Call<ShiftList> call = apiClient.getShifts(where, embedded);
        call.enqueue(new Callback<ShiftList>() {
            @Override
            public void onResponse(Call<ShiftList> call, Response<ShiftList> response) {
                Log.d("onresponseDetailDay", call.request().url().toString());
                if (response.body() != null) {
                    List<Shift> sl = response.body().getShifts();
                    ListView mListView = findViewById(R.id.shiftsListView);
                    if (mListView != null) {
                        mListView.setAdapter(new ShiftsByDayViewAdapter(getLayoutInflater(), sl));
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());
                Log.d("msg", t.getLocalizedMessage());
            }
        });
    }
}
