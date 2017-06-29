package br.com.dfn.radar.presenter.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.dfn.radar.model.User;
import br.com.dfn.radar.model.persistence.SharedPreferenceManager;


/**
 * The type Login presenter.
 */
public class LoginPresenter implements LoginContracts.Presenter {
    @NonNull
    private final LoginContracts.View mLoginView;


    /**
     * Instantiates a new Login presenter.
     *
     * @param loginView the login view
     */
    public LoginPresenter(@NonNull LoginContracts.View loginView) {
        this.mLoginView = loginView;

    }

    @Override
    public void saveUser(JSONObject object, GraphResponse response) {
        try {
            String profilePicUrl = "";
            String name = object.getString("name");
            JSONObject data = response.getJSONObject();
            if (data.has("picture")) {
                profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");
            }
            SharedPreferenceManager sharedPreference = SharedPreferenceManager.getInstance();
            sharedPreference.storeUserName(name);
            sharedPreference.storeUserUrl(profilePicUrl);
            mLoginView.showSuccessLogin();

        } catch (JSONException e) {
            e.printStackTrace();
            mLoginView.showLoginError(e);
        }
    }
}
