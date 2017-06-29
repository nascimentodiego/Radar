package br.com.dfn.radar.view.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.List;

import br.com.dfn.radar.R;
import br.com.dfn.radar.model.Place;
import br.com.dfn.radar.view.base.activity.BaseOAuthActivity;
import br.com.dfn.radar.view.home.custom.DialogPlaceInformation;

public class MainActivity extends BaseOAuthActivity implements RadarFragment.OnRadarFragmentListener,
        ListFragment.OnListFragmentListener, DialogPlaceInformation.DialogListener {

    private RadarFragment radarFragment;
    private ListFragment listFragment;
    private ProfileFragment profileFragment;
    private DialogPlaceInformation dialog = new DialogPlaceInformation();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_radar:
                    changeFragment(radarFragment, RadarFragment.class.getSimpleName());
                    return true;
                case R.id.navigation_list:
                    changeFragment(listFragment, ListFragment.class.getSimpleName());
                    return true;
                case R.id.navigation_profile:
                    changeFragment(profileFragment, ProfileFragment.class.getSimpleName());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildFragment(savedInstanceState);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void buildFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            radarFragment = (RadarFragment)
                    fragmentManager.findFragmentByTag(RadarFragment.class.getSimpleName());
            if (radarFragment == null) {
                radarFragment = new RadarFragment();
            }
            listFragment = (ListFragment)
                    fragmentManager.findFragmentByTag(ListFragment.class.getSimpleName());
            if (listFragment == null) {
                listFragment = new ListFragment();
            }
            profileFragment = (ProfileFragment)
                    fragmentManager.findFragmentByTag(ProfileFragment.class.getSimpleName());
            if (profileFragment == null) {
                profileFragment = new ProfileFragment();
            }
        } else {
            radarFragment = new RadarFragment();
            listFragment = new ListFragment();
            profileFragment = new ProfileFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.container, radarFragment, RadarFragment.class.getSimpleName())
                    .commit();
        }
    }

    private void changeFragment(Fragment SelectedFragment, String tag) {
        fragmentManager.beginTransaction()
                .replace(R.id.container, SelectedFragment, tag)
                .commit();
    }

    @Override
    public void onPlacesListener(List<Place> places) {
        listFragment.setPlaces(places);
    }

    @Override
    public void onMarkerClick(Place place) {
        dialog.setPlace(place);
        dialog.show(getSupportFragmentManager(), DialogPlaceInformation.class.getSimpleName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if (dialog != null && dialog.getDialog().isShowing()) {
            dialog.dismiss();
        }*/
    }

    @Override
    public void onItemClick(Place place) {

    }

    @Override
    public void onClickDialog(Place place) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=an" + place.getAddress()));
        startActivity(intent);
    }
}
