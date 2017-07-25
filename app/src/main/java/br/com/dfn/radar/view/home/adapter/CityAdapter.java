package br.com.dfn.radar.view.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import br.com.dfn.radar.R;
import br.com.dfn.radar.model.City;
import br.com.dfn.radar.model.Weather;

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
public class CityAdapter extends ArrayAdapter<City> {

    /**
     * Instantiates a new city adapter.
     *
     * @param context   the context
     * @param cityList the city list
     */
    public CityAdapter(Context context, List<City> cityList) {
        super(context, 0, cityList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City obj = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_place, parent, false);
        }

        TextView tx_name = (TextView) convertView.findViewById(R.id.tx_name);
        tx_name.setText(obj.name);

        return convertView;
    }
}
