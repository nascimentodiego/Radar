package br.com.dfn.radar.presenter.login;

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
         * @param resStringId the res string id
         */
        void showLoginError(int resStringId);

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
         * Do login.
         */
        void doLogin();

        /**
         * Is access boolean.
         *
         * @return the boolean
         */
        boolean isAccess();

        /**
         * Check account.
         */
        void checkAccount();

        /**
         * Call main activity.
         */
        void callMainActivity();

        /**
         * On success get user.
         *
         * @param result the result
         */
        void onSuccessGetUser(User result);

    }
}


