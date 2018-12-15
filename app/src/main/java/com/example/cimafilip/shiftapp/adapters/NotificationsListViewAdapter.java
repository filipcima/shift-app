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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.activities.MainActivity;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.models.Notification;
import com.example.cimafilip.shiftapp.models.NotificationHelper;
import com.example.cimafilip.shiftapp.models.NotificationList;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.ShiftHelper;
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
        final View view = inflater.inflate(R.layout.notification_layout, null);
        List<Notification> notificationList = mNotificationList.getNotifications();
        final Notification notification = notificationList.get(position);
        ViewHolder viewHolder = new ViewHolder();

        viewHolder.message = view.findViewById(R.id.notificationMessageTextView);
        viewHolder.date = view.findViewById(R.id.notificationDateTextView);
        viewHolder.from = view.findViewById(R.id.notificationFromTextView);
        viewHolder.button = view.findViewById(R.id.resolveButton);

        StringBuilder msg = new StringBuilder();
        msg.append("Prosim, prijd: ");
        msg.append(notification.getShift().getDateFrom().substring(0, 5));
        msg.append(" ");
        msg.append(notification.getShift().getDateFrom().split(" ")[1].substring(0, 5));
        msg.append("-");
        msg.append(notification.getShift().getDateTo().split(" ")[1].substring(0, 5));

        viewHolder.message.setText(msg.toString());
        viewHolder.date.setText(notification.getCreated());
        viewHolder.from.setText(notification.getFromUser().getFirstName());

        if (notification.isShow()) {
            viewHolder.button.setVisibility(View.VISIBLE);
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());

                    Shift shift = notification.getShift();
                    List<User> workers = shift.getWorkers();

                    for (int i = 0; i < workers.size(); i++) {
                        User worker = workers.get(i);
                        if (worker.get_id().equals(notification.getFromUser().get_id())) {
                            workers.remove(i);
                            break;
                        }
                    }

                    User tempUser = new User();
                    tempUser.set_id(prefs.getString("idUser", ""));
                    workers.add(tempUser);

                    List<String> workerIds = new ArrayList<>();

                    for(User worker : shift.getWorkers()) {
                        workerIds.add(worker.get_id());
                    }

                    ShiftHelper shiftHelper = new ShiftHelper();
                    shiftHelper.setWorkers((ArrayList<String>) workerIds);
                    IAPIEndpoints apiService = APIClient.getApiService();
                    Call<ShiftHelper> call = apiService.patchShift(shift.get_id(), shiftHelper, shift.get_etag());

                    call.enqueue(new Callback<ShiftHelper>() {
                        @Override
                        public void onResponse(Call<ShiftHelper> call, Response<ShiftHelper> response) {
                            Log.d("good", call.request().url().toString());
                            NotificationHelper notificationHelper = new NotificationHelper();
                            notificationHelper.setShow(false);
                            IAPIEndpoints apiService = APIClient.getApiService();
                            Call<NotificationHelper> call2 = apiService.patchNotification(notification.get_id(), notificationHelper, notification.get_etag());
                            call2.enqueue(new Callback<NotificationHelper>() {
                                @Override
                                public void onResponse(Call<NotificationHelper> call, Response<NotificationHelper> response) {
                                    Toast.makeText(view.getContext(), "Shifts saved.", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(Call<NotificationHelper> call, Throwable t) {
                                    Toast.makeText(view.getContext(), "Error occured during exchange.", Toast.LENGTH_LONG).show();

                                }
                            });


                        }

                        @Override
                        public void onFailure(Call<ShiftHelper> call, Throwable t) {
                            Log.d("fail", call.request().url().toString());
                            Log.d("fail", t.getLocalizedMessage());
                            Toast.makeText(view.getContext(), "Error occured during exchange.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        } else {
            viewHolder.button.setVisibility(View.INVISIBLE);
        }
        

        
        return view;
    }

    private static class ViewHolder {
        TextView message;
        TextView date;
        TextView from;
        Button button;
    }
}
