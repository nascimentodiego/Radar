package br.com.dfn.radar.view.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import br.com.dfn.radar.App;
import br.com.dfn.radar.R;
import br.com.dfn.radar.model.City;
import br.com.dfn.radar.presenter.home.RadarContracts;
import br.com.dfn.radar.presenter.home.RadarPresenter;
import br.com.dfn.radar.util.PermissionUtil;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

public class RadarFragment extends BaseFragment implements OnMapReadyCallback,
        OnSuccessListener<Location>, RadarContracts.View {

    private RadarContracts.Presenter mPresenter;
    private OnRadarFragmentListener mOnRadarFragmentListener;

    private View root;
    private MapView mMapView;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng myLastKnowLocation;

    /**
     * Instantiates a new Radar fragment.
     */
    public RadarFragment() {
        mPresenter = new RadarPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_radar, container, false);

        mMapView = (MapView) root.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(App.getContext());

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            LatLng center = googleMap.getCameraPosition().target;
            mPresenter.doRequest(center.latitude, center.longitude);
        });

        return root;
    }

    private void setMyLocation() {
        if (!(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {
            googleMap.setMyLocationEnabled(true);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRadarFragmentListener) {
            mOnRadarFragmentListener = (OnRadarFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRadarFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnRadarFragmentListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }

        if (mPresenter != null) {
            mPresenter.clearCompositeDisposable();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setMyLocation();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        if (PermissionUtil.hasPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                getActivity(), PermissionUtil.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)) {
            setMyLocation();
        }
    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            myLastKnowLocation = new LatLng(lat, lng);

            googleMap.clear();

            //Add My Location
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLastKnowLocation, 16));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(myLastKnowLocation)
                    .zoom(16)
                    .bearing(90)
                    .build();

            // Animate the change in camera view over 2 seconds
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                    2000, null);

            LatLng center = googleMap.getCameraPosition().target;
            mPresenter.doRequest(center.latitude, center.longitude);
        }
    }


    @Override
    public void showCities(List<City> cities) {
        if (mOnRadarFragmentListener != null) {
            mOnRadarFragmentListener.onPlacesListener(cities);
        }
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * The interface On radar fragment listener.
     */
    public interface OnRadarFragmentListener {
        void onPlacesListener(List<City> cities);
    }
}
