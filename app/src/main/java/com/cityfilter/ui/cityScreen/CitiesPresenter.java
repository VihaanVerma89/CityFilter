package com.cityfilter.ui.cityScreen;

import android.support.annotation.NonNull;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesPresenter implements CitiesContract.Presenter{

    @NonNull
    private CitiesContract.View mView;

    public CitiesPresenter(@NonNull CitiesContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
