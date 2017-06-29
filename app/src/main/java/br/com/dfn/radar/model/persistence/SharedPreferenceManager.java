package br.com.dfn.radar.model.persistence;


import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.camera2.params.StreamConfigurationMap;

import br.com.dfn.radar.App;

public class SharedPreferenceManager {

    private static SharedPreferenceManager sInstance;
    public static final String PREFERENCE_NAME = "radar_preferences";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_URL = "user_url";


    private SharedPreferenceManager() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SharedPreferenceManager getInstance() {
        if (sInstance == null) {
            sInstance = new SharedPreferenceManager();
        }
        return sInstance;
    }

    /***
     * Get the butler_preferences.xml
     *
     * @return the sharedPreferences
     */
    private static SharedPreferences getPreferences() {
        return App.getContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Store user name
     *
     * @param name the user name
     */
    public void storeUserName(String name) {
        getPreferences().edit().putString(KEY_USER_NAME, name).commit();
    }

    /**
     * Returns the user name Preference
     *
     * @return the user name
     */
    public String getUserName() {
        return getPreferences().getString(KEY_USER_NAME, "");
    }

    /**
     * Store user picture url
     *
     * @param url the user picture url
     */
    public void storeUserUrl(String url) {
        getPreferences().edit().putString(KEY_USER_URL, url).commit();
    }

    /**
     * Returns the user name Preference
     *
     * @return the user picture url
     */
    public String getUserUrl() {
        return getPreferences().getString(KEY_USER_URL, "");
    }
}
