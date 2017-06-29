package br.com.dfn.radar.view.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import br.com.dfn.radar.App;
import br.com.dfn.radar.R;
import br.com.dfn.radar.model.Place;
import br.com.dfn.radar.presenter.home.RadarContracts;
import br.com.dfn.radar.presenter.home.RadarPresenter;
import br.com.dfn.radar.util.PermissionUtil;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

public class RadarFragment extends BaseFragment implements OnMapReadyCallback,
        OnSuccessListener<Location>, GoogleMap.OnMarkerClickListener, RadarContracts.View {

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

        return root;
    }

    private void setMyLocation() {
        if (!(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)) {
            googleMap.setMyLocationEnabled(false);
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), this);
        }
    }

    private void populate(List<Place> places) {
        if (isAdded()) {
            for (final Place place : places) {
                final LatLng mapCenter = new LatLng(place.getLat(), place.getLng());
                BitmapDescriptor icon;

                if (place.getType().equals(Place.TYPE_AIRPORT)) {
                    icon = BitmapDescriptorFactory.fromBitmap(getPlaceIcon(R.drawable.place_airport));
                } else if (place.getType().equals(Place.TYPE_NIGHT_CLUB)) {
                    icon = BitmapDescriptorFactory.fromBitmap(getPlaceIcon(R.drawable.place_party));
                } else if (place.getType().equals(Place.TYPE_RESTAURANT)) {
                    icon = BitmapDescriptorFactory.fromBitmap(getPlaceIcon(R.drawable.place_restaurant));
                } else if (place.getType().equals(Place.TYPE_SHOPPING)) {
                    icon = BitmapDescriptorFactory.fromBitmap(getPlaceIcon(R.drawable.place_shopping));
                } else {
                    icon = BitmapDescriptorFactory.fromBitmap(getPlaceIcon(R.drawable.place_market));
                }

                googleMap.addMarker(new MarkerOptions()
                        .icon(icon)
                        .position(mapCenter)).setTag(place);
            }


        }
    }

    public Bitmap getPlaceIcon(int resourceId) {
        int height = 80;
        int width = 80;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(resourceId);
        Bitmap b = bitmapdraw.getBitmap();
        return Bitmap.createScaledBitmap(b, width, height, false);
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

        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            myLastKnowLocation = new LatLng(lat, lng);

            googleMap.clear();

            //Add My Location
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(getPlaceIcon(R.drawable.profile_active));
            googleMap.addMarker(new MarkerOptions()
                    .icon(icon)
                    .position(myLastKnowLocation));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLastKnowLocation, 16));

            CameraPosition cameraPosition = CameraPosition.builder()
                    .target(myLastKnowLocation)
                    .zoom(16)
                    .bearing(90)
                    .build();

            // Animate the change in camera view over 2 seconds
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                    2000, null);

            mPresenter.doRequest(lat, lng, "1000");
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() instanceof Place) {
            Place obj = (Place) marker.getTag();
            if (mOnRadarFragmentListener != null) {
                mOnRadarFragmentListener.onMarkerClick(obj);
            }
        }

        return false;
    }

    @Override
    public void showPlaces(List<Place> places) {
        populate(places);
        if (mOnRadarFragmentListener != null) {
            mOnRadarFragmentListener.onPlacesListener(places);
        }
    }

    /**
     * The interface On radar fragment listener.
     */
    public interface OnRadarFragmentListener {
        /**
         * On place listener
         */
        void onPlacesListener(List<Place> places);

        void onMarkerClick(Place place);
    }
}
