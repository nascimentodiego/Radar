package br.com.dfn.radar.presenter.login;

import com.facebook.GraphResponse;

import org.json.JSONObject;

import br.com.dfn.radar.model.User;
import br.com.dfn.radar.presenter.BasePresenter;

/**
 * The type Login contracts.
 */
public class LoginContracts {

    /**
     * The interface View.
     */
    public interface View {
        /**
         * Show login error.
         *
         * @param exception the exception throw
         */
        void showLoginError(Exception exception);

        /**
         * Show success login.
         */
        void showSuccessLogin();

        /**
         * Call main activity.
         */
        void callMainActivity();

    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {
        /**
         * save user.
         */
        void saveUser(JSONObject object, GraphResponse response);
    }
}


