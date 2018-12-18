package com.example.cimafilip.shiftapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.activities.ChangeRequestActivity;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ShiftsByDayViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Shift> shiftList;
    private String idUser;
    private Button changeRequestButton;

    public ShiftsByDayViewAdapter(LayoutInflater inflater, List<Shift> shiftList) {
        super();
        this.inflater = inflater;
        this.shiftList = shiftList;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(inflater.getContext());
        idUser = prefs.getString("idUser", "");
    }

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
        View view = inflater.inflate(R.layout.shift_layout, null);
        final Shift s = shiftList.get(position);

        changeRequestButton = view.findViewById(R.id.button);
        changeRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> usersOnShift = new ArrayList<>();
                for (User user: s.getWorkers()) {
                    usersOnShift.add(user.get_id());
                }
                Intent intent = new Intent(inflater.getContext(), ChangeRequestActivity.class);
                intent.putStringArrayListExtra("usersOnShift", usersOnShift);
                intent.putExtra("idShift", s.get_id());
                inflater.getContext().startActivity(intent);
            }
        });

        changeRequestButton.setVisibility(View.INVISIBLE);
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.date = view.findViewById(R.id.shiftDate);
        viewHolder.dayOfWeek = view.findViewById(R.id.shiftDayOfWeek);
        viewHolder.placeName = view.findViewById(R.id.shiftPlaceName);
        viewHolder.workingTime = view.findViewById(R.id.shiftTime);

        String day = s.getDateFrom().split(" ")[0].split("/")[0];
        String month = s.getDateFrom().split(" ")[0].split("/")[1];
        String startTime = s.getDateFrom().split(" ")[1].substring(0, 5);
        String endTime = s.getDateTo().split(" ")[1].substring(0, 5);
        StringBuilder workers = new StringBuilder();

        viewHolder.placeName.setText("");

        if (s.getWorkers() != null) {
            int i = 0;
            for (User worker: s.getWorkers()) {
                String fullName = worker.getFirstName() + " " + worker.getSecondName();
                workers.append(fullName);
                if (!(i++ == s.getWorkers().size() - 1)) {
                    workers.append(", ");
                }
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                Date dateShift = new Date();
                Date dateNow = new Date();

                try {
                    dateShift = df.parse(s.getDateFrom());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (worker.get_id().equals(idUser) && dateNow.before(dateShift)) {
                    view.findViewById(R.id.button).setVisibility(View.VISIBLE);
                }
            }
            viewHolder.placeName.setText(workers.toString());

        }

        viewHolder.dayOfWeek.setText("PO");
        viewHolder.date.setText(day + "." + month + ".");
        viewHolder.workingTime.setText(startTime + "-" + endTime);
        return view;
    }

    private static class ViewHolder {
        TextView dayOfWeek;
        TextView date;
        TextView placeName;
        TextView workingTime;
    }
}
