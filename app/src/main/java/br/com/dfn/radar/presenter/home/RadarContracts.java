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
package br.com.dfn.radar.presenter.home;

import java.util.List;

import br.com.dfn.radar.model.City;
import br.com.dfn.radar.presenter.BasePresenter;

/**
 * The type Radar contracts.
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

        /**
         * Show any error can happens
         */
        void showError(Throwable throwable);

        /**
         * Use to return the information that request was done
         */
        void onComplete();
    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {

        /**
         * Do request.
         *
         * @param lat the latitude
         * @param lng the longitude
         */
        void doRequest(double lat, double lng);

    }
}
