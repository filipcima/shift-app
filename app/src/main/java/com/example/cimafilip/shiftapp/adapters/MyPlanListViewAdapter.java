package com.example.cimafilip.shiftapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;

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
            "COKAFE Centrum",
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
        // TODO: implement this method to return ObjectId
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.shift_layout, null);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.date = view.findViewById(R.id.shiftDate);
        viewHolder.dayOfWeek = view.findViewById(R.id.shiftDayOfWeek);
        viewHolder.placeName = view.findViewById(R.id.shiftPlaceName);
        viewHolder.workingTime = view.findViewById(R.id.shiftTime);

        viewHolder.dayOfWeek.setText(days[position]);
        viewHolder.date.setText(dates[position]);
        viewHolder.placeName.setText(places[position]);
        viewHolder.workingTime.setText(times[position]);
        return view;
    }

    private static class ViewHolder {
        TextView dayOfWeek;
        TextView date;
        TextView placeName;
        TextView workingTime;
    }
}