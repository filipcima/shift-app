package com.example.cimafilip.shiftapp.fragments;

import android.app.ProgressDialog;
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
import com.example.cimafilip.shiftapp.adapters.MyPlanListViewAdapter;
import com.example.cimafilip.shiftapp.adapters.NotificationsListViewAdapter;
import com.example.cimafilip.shiftapp.api.APIClient;
import com.example.cimafilip.shiftapp.api.IAPIEndpoints;
import com.example.cimafilip.shiftapp.helpers.RetrofitURLBuilder;
import com.example.cimafilip.shiftapp.models.Notification;
import com.example.cimafilip.shiftapp.models.NotificationList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotificationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NotificationList notificationList;

    private ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;

    public NotificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading....");
        progressDialog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications, container, false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String idUser = prefs.getString("idUser", "");
        getData(idUser);

        return rootView;
    }

    private void getData(String id) {
        IAPIEndpoints apiService = APIClient.getApiService();
        String query = "{\"for_users\":{\"$in\": [\""+ id +"\"]}}";
        String embed = new RetrofitURLBuilder("embedded")
                .add("from_user", "1")
                .add("for_users", "1")
                .add("shift", "1")
                .add("shift.workers", "1")
                .build();

        Log.d("embedded", embed);
        String sort = new RetrofitURLBuilder("sort")
                .add("_created", "-1")
                .build();

        Call<NotificationList> call = apiService.getNotifications(query, sort, embed);
        call.enqueue(new Callback<NotificationList>() {
            @Override
            public void onResponse(Call<NotificationList> call, Response<NotificationList> response) {
                Log.d("notifications ok", call.request().url().toString());

                notificationList = response.body();
                ListView mListView = getView().findViewById(R.id.notificationsListView);
                if (mListView != null) {
                    mListView.setAdapter(new NotificationsListViewAdapter(getLayoutInflater(), notificationList));
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<NotificationList> call, Throwable t) {
                Log.d("url", call.request().url().toString());
                Log.d("stackTrace", t.getLocalizedMessage());
            }

        });
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
