package com.example.cimafilip.shiftapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.adapters.WorkersListViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.User;
import com.example.cimafilip.shiftapp.models.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeRequestActivity extends AppCompatActivity {
    private ListView mWorkersListView;
    private Button mButton;
    private TextView mPromptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_request);

        mWorkersListView = findViewById(R.id.workersListView);
        mPromptTextView = findViewById(R.id.changeRequestPromptText);
        mButton = findViewById(R.id.confirmRequestChangeButton);

        Intent i = getIntent();
        final List<String> usersOnShift = i.getStringArrayListExtra("usersOnShift");


        String usersString = new RetrofitURLBuilder("").itemsToArray(usersOnShift);
        String query = "{\"$and\":[{\"_id\":{\"$nin\":" + usersString + "}},{\"role\":\"basic_user\"}]}";
        IAPIEndpoints apiClient = APIClient.getApiService();
        Call<UserList> call = apiClient.getUsers(query);
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                Log.d("changeShiftActivity", call.request().url().toString());
                List<User> usersNotOnShift = response.body().getPeople();

                if (mWorkersListView != null) {
                    mWorkersListView.setAdapter(new WorkersListViewAdapter(getLayoutInflater(), usersNotOnShift));
                }

                if (usersNotOnShift.size() == 0) {
                    mButton.setEnabled(false);
                    mPromptTextView.setText("Bohuzel neni nikdo, kdo by te nahradil.");
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());
                Log.d("msg", t.getLocalizedMessage());
            }
        });

    }





}
