package com.example.cimafilip.shiftapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.activities.MainActivity;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.models.Notification;
import com.example.cimafilip.shiftapp.models.NotificationList;
import com.example.cimafilip.shiftapp.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private NotificationList mNotificationList;

    public NotificationsListViewAdapter(LayoutInflater inflater, NotificationList notificationList) {
        super();

        this.inflater = inflater;
        this.mNotificationList = notificationList;
    }

    @Override
    public int getCount() {
        return mNotificationList.getNotifications().size();
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
        View view = inflater.inflate(R.layout.notification_layout, null);

        ViewHolder viewHolder = new ViewHolder();

        viewHolder.message = view.findViewById(R.id.notificationMessageTextView);
        viewHolder.date = view.findViewById(R.id.notificationDateTextView);
        viewHolder.from = view.findViewById(R.id.notificationFromTextView);

        viewHolder.message.setText(mNotificationList.getNotifications().get(position).getMessage());
        viewHolder.date.setText(mNotificationList.getNotifications().get(position).getCreated());
        viewHolder.from.setText(mNotificationList.getNotifications().get(position).getUser().getFirstName());

        return view;
    }

    private static class ViewHolder {
        TextView message;
        TextView date;
        TextView from;
    }
}
