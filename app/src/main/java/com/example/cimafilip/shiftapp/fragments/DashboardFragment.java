package com.example.cimafilip.shiftapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.activities.MainActivity;
import com.example.cimafilip.shiftapp.activities.SetShiftsActivity;
import com.example.cimafilip.shiftapp.adapters.MyPlanListViewAdapter;
import com.example.cimafilip.shiftapp.adapters.NotificationsListViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.NotificationList;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.ShiftList;
import com.example.cimafilip.shiftapp.models.SuperiorPlan;
import com.example.cimafilip.shiftapp.models.SuperiorPlanList;
import com.example.cimafilip.shiftapp.models.SuperiorPlanList;
import com.example.cimafilip.shiftapp.models.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mWelcomeTextView;
    private TextView mReminderTextView;
    private Button mShowShiftsButton;
    private String idUser;
    private SharedPreferences prefs;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String nameUser = prefs.getString("nameUser", "");

        mWelcomeTextView = rootView.findViewById(R.id.welcomeTextView);
        mWelcomeTextView.setText("Vítej, " + nameUser);

        mReminderTextView = rootView.findViewById(R.id.reminderTextView);
        mReminderTextView.setVisibility(View.INVISIBLE);

        mShowShiftsButton = rootView.findViewById(R.id.setShiftsButton);
        mShowShiftsButton.setVisibility(View.INVISIBLE);
        mShowShiftsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SetShiftsActivity.class);
                startActivity(intent);
            }
        });

        idUser = prefs.getString("idUser", "");
        getNextShift();
        getPendingSuperiorPlan();

        return rootView;
    }

    private void getPendingSuperiorPlan() {
         String query = new RetrofitURLBuilder("query")
                 .add("status", "pending")
                 .build();
         String embedded = new RetrofitURLBuilder("embedded")
                 .add("owner", "1")
                 .build();
         String limit = new RetrofitURLBuilder("limit")
                 .add("1")
                 .build();
         String order = new RetrofitURLBuilder("sort")
                 .add("_created", "-1")
                 .build();

        IAPIEndpoints apiService = APIClient.getApiService();
        Call<SuperiorPlanList> call = apiService.getSuperiorPlans(query, limit, order, embedded);
        call.enqueue(new Callback<SuperiorPlanList>() {
            @Override
            public void onResponse(Call<SuperiorPlanList> call, Response<SuperiorPlanList> response) {
                Log.d("good", call.request().url().toString());
                SuperiorPlanList superiorPlans = response.body();

                if (superiorPlans.getSuperiorPlans().size() > 0) {
                    SuperiorPlan superiorPlan = superiorPlans.getSuperiorPlans().get(0);
                    if (superiorPlan.getStatus().equals("pending")) {
                        prefs.edit()
                                .putString("pendingPlanId", superiorPlan.get_id())
                                .apply();
                        mShowShiftsButton.setVisibility(View.VISIBLE);
                        mReminderTextView.setVisibility(View.VISIBLE);
                    } else {
                        mShowShiftsButton.setVisibility(View.INVISIBLE);
                        mReminderTextView.setVisibility(View.INVISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<SuperiorPlanList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());
                Log.d("fail", t.getLocalizedMessage());

            }
        });

    }

    private void getNextShift() {
        String now = new SimpleDateFormat("dd/MM/yy HH:mm:ss").format(Calendar.getInstance().getTime());

        String query = "{\"$and\":[{\"workers\":{\"$in\":[\"" + idUser + "\"]}},{\"date_from\":{\"$gt\":\""+ now +"\"}}]}";

        String order = new RetrofitURLBuilder("sort")
                .add("date_from", "-1")
                .build();

        String limit = new RetrofitURLBuilder("limit")
                .add("1")
                .build();

        String embedded = new RetrofitURLBuilder("embedded")
                .add("workers", "1")
                .build();

        IAPIEndpoints apiService = APIClient.getApiService();
        Call<ShiftList> call = apiService.getNextShift(query, limit, order, embedded);
        call.enqueue(new Callback<ShiftList>() {
            @Override
            public void onResponse(Call<ShiftList> call, Response<ShiftList> response) {
                Log.d("good", call.request().url().toString());
                ShiftList list = response.body();
                if (list != null) {
                    TextView workers = getView().findViewById(R.id.shiftPlaceName2);
                    TextView date = getView().findViewById(R.id.textView2);
                    TextView shiftTime = getView().findViewById(R.id.shiftTime2);
                    TextView dayText = getView().findViewById(R.id.dayText2);

                    if (list.getShifts().size() > 0) {
                        Shift s = list.getShifts().get(0);

                        String day = s.getDateFrom().split(" ")[0].split("/")[0];
                        String month = s.getDateFrom().split(" ")[0].split("/")[1];
                        String startTime = s.getDateFrom().split(" ")[1].substring(0, 5);
                        String endTime = s.getDateTo().split(" ")[1].substring(0, 5);
                        StringBuilder workersString = new StringBuilder();

                        int i = 0;
                        for (User worker : s.getWorkers()) {
                            String fullName = worker.getFirstName() + " " + worker.getSecondName();
                            workersString.append(fullName);

                            if (!(i++ == s.getWorkers().size() - 1)) {
                                workersString.append(", ");
                            }
                        }

                        workers.setText(workersString.toString());
                        date.setText(day + "." + month + ".");
                        shiftTime.setText(startTime + "-" + endTime);
                    } else {
                        workers.setText("");
                        date.setText("");
                        shiftTime.setText("");
                        dayText.setText("");
                    }
                }
            }

            @Override
            public void onFailure(Call<ShiftList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());

            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
