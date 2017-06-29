package br.com.dfn.radar.presenter.home;

import android.support.annotation.NonNull;

import br.com.dfn.radar.model.User;
import br.com.dfn.radar.model.persistence.SharedPreferenceManager;

public class ProfilePresenter implements ProfileContracts.Presenter {

    @NonNull
    private final ProfileContracts.View mProfileView;

    public ProfilePresenter(@NonNull ProfileContracts.View mProfileView) {
        this.mProfileView = mProfileView;
    }

    @Override
    public void showUser() {
        SharedPreferenceManager sharedPreference = SharedPreferenceManager.getInstance();
        User user = new User(sharedPreference.getUserName(), sharedPreference.getUserUrl());
        mProfileView.showUser(user);
    }
}
