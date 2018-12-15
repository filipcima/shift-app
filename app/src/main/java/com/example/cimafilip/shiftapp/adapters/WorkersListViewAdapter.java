package com.example.cimafilip.shiftapp.adapters;

import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.models.User;

import java.util.List;

public class WorkersListViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<User> usersList;

    public WorkersListViewAdapter(LayoutInflater inflater, List<User> usersList) {
        super();
        this.inflater = inflater;
        this.usersList = usersList;
    }


    @Override
    public int getCount() {
        return usersList.size();
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
        View view = inflater.inflate(R.layout.worker_layout, null);
        ViewHolder viewHolder = new ViewHolder();

        User user = usersList.get(position);

        viewHolder.userNameTextView = view.findViewById(R.id.workerNameTextView);
        viewHolder.userNameTextView.setText(user.getFirstName() + " " + user.getSecondName());

        return view;
    }

    private static class ViewHolder {
        TextView userNameTextView;
    }
}
