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

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import br.com.dfn.radar.model.City;
import br.com.dfn.radar.model.communication.api.observable.GetWeather;
import br.com.dfn.radar.model.communication.api.observable.SchedulerContracts;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * The type Radar presenter test.
 */
@RunWith(MockitoJUnitRunner.class)
public class RadarPresenterTest {

    private static final double LAT = -8.0631534;
    private static final double LNG = -34.8733055;

    private static final double INVALID_LAT = -100;
    private static final double INVALID_LNG = -100;

    private static final String PROPERTY_CITY_NAME = "name";
    private static final String EXPECTED_CITY_ENTRY = "Recife";

    @Mock
    private RadarContracts.View radarView;

    @Mock
    private SchedulerContracts schedulerContracts;

    @Captor
    private ArgumentCaptor<List<City>> captorList;

    @Captor
    private ArgumentCaptor<Throwable> captorError;

    private CompositeDisposable compositeDisposable;
    private GetWeather wheater;
    private RadarPresenter radarPresenter;

    /**
     * Sets presenter.
     */
    @Before
    public void setupPresenter() {
        this.compositeDisposable = new CompositeDisposable();
        this.wheater = new GetWeather(schedulerContracts);

        // Inject the mock tests class
        radarPresenter = new RadarPresenter(radarView, wheater, compositeDisposable);

        // Mock the behavior of scheduler contracts
        Mockito.when(schedulerContracts.io()).thenReturn(Schedulers.trampoline());
        Mockito.when(schedulerContracts.mainThread()).thenReturn(Schedulers.trampoline());
    }

    /**
     * Test if the request received a no empty list
     */
    @Test
    public void doRequest_success() {
        // call the presenter
        radarPresenter.doRequest(LAT, LNG);

        // Verify if the radarView.showCities has been called
        Mockito.verify(radarView, Mockito.atLeastOnce()).showCities(captorList.capture());

        // Verify if the radarView.onComplete() has been called
        Mockito.verify(radarView, Mockito.atLeastOnce()).onComplete();

        // Get the received list of web service
        final List<City> receivedList = captorList.getValue();

        // check if the list received is not null
        assertThat(receivedList.isEmpty(), Matchers.not(true));

        // Check if the list received contains the entry "Recife"
        assertThat(receivedList, hasItem(hasProperty(PROPERTY_CITY_NAME, is(EXPECTED_CITY_ENTRY))));
    }

    /***
     * Test if a invalid request call the showError in RadarPresenter and if the exception HttpException was thrown
     */
    @Test
    public void doRequest_error() {
        // call the presenter
        radarPresenter.doRequest(INVALID_LAT, INVALID_LNG);

        // Verify if the radarView.showCities has been called
        Mockito.verify(radarView, Mockito.atLeastOnce()).showError(captorError.capture());

        // Check if the web service returns a error on request
        assertThat(captorError.getValue(), instanceOf(retrofit2.adapter.rxjava2.HttpException.class));

        // Check if the showError was called on presenter
        Mockito.verify(radarView, Mockito.atLeastOnce()).showError(captorError.getValue());
    }

}