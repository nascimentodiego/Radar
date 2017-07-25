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
import br.com.dfn.radar.model.City;
import br.com.dfn.radar.view.base.activity.BaseActivity;

public class MainActivity extends BaseActivity implements RadarFragment.OnRadarFragmentListener,
        ListFragment.OnListFragmentListener {

    private RadarFragment radarFragment;
    private ListFragment listFragment;

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

        } else {
            radarFragment = new RadarFragment();
            listFragment = new ListFragment();

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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onItemClick(City city) {

    }
}
