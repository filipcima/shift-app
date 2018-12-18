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
import android.widget.ListView;

import com.example.cimafilip.shiftapp.activities.DetailDayActivity;
import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.adapters.NotificationsListViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.NotificationList;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.ShiftList;
import com.example.cimafilip.shiftapp.models.SuperiorPlan;
import com.example.cimafilip.shiftapp.models.SuperiorPlanList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.vo.DateData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductionPlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductionPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductionPlanFragment extends Fragment {
    private MCalendarView mCalendarView;
    private List<Shift> shifts;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProductionPlanFragment() {
        String query = new RetrofitURLBuilder("query")
                .add("status", "active")
                .build();
        String embedded = new RetrofitURLBuilder("embedded")
                .add("owner", "1")
                .add("owner.inferiors", "1")
                .build();


        IAPIEndpoints apiService = APIClient.getApiService();
        Call<SuperiorPlanList> call = apiService.getSuperiorPlans(query, "1", "-created", embedded);
        call.enqueue(new Callback<SuperiorPlanList>() {
            @Override
            public void onResponse(Call<SuperiorPlanList> call, Response<SuperiorPlanList> response) {
                Log.d("active production plan", call.request().url().toString());
                if (response.body().getSuperiorPlans() != null && response.body().getSuperiorPlans().size() > 0) {
                    SuperiorPlan plan = response.body().getSuperiorPlans().get(0);
                    getData(plan.get_id());
                }

            }

            @Override
            public void onFailure(Call<SuperiorPlanList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());
                Log.d("msg", t.getLocalizedMessage());
            }
        });
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductionPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductionPlanFragment newInstance(String param1, String param2) {
        ProductionPlanFragment fragment = new ProductionPlanFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_production_plan, container, false);
        mCalendarView = (MCalendarView) rootView.findViewById(R.id.calendar);

        mCalendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                Intent intent = new Intent(getContext(), DetailDayActivity.class);

                intent.putExtra("day", date.getDay());
                intent.putExtra("month", date.getMonth());
                intent.putExtra("year", date.getYear());
                if (date.getMonth() != 0) {
                    startActivity(intent);
                }
            }
        });

        return rootView;
    }

    private void getData(String id) {
        String query = new RetrofitURLBuilder("query")
                .add("superior_plan", id)
                .build();
        String embedded = new RetrofitURLBuilder("embedded")
                .add("workers", "1")
                .build();

        Log.d("embed", embedded);

        IAPIEndpoints apiService = APIClient.getApiService();
        Call<ShiftList> call = apiService.getShifts(query, embedded);
        call.enqueue(new Callback<ShiftList>() {
            @Override
            public void onResponse(Call<ShiftList> call, Response<ShiftList> response) {
                Log.d("Onresponse", call.request().url().toString());
                shifts = response.body().getShifts();

                if (shifts != null) {
                    for (Shift shift: shifts) {
                        String datetime = shift.getDateFrom();
                        String date2 = datetime.split(" ")[0];
                        String[] dateArray = date2.split("/");

                        mCalendarView.markDate(
                                Integer.parseInt("20" + dateArray[2]),
                                Integer.parseInt(dateArray[1]),
                                Integer.parseInt(dateArray[0]));
                    }

                }
            }

            @Override
            public void onFailure(Call<ShiftList> call, Throwable t) {
                Log.d("fail", call.request().url().toString());
                Log.d("msg", t.getLocalizedMessage());

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

    private ArrayList<DateData> getShiftDays() {
        ArrayList<DateData> dates = new ArrayList<>();
        dates.add(new DateData(2018,11,20));
        dates.add(new DateData(2018,11,27));
        dates.add(new DateData(2018,11,1));
        dates.add(new DateData(2018,11,3));
        dates.add(new DateData(2018,11,4));

        return dates;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
