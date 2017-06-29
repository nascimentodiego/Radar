package br.com.dfn.radar.presenter.home;


import br.com.dfn.radar.model.User;
import br.com.dfn.radar.presenter.BasePresenter;

public class ProfileContracts {

    /**
     * The interface View.
     */
    public interface View {
        /**
         * Show places
         */
        void showUser(User user);

        void disconnect();
    }

    /**
     * The interface Presenter.
     */
    public interface Presenter extends BasePresenter {

        /**
         * Show User
         *
         */
        void showUser();

    }
}
