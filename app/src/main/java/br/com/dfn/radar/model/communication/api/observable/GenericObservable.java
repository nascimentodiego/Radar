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

import br.com.dfn.radar.model.communication.api.RequestApi;
import br.com.dfn.radar.model.communication.api.ServiceClient;
import io.reactivex.Observable;

/**
 * The type Generic observable.
 *
 * @param <T> the type parameter
 */
public abstract class GenericObservable<T> {

    /**
     * The Api.
     */
    protected RequestApi api;
    /**
     * The Observable.
     */
    protected Observable<T> observable;

    /**
     * Instantiates a new Generic observable.
     */
    public GenericObservable() {
        this.api = ServiceClient.getBuilderRetrofit().create(RequestApi.class);
    }

    /**
     * Gets observable.
     *
     * @return the observable
     */
    public Observable<T> getObservable() {
        return this.observable;
    }

    /**
     * Sets observable.
     *
     * @param observable the observable
     */
    public void setObservable(Observable<T> observable) {
        this.observable = observable;
    }
}
