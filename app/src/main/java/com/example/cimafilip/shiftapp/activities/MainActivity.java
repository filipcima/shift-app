package com.example.cimafilip.shiftapp.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.cimafilip.shiftapp.R;
import com.example.cimafilip.shiftapp.fragments.DashboardFragment;
import com.example.cimafilip.shiftapp.fragments.MyPlanFragment;
import com.example.cimafilip.shiftapp.fragments.NotificationsFragment;
import com.example.cimafilip.shiftapp.fragments.ProductionPlanFragment;
import com.example.cimafilip.shiftapp.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity implements DashboardFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener, ProductionPlanFragment.OnFragmentInteractionListener, MyPlanFragment.OnFragmentInteractionListener, NotificationsFragment.OnFragmentInteractionListener {

    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();
        toolbar.setTitle("Dashboard");
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#fe485a")));

        loadFragment(new DashboardFragment());

        BottomNavigationView menu = findViewById(R.id.navigation);
        menu.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    toolbar.setTitle("Dashboard");
                    fragment = new DashboardFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_production_plan:
                    toolbar.setTitle("Production plan");
                    fragment = new ProductionPlanFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_my_plan:
                    toolbar.setTitle("My plan");
                    fragment = new MyPlanFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    toolbar.setTitle("Notifications");
                    fragment = new NotificationsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri){

    }

}