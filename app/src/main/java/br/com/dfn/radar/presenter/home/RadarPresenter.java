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


import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import br.com.dfn.radar.model.communication.api.observable.GetWeather;
import io.reactivex.disposables.CompositeDisposable;

/**
 * The type Radar presenter.
 */
public class RadarPresenter implements RadarContracts.Presenter {

    private RadarContracts.View mRadarView;
    private CompositeDisposable mCompositeDisposable;
    private GetWeather getWeather;

    /**
     * Instantiates a new Radar presenter.
     *
     * @param mRadarView the radar view
     */
    public RadarPresenter(RadarContracts.View mRadarView) {
        this.mRadarView = mRadarView;
        this.getWeather = new GetWeather();
        this.mCompositeDisposable = new CompositeDisposable();
    }

    /**
     * Instantiates a new Radar presenter.
     *
     * @param radarView           the radar view
     * @param wheater             the wheater
     * @param compositeDisposable the composite disposable
     */
    @VisibleForTesting
    public RadarPresenter(@NonNull RadarContracts.View radarView,
                          @NonNull GetWeather wheater,
                          @NonNull CompositeDisposable compositeDisposable) {
        this.mRadarView = radarView;
        this.getWeather = wheater;
        this.mCompositeDisposable = compositeDisposable;
    }

    @Override
    public void doRequest(double lat, double lng) {
        getWeather.prepareRequest(lat, lng);
        this.mCompositeDisposable.add(
                getWeather.getObservable()
                        .subscribe(
                                resultCities -> mRadarView.showCities(resultCities.getList()),
                                throwable -> mRadarView.showError(throwable),
                                () -> mRadarView.onComplete())
        );
    }

    @Override
    public void clearCompositeDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
