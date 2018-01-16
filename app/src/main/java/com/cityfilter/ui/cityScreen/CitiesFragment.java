package com.cityfilter.ui.cityScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.cityfilter.R;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesFragment extends Fragment implements CitiesContract.View {

    public static CitiesFragment newInstance() {
        Bundle args = new Bundle();
        CitiesFragment fragment = new CitiesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_cities, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){
        initProgressBar();
        initRecyclerView();
    }

    private ProgressBar mProgressBar;
    private void initProgressBar(){
        mProgressBar = getView().findViewById(R.id.progressBar);
    }

    private void hideProgressView(){
        mProgressBar.setVisibility(View.GONE);
    }


    private RecyclerView mRecyclerView;
    private void initRecyclerView(){
        mRecyclerView = getView().findViewById(R.id.citiesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private CitiesContract.Presenter mPresenter;
    @Override
    public void setPresenter(CitiesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
