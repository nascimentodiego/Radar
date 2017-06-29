package br.com.dfn.radar.view.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.dfn.radar.R;
import br.com.dfn.radar.view.base.fragment.BaseFragment;

public class ProfileFragment extends BaseFragment {
    private View root;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profiler, container, false);
//        initView();

//        mPresenter.checkAccount();

        return root;
    }
}
