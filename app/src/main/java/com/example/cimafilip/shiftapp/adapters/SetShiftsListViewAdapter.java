package com.example.cimafilip.shiftapp.adapters;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SetShiftsListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Shift> shiftList;
    private SharedPreferences prefs;

    public SetShiftsListViewAdapter(LayoutInflater inflater, List<Shift> shiftList) {
        super();
        this.inflater = inflater;
        this.shiftList = shiftList;
        prefs = PreferenceManager.getDefaultSharedPreferences(inflater.getContext());
    }

    private String[] shifts = {
            "21. 2. 2018 | PO | 7:30 - 14:00",
            "21. 2. 2018 | PO | 14:00 - 20:00",
            "23. 2. 2018 | ST | 7:30 - 14:00",
            "23. 2. 2018 | ST | 7:30 - 14:00",
            "23. 2. 2018 | ST | 7:30 - 14:00",
            "23. 2. 2018 | ST | 7:30 - 14:00",
            "23. 2. 2018 | ST | 7:30 - 14:00",
            "23. 2. 2018 | ST | 7:30 - 14:00",
    };

    @Override
    public int getCount() {
        return shiftList.size();
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
        View view = inflater.inflate(R.layout.shift_picker_layout, null);
        ViewHolder viewHolder = new ViewHolder();

        Shift shift = shiftList.get(position);

        viewHolder.shiftDetailTextView = view.findViewById(R.id.shiftDetailTextView);
        viewHolder.canGoSwitch = view.findViewById(R.id.canGoSwitch);

        for (User worker: shift.getWorkers()) {
            if (prefs.getString("idUser", "").equals(worker.get_id())) {
                viewHolder.canGoSwitch.setChecked(true);
                break;
            }
        }

        try {
            viewHolder.shiftDetailTextView.setText(getShiftText(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    private static class ViewHolder {
        TextView shiftDetailTextView;
        Switch canGoSwitch;
    }

    public String getShiftText(int position) throws ParseException {
        Shift shift = shiftList.get(position);
        StringBuilder sb = new StringBuilder();

        // extract date
        String[] datetimeFrom = shift.getDateFrom().split(" ");
        String[] datetimeTo = shift.getDateFrom().split(" ");
        String date = datetimeFrom[0];
        String from = datetimeFrom[1].substring(0, 5);
        String to = datetimeTo[1].substring(0, 5);

        Date parsedDate = new SimpleDateFormat("dd/MM/yy").parse(date);

        DateFormat DOWFormat = new SimpleDateFormat("EE");

        String DOW = DOWFormat.format(parsedDate);

        sb.append(date);
        sb.append(" | ");
        sb.append(DOW);
        sb.append(" | ");
        sb.append(from);
        sb.append(" - ");
        sb.append(to);

        return sb.toString();
    }
}
