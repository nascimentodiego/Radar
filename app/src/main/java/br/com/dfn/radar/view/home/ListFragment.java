package br.com.dfn.radar.view.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import br.com.dfn.radar.App;
import br.com.dfn.radar.R;
import br.com.dfn.radar.model.Place;
import br.com.dfn.radar.view.base.fragment.BaseFragment;
import br.com.dfn.radar.view.home.adapter.PlaceAdapter;


public class ListFragment extends BaseFragment {
    private View root;
    private ListView lst_places;

    private PlaceAdapter adapter;
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
        return root;
    }

    public void setPlaces(List<Place> placeList) {
        adapter = new PlaceAdapter(App.getContext(), placeList);
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

    /**
     * The interface On list fragment listener.
     */
    public interface OnListFragmentListener {
        /**
         * on item click
         */
        void onItemClick(Place place);
    }


}
