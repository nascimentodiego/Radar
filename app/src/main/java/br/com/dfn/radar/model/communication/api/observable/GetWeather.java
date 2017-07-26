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
package br.com.dfn.radar.model.communication.api.observable;

import br.com.dfn.radar.model.ResultCities;
import br.com.dfn.radar.model.communication.api.ServiceClient;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Get weather.
 */
public class GetWeather extends GenericObservable<ResultCities> {

    /**
     * Instantiates a new Get weather.
     *
     * @param lat the lat
     * @param lon the lon
     */
    public GetWeather(double lat, double lon) {
        setObservable(api.getWeather(lat, lon, ServiceClient.APPID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).cache());
    }

}
