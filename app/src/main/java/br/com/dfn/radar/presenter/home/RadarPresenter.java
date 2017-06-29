package br.com.dfn.radar.presenter.home;


import java.util.List;

import br.com.dfn.radar.model.Place;
import br.com.dfn.radar.model.communication.Parser;
import br.com.dfn.radar.model.communication.RequestManager;

public class RadarPresenter implements RadarContracts.Presenter, RequestManager.CallbackRequest {

    private final RadarContracts.View mRadarView;

    /**
     * Instantiates a new Radar presenter.
     *
     * @param mRadarView the radar view
     */
    public RadarPresenter(RadarContracts.View mRadarView) {
        this.mRadarView = mRadarView;
    }

    public void doRequest(double lat, double lng, String radius) {
        String param = "{FILTER}";
        String pUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                + lat + "," + lng + "&radius=" + radius
                + "&key=AIzaSyBK5aGJiR7TH5B599cdoM5ebcBdWX-9W7U&type=" + "{FILTER}";

        RequestManager.startRequest(pUrl.replace(param,"shopping_mall"),this);
        RequestManager.startRequest(pUrl.replace(param,"night_club"),this);
        RequestManager.startRequest(pUrl.replace(param,"restaurant"),this);
        RequestManager.startRequest(pUrl.replace(param,"airport"),this);
        RequestManager.startRequest(pUrl.replace(param,"grocery_or_supermarket"),this);

    }

    @Override
    public void onResultRequest(byte[] bytes) {
        List<Place> places = Parser.parseBytesToObject(bytes);
        mRadarView.showPlaces(places);
    }
}
