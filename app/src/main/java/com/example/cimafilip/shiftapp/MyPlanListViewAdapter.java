package com.example.cimafilip.shiftapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyPlanListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    public MyPlanListViewAdapter(LayoutInflater inflater) {
        super();
        this.inflater = inflater;
    }

    private String[] dates = {
            "12. 3.",
            "13. 3.",
            "14. 3.",
            "15. 3."
    };

    private String[] days = {
            "MO",
            "TU",
            "WE",
            "TH"
    };

    private String[] times = {
            "14:00 - 18:00",
            "14:00 - 19:30",
            "9:00 - 19:00",
            "9:00 - 19:00"
    };

    private String[] places = {
            "COKAFE Poruba",
            "COKAFE Centrum",
            "COKAFE Centrum",
            "COKAFE Centrum"
    };


    @Override
    public int getCount() {
        return dates.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.shift_layout, null);
        TextView dayOfWeek = view.findViewById(R.id.shiftDayOfWeek);
        TextView date = view.findViewById(R.id.shiftDate);
        TextView placeName = view.findViewById(R.id.shiftPlaceName);
        TextView workingTime = view.findViewById(R.id.shiftTime);
        dayOfWeek.setText(days[position]);
        date.setText(dates[position]);
        placeName.setText(places[position]);
        workingTime.setText(times[position]);


        return view;
    }
}