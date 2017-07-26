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
package br.com.dfn.radar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.List;

import br.com.dfn.radar.model.City;
import br.com.dfn.radar.model.ResultCities;
import br.com.dfn.radar.model.communication.api.observable.GetWeather;
import br.com.dfn.radar.presenter.home.RadarContracts;
import br.com.dfn.radar.presenter.home.RadarPresenter;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * The type Radar presenter test.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({CompositeDisposable.class})
public class RadarPresenterTest {

    @Mock
    private RadarContracts.View radarView;

    @Mock
    private CompositeDisposable compositeDisposable;

    @Mock
    private GetWeather wheater;

    @Mock
    private ResultCities resultCities;

    private RadarPresenter radarPresenter;

    /**
     * Sets presenter.
     */
    @Before
    public void setupPresenter() {
        // Init all mockito class
        MockitoAnnotations.initMocks(this);

        // Mock the resultCities
        Mockito.when(resultCities.getList()).thenReturn(getStubCities());

        // Inject the mock tests class
        radarPresenter = new RadarPresenter(radarView, wheater, compositeDisposable);
    }

    /**
     * Test if the request mocked calls all methods of view contracts
     */
    @Test
    public void doRequest_success() {
        // Mock the wheater.getObservable()
        PowerMockito.when(wheater.getObservable()).thenReturn(Observable.create(subscriber -> {
            subscriber.onNext(resultCities);
            subscriber.onComplete();
        }));

        // call the presenter
        radarPresenter.doRequest(10, 10);

        // Verify if the radarView.showCities has been called
        Mockito.verify(radarView, Mockito.atLeastOnce()).showCities(resultCities.getList());

        // Verify if the radarView.onComplete() has been called
        Mockito.verify(radarView, Mockito.atLeastOnce()).onComplete();
    }

    /**
     * Test if the request mocked  throws the RuntimeException
     */
    @Test
    public void doRequest_error() {
        // This error will be throws when the onError is called in
        final RuntimeException runtimeError = new RuntimeException();

        // Mock the wheater.getObservable()
        PowerMockito.when(wheater.getObservable()).thenReturn(Observable.create(subscriber -> {

            // throws a runtimeException
            subscriber.onError(runtimeError);
        }));

        // call the presenter
        radarPresenter.doRequest(10, 10);

        // Verify if a exception has been throws
        Mockito.verify(radarView, Mockito.atLeastOnce()).showError(runtimeError);
    }

    /**
     * Stub a list of cities objects
     *
     * @return a list of cities objects
     */
    private List<City> getStubCities() {
        final City city = new City();
        city.setName("Prazeres");

        final List<City> cities = Arrays.asList(city);

        return cities;
    }
}