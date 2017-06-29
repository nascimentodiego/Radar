package br.com.dfn.radar.view.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import br.com.dfn.radar.view.home.MainActivity;
import br.com.dfn.radar.R;
import br.com.dfn.radar.presenter.login.LoginContracts;
import br.com.dfn.radar.presenter.login.LoginPresenter;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

/**
 * The type Login fragment.
 */
public class LoginFragment extends BaseFragment implements LoginContracts.View, View.OnClickListener, FacebookCallback<LoginResult> {

    private View root;
    private Button btn_login;

    private LoginContracts.Presenter mPresenter;
    private OnLoginFragmentListener mOnLoginFragmentListener;

    CallbackManager callbackManager;

    LoginButton loginButton;

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
        initView(root);

       /* if (AccessToken.getCurrentAccessToken() != null) {
            callMainActivity();
        }*/

        return root;
    }

    private void initView(View root) {
        btn_login = (Button) root.findViewById(R.id.bt_login);
        btn_login.setOnClickListener(this);

        loginButton = (LoginButton) root.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));

        loginButton.setFragment(this);

        // Callback registration
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, this);
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

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());

                        // Application code
                        try {
                            String email = object.getString("email");
                            String name = object.getString("name"); // 01/31/1980 format
                            JSONObject data = response.getJSONObject();
                            if (data.has("picture")) {
                                String profilePicUrl = data.getJSONObject("picture").getJSONObject("data").getString("url");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
