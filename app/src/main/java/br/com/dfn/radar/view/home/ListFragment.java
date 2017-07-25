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

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.com.dfn.radar.App;
import br.com.dfn.radar.R;
import br.com.dfn.radar.model.City;
import br.com.dfn.radar.view.base.fragment.BaseFragment;
import br.com.dfn.radar.view.home.adapter.CityAdapter;


public class ListFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private View root;
    private ListView lst_places;

    private CityAdapter adapter;
    private OnListFragmentListener mOnListFragmentListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_list, container, false);
        lst_places = (ListView) root.findViewById(R.id.lst_places);
        lst_places.setAdapter(adapter);
        lst_places.setOnItemClickListener(this);
        return root;
    }


    public void setPlaces(List<City> placeList) {
        adapter = new CityAdapter(App.getContext(), placeList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentListener) {
            mOnListFragmentListener = (OnListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRadarFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnListFragmentListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnListFragmentListener.onItemClick(adapter.getItem(position));
    }

    /**
     * The interface On list fragment listener.
     */
    public interface OnListFragmentListener {


        /**
         * on item click
         */
        void onItemClick(City city);
    }


}
