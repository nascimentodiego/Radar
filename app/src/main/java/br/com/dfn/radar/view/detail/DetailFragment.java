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
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dfn.radar.R;
import br.com.dfn.radar.model.City;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

/**
 * The type Detail fragment.
 */
public class DetailFragment extends BaseFragment {
    /**
     * The constant INDEX.
     */
    public static final int INDEX = 0;
    private View root;
    private TextView txtName, txtTemperatureMin, txtTemperatureMax, txtWeather;

    private City city;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_detail, container, false);

        if (savedInstanceState == null && getArguments() != null) {
            city = (City) getArguments().getSerializable(City.class.getSimpleName());
        }

        initView();

        return root;
    }

    private void initView() {
        txtName = (TextView) root.findViewById(R.id.txtName);
        txtTemperatureMin = (TextView) root.findViewById(R.id.txtTemperatureMin);
        txtTemperatureMax = (TextView) root.findViewById(R.id.txtTemperatureMax);
        txtWeather = (TextView) root.findViewById(R.id.txtWeather);

        if (null != city) {
            txtName.setText(city.getName());
            String minTemperature = getString(R.string.label_min_temperature,
                    String.valueOf(city.getMain().getTempMin()));
            String maxTemperature = getString(R.string.label_max_temperature,
                    String.valueOf(city.getMain().getTempMax()));
            txtTemperatureMin.setText(minTemperature);
            txtTemperatureMax.setText(maxTemperature);
            if (city.getWeather() != null && !city.getWeather().isEmpty()) {
                String weather = getString(R.string.label_wethear,
                        city.getWeather().get(INDEX).getDescription());
                txtWeather.setText(weather);
            }
        }

    }
}
