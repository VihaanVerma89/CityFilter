package com.cityfilter.ui.cityScreen;

import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cityfilter.R;
import com.cityfilter.data.CitiesRepository;
import com.cityfilter.data.Injection;
import com.cityfilter.utils.ActivityUtils;
import com.cityfilter.utils.EspressoIdlingResource;
import com.cityfilter.utils.schedulers.BaseSchedulerProvider;

public class CitiesActivity extends AppCompatActivity {

    private CitiesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        initFragment();
        initPresenter();
    }

    private void initPresenter() {
        CitiesRepository repository = Injection.provideCitiesRepository(getApplicationContext());
        BaseSchedulerProvider schedulerProvider = Injection.provideSchedulerProvider();
        mPresenter = new CitiesPresenter(repository, mCitiesFragment, schedulerProvider);
    }

    private CitiesFragment mCitiesFragment;

    private void initFragment() {
        mCitiesFragment = (CitiesFragment) getSupportFragmentManager().findFragmentById(R.id
                .frameLayout);
        if (mCitiesFragment == null) {
            mCitiesFragment = CitiesFragment.newInstance();
            ActivityUtils.showFragment(getSupportFragmentManager(), R.id.frameLayout,
                    mCitiesFragment);
        }
    }

    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
