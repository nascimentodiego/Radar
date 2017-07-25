/*
 * Copyright (C) 2017 Diego Figueredo do Nascimento.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.dfn.radar.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.List;

import br.com.dfn.radar.R;
import br.com.dfn.radar.model.City;
import br.com.dfn.radar.view.base.activity.BaseActivity;
import br.com.dfn.radar.view.detail.DetailActivity;

public class MainActivity extends BaseActivity implements RadarFragment.OnRadarFragmentListener,
        ListFragment.OnListFragmentListener {

    private RadarFragment radarFragment;
    private ListFragment listFragment;

    BottomNavigationView navigation;

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

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
        Intent it = new Intent(this, DetailActivity.class);
        it.putExtra(City.class.getSimpleName(), city);
        startActivity(it);
    }

    @Override
    public void onPlacesListener(List<City> cities) {
        listFragment.setPlaces(cities);
        MenuItem item = navigation.getMenu().getItem(2);
        mOnNavigationItemSelectedListener.onNavigationItemSelected(item);
    }
}
