package br.com.dfn.radar.presenter.home;

import java.util.List;

import br.com.dfn.radar.model.Place;
import br.com.dfn.radar.presenter.BasePresenter;

/**
 * The type Login contracts.
 */
public class RadarContracts {

    /**
     * The interface View.
     */
    public interface View {
        /**
         * Show places
         */
        void showPlaces(List<Place> places);
    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {

        /**
         * Do request.
         *
         * @param lat    the latitude
         * @param lng    the longitude
         * @param radius the radius
         */
        void doRequest(double lat, double lng, String radius);

    }
}
