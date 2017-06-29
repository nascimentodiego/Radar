package br.com.dfn.radar.view.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import br.com.dfn.radar.R;
import br.com.dfn.radar.view.base.activity.BaseActivity;


public class LoginActivity extends BaseActivity implements LoginFragment.OnLoginFragmentListener {

    private LoginFragment loginFragment;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buildToolbar();
        buildFragment(savedInstanceState);
    }

    @Override
    protected void buildFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            loginFragment = (LoginFragment)
                    fragmentManager.findFragmentByTag(LoginFragment.class.getSimpleName());

        } else {
            loginFragment = new LoginFragment();

            fragmentManager.beginTransaction()
                    .add(R.id.container, loginFragment)
                    .commit();
        }
    }

    @Override
    public void onShowProgress() {
        showProgressDialog();
    }

    @Override
    public void onHideProgress() {
        hideProgressDialog();
    }

    @Override
    public void onErrorLoginFragmentListener() {
        hideProgressDialog();
    }

}
