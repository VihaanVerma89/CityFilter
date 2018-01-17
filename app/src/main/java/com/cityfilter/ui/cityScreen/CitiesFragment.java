package com.cityfilter.ui.cityScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cityfilter.R;
import com.cityfilter.network.models.City;
import com.cityfilter.utils.CityUtil;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

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
        initToolbar();
        initSwipeRefreshLayout();
        initProgressBar();
        initRecyclerView();
        initSearchView();
    }

    private ProgressBar mProgressBar;

    private void initProgressBar() {
        mProgressBar = getView().findViewById(R.id.progressBar);
    }

    @Override
    public void hideProgressView() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideSwipeRefreshView() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(toolbar);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        final ActionBar ab = appCompatActivity.getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private RecyclerView mRecyclerView;
    private CitiesAdapter mCitiesAdapter;

    private void initRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.citiesRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

    private void initSwipeRefreshLayout() {
        // Set up progress indicator
        mSwipeRefreshLayout =
                getView().findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        mSwipeRefreshLayout.setScrollUpChild(mRecyclerView);

        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.refreshCities());
    }

    private void initSearchView(){
         android.support.v7.widget.SearchView searchView= getView().findViewById(R.id.searchView);
        RxSearchView.queryTextChanges(searchView)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString())
                .filter(text -> !text.isEmpty())
                .distinctUntilChanged()
                .switchMap(text -> mPresenter.loadCities(text).toObservable())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cities ->{
                    showCities(cities);
                });
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
    public void showCities(List<City> cities) {
        mCities = cities;
        mCitiesAdapter = new CitiesAdapter(getActivity(), this, mCities);
        mRecyclerView.setAdapter(mCitiesAdapter);
    }

    @Override
    public void showToast(String text, int type) {
        Toast.makeText(getActivity(), text, type).show();
    }

    @Override
    public void onCityClicked(int position) {
        String toast = CityUtil.getCitySelectionText(mCities.get(position));
        Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
    }

}
