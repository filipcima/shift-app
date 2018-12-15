package com.example.cimafilip.shiftapp.activities;
import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.adapters.SetShiftsListViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.ShiftHelper;
import com.example.cimafilip.shiftapp.models.ShiftList;
import com.example.cimafilip.shiftapp.models.User;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetShiftsActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingButton;
    private ListView mListView;
    private ShiftList shiftList;
    private String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_shifts);


        getSupportActionBar().setTitle("Navol, kdy můžeš pracovat.");
        mListView = findViewById(R.id.setShiftsListView);
        mFloatingButton = findViewById(R.id.floatingActionButton);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        idUser = prefs.getString("idUser", "");

        getData();

        mFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView shiftDetailTextView;
                Switch canGoSwitch;
                List<Shift> shifts = shiftList.getShifts();
                for (int i = 0; i < mListView.getCount(); i++) {
                    View item = mListView.getChildAt(i);
                    boolean userOnShift = false;
                    int index = 0;

                    shiftDetailTextView = item.findViewById(R.id.shiftDetailTextView);
                    canGoSwitch = item.findViewById(R.id.canGoSwitch);

                    Shift shift = shifts.get(i);

                    List<User> workers = shift.getWorkers();

                    for (int j = 0; j < workers.size(); j++) {
                        if (workers.get(j).get_id().equals(idUser)) {
                            userOnShift = true;
                            index = j;
                        }
                    }
                    if ((!userOnShift && canGoSwitch.isChecked()) || (userOnShift && !canGoSwitch.isChecked())) {
                        if (userOnShift && !canGoSwitch.isChecked()) {
                            shift.getWorkers().remove(index);
                        } else if (!userOnShift && canGoSwitch.isChecked()) {
                            User userWithId = new User();
                            userWithId.set_id(idUser);
                            shift.getWorkers().add(userWithId);
                        }
                    }


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
                            Toast.makeText(getApplicationContext(), "Shifts saved.", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ShiftHelper> call, Throwable t) {
                            Log.d("fail", call.request().url().toString());
                            Log.d("fail", t.getLocalizedMessage());
                            Toast.makeText(getApplicationContext(), "Error occured during saving shifts.", Toast.LENGTH_LONG).show();
                        }
                    });

                    Log.d("shift detail", String.valueOf(shiftDetailTextView.getText()));
                    Log.d("shift detail", String.valueOf(canGoSwitch.isChecked()));
                }
            }
        });
    }

    private void getData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String pendingPlanId = prefs.getString("pendingPlanId", "");
        String where = new RetrofitURLBuilder("query")
                .add("superior_plan", pendingPlanId)
                .build();

        String sort = new RetrofitURLBuilder("sort")
                .add("date_from", "1")
                .build();

        String embedded = new RetrofitURLBuilder("embedded")
                .add("workers", "1")
                .build();

        IAPIEndpoints apiClient = APIClient.getApiService();
        Call<ShiftList> call = apiClient.getShiftsByPlan(where, sort, embedded);
        call.enqueue(new Callback<ShiftList>() {
            @Override
            public void onResponse(Call<ShiftList> call, Response<ShiftList> response) {
                Log.d("good", call.request().url().toString());
                shiftList = response.body();

                if (mListView != null) {
                    mListView.setAdapter(new SetShiftsListViewAdapter(getLayoutInflater(), shiftList.getShifts()));
                }

            }

            @Override
            public void onFailure(Call<ShiftList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());
                Log.d("fail", t.getLocalizedMessage());
            }
        });
    }

}
