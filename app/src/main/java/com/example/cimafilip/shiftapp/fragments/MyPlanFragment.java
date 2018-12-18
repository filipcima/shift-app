package com.example.cimafilip.shiftapp.fragments;

import android.content.Context;
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

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.adapters.ShiftsByDayViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.Shift;
import com.example.cimafilip.shiftapp.models.ShiftList;
import com.example.cimafilip.shiftapp.models.SuperiorPlan;
import com.example.cimafilip.shiftapp.models.SuperiorPlanList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyPlanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyPlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPlanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView mListView;

    private OnFragmentInteractionListener mListener;

    public MyPlanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyPlanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyPlanFragment newInstance(String param1, String param2) {
        MyPlanFragment fragment = new MyPlanFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_my_plan, container, false);
        mListView = rootView.findViewById(R.id.listViewMyPlan);

        String query = "{\"$and\": [{\"owner\": \"5c0820e3766cf241597b3324\"},{\"status\": \"active\"}]}";
        String embedded = new RetrofitURLBuilder("embedded")
                .add("owner", "1")
                .add("owner.inferiors", "1")
                .build();

        final IAPIEndpoints apiClient = APIClient.getApiService();
        Call<SuperiorPlanList> call = apiClient.getSuperiorPlans(query, "1", "-created", embedded);
        call.enqueue(new Callback<SuperiorPlanList>() {
            @Override
            public void onResponse(Call<SuperiorPlanList> call, Response<SuperiorPlanList> response) {
                Log.d("call1", call.request().url().toString());

                if (response.body() == null) {
                    return;
                }
                List<SuperiorPlan> plans = response.body().getSuperiorPlans();

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String idUser = prefs.getString("idUser", "");
                if (plans.size() == 0) {
                    return;
                }
                String idSuperiorPlan = plans.get(0).get_id();
                String where = "{\"$and\":[{\"workers\":{\"$in\":[\"" + idUser + "\"]}},{\"superior_plan\":\""+ idSuperiorPlan +"\"}]}";
                String order = new RetrofitURLBuilder("sort")
                        .add("date_from", "1")
                        .build();
                String embedded = new RetrofitURLBuilder("embedded")
                        .add("workers", "1")
                        .build();

                Call<ShiftList> call2 = apiClient.getShiftsByPlan(where, order, embedded);
                call2.enqueue(new Callback<ShiftList>() {
                    @Override
                    public void onResponse(Call<ShiftList> call, Response<ShiftList> response) {
                        Log.d("myplanFragment", call.request().url().toString());

                        if (response.body() != null) {
                            List<Shift> sl = response.body().getShifts();
                            ListView mListView = rootView.findViewById(R.id.listViewMyPlan);
                            if (mListView != null) {
                                mListView.setAdapter(new ShiftsByDayViewAdapter(getLayoutInflater(), sl));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ShiftList> call, Throwable t) {
                        Log.d("fail2", call.request().url().toString());
                        Log.d("msg", t.getLocalizedMessage());
                    }
                });

            }

            @Override
            public void onFailure(Call<SuperiorPlanList> call, Throwable t) {
                Log.d("fail1", call.request().url().toString());
                Log.d("msg", t.getLocalizedMessage());
            }
        });


        return rootView;
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
