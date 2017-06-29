package br.com.dfn.radar.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.dfn.radar.view.home.MainActivity;
import br.com.dfn.radar.R;
import br.com.dfn.radar.presenter.login.LoginContracts;
import br.com.dfn.radar.presenter.login.LoginPresenter;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

/**
 * The type Login fragment.
 */
public class LoginFragment extends BaseFragment implements LoginContracts.View, View.OnClickListener {

    private View root;
    private Button btn_login;

    private LoginContracts.Presenter mPresenter;
    private OnLoginFragmentListener mOnLoginFragmentListener;

    /**
     * Instantiates a new Login fragment.
     */
    public LoginFragment() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        initView();

        mPresenter.checkAccount();

        return root;
    }

    private void initView() {
        btn_login = (Button) root.findViewById(R.id.bt_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginFragmentListener) {
            mOnLoginFragmentListener = (OnLoginFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginFragmentListener = null;
    }

    @Override
    public void showLoginError(int resStringId) {
        Snackbar.make(root, getResources().getText(resStringId), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        mOnLoginFragmentListener.onErrorLoginFragmentListener();
    }

    @Override
    public void showSuccessLogin() {
        mOnLoginFragmentListener.onHideProgress();
        mPresenter.callMainActivity();
    }

    @Override
    public void callMainActivity() {
        Intent it = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(it);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        mPresenter.callMainActivity();
    }

    /**
     * The interface On login fragment listener.
     */
    public interface OnLoginFragmentListener {

        /**
         * On show progress.
         */
        void onShowProgress();

        /**
         * On hide progress.
         */
        void onHideProgress();

        /**
         * On error login fragment listener.
         */
        void onErrorLoginFragmentListener();

    }
}
