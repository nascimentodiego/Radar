package br.com.dfn.radar.view.home.custom;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.dfn.radar.R;
import br.com.dfn.radar.model.Place;

public class DialogPlaceInformation extends DialogFragment implements View.OnClickListener {

    private TextView tx_name;
    private TextView tx_address;
    private Place place;
    private DialogListener mDialogListener;

    public DialogPlaceInformation() {
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public void onClick(View v) {
        if (mDialogListener != null) {
            mDialogListener.onClickDialog(place);
        }
    }

    public interface DialogListener {
        void onClickDialog(Place place);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogListener) {
            mDialogListener = (DialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRadarFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDialogListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_place_information, container);
        tx_name = (TextView) view.findViewById(R.id.tx_name);
        tx_address = (TextView) view.findViewById(R.id.tx_address);
        view.findViewById(R.id.bt_track_route).setOnClickListener(this);

        if (place != null) {
            tx_name.setText(place.getName());
            tx_address.setText(place.getAddress());
        }

        getDialog().setTitle(null);

        return view;
    }

}
