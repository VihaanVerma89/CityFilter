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
import android.widget.Toast;

import com.cityfilter.R;
import com.cityfilter.network.models.City;
import com.cityfilter.network.models.CityData;

import java.util.List;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesFragment extends Fragment
        implements CitiesContract.View,
        CitiesAdapter.CitiesListener

{

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

    private void initViews() {
        initProgressBar();
        initRecyclerView();
    }

    private ProgressBar mProgressBar;

    private void initProgressBar() {
        mProgressBar = getView().findViewById(R.id.progressBar);
    }

    @Override
    public void hideProgressView() {
        mProgressBar.setVisibility(View.GONE);
    }


    private RecyclerView mRecyclerView;
    private CitiesAdapter mCitiesAdapter;

    private void initRecyclerView() {
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

    private List<City> mCities;

    @Override
    public void showCities(CityData cityData) {
        if (isAdded()) {
            mCities = cityData.getCities();
            mCitiesAdapter = new CitiesAdapter(getActivity(), this, mCities);
            mRecyclerView.setAdapter(mCitiesAdapter);
        }
    }

    @Override
    public void showCitiesError(Throwable error) {
        if (isAdded()) {

        }
    }

    @Override
    public void onCityClicked(int position) {
        String cityName = mCities.get(position).getName();
        String toast = new StringBuilder("Selection is - ").append(cityName).toString();
        Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
    }
}
