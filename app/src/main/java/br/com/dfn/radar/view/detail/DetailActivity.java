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
package br.com.dfn.radar.view.detail;

import android.os.Bundle;

import br.com.dfn.radar.R;
import br.com.dfn.radar.view.base.activity.BaseActivity;
import br.com.dfn.radar.view.home.RadarFragment;

/**
 * The type Detail activity.
 */
public class DetailActivity extends BaseActivity {

    private DetailFragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        buildFragment(savedInstanceState);
    }

    @Override
    protected void buildFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            detailFragment = (DetailFragment)
                    fragmentManager.findFragmentByTag(RadarFragment.class.getSimpleName());
            if (detailFragment == null) {
                detailFragment = new DetailFragment();
                detailFragment.setArguments(getIntent().getExtras());
            }

        } else {
            detailFragment = new DetailFragment();
            detailFragment.setArguments(getIntent().getExtras());
            fragmentManager.beginTransaction()
                    .add(R.id.container, detailFragment, RadarFragment.class.getSimpleName())
                    .commit();
        }
    }
}
