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
package br.com.dfn.radar.model;

import java.io.Serializable;
import java.util.List;

/**
 * The type City.
 */
public class City implements Serializable {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Weather.
     */
    private List<Weather> weather;
    /**
     * The Main.
     */
    private Temperature main;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets weather.
     *
     * @return the weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * Sets weather.
     *
     * @param weather the weather
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    /**
     * Gets main.
     *
     * @return the main
     */
    public Temperature getMain() {
        return main;
    }

    /**
     * Sets main.
     *
     * @param main the main
     */
    public void setMain(Temperature main) {
        this.main = main;
    }
}
