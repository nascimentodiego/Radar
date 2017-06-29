package br.com.dfn.radar.view.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;

import br.com.dfn.radar.App;
import br.com.dfn.radar.R;
import br.com.dfn.radar.model.User;
import br.com.dfn.radar.presenter.home.ProfileContracts;
import br.com.dfn.radar.presenter.home.ProfilePresenter;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

public class ProfileFragment extends BaseFragment implements ProfileContracts.View, View.OnClickListener {
    private View root;
    private ImageView img_profile;
    private TextView tx_profile;
    private ProfileContracts.Presenter mPresenter;

    public ProfileFragment() {
        mPresenter = new ProfilePresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profiler, container, false);
        img_profile = (ImageView) root.findViewById(R.id.ic_profile);
        img_profile.setOnClickListener(this);


        tx_profile = (TextView) root.findViewById(R.id.tx_name);

        mPresenter.showUser();

        return root;
    }

    @Override
    public void showUser(User user) {
        tx_profile.setText(user.getName());
        Picasso.with(App.getContext())
                .load(user.getPictureUrl())
                .placeholder(R.drawable.profile_active)
                .into(img_profile);
    }

    @Override
    public void disconnect() {
        if (AccessToken.getCurrentAccessToken() == null) {
            callLoginActivity();
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                LoginManager.getInstance().logOut();
                callLoginActivity();
            }
        }).executeAsync();
    }

    @Override
    public void onClick(View v) {
        Animation pulse = AnimationUtils.loadAnimation(App.getContext(), R.anim.pulse);
        img_profile.startAnimation(pulse);
        disconnect();
    }

    private void callLoginActivity() {
        Intent it = new Intent(getActivity(), MainActivity.class);
        getActivity().startActivity(it);
        getActivity().finish();
    }
}
