package br.com.dfn.radar.presenter.login;

import android.os.Bundle;
import android.support.annotation.NonNull;

import br.com.dfn.radar.model.User;


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
    public void doLogin() {
    }

    @Override
    public boolean isAccess() {
        return false;
    }

    @Override
    public void checkAccount() {
        if (isAccess()) {
            mLoginView.callMainActivity();
        }
    }

    @Override
    public void callMainActivity() {
        mLoginView.callMainActivity();
    }

    @Override
    public void onSuccessGetUser(User result) {

    }
}
