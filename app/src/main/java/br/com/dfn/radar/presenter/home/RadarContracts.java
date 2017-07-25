package br.com.dfn.radar.presenter.home;

import java.util.List;

import br.com.dfn.radar.model.City;
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
         * Show cities
         */
        void showCities(List<City> cities);
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
         */
        void doRequest(double lat, double lng);

    }
}
