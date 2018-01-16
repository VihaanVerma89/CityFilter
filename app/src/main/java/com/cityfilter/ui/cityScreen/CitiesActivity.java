package com.cityfilter.ui.cityScreen;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cityfilter.R;
import com.cityfilter.utils.ActivityUtils;

public class CitiesActivity extends AppCompatActivity {

    private CitiesPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        initFragment();
        initPresenter();
    }

    private void initPresenter(){
        mPresenter = new CitiesPresenter(mCitiesFragment);
    }

    private CitiesFragment mCitiesFragment;
    private void initFragment(){
        mCitiesFragment= (CitiesFragment) getSupportFragmentManager().findFragmentById(R.id
                .frameLayout);
        if(mCitiesFragment==null)
        {
            ActivityUtils.showFragment(getSupportFragmentManager(), R.id.frameLayout, CitiesFragment
                    .newInstance());
        }
    }

}
