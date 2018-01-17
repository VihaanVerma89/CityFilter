package com.cityfilter.ui.cityScreen;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.cityfilter.data.CitiesRepository;
import com.cityfilter.network.models.City;
import com.cityfilter.utils.EspressoIdlingResource;
import com.cityfilter.utils.schedulers.BaseSchedulerProvider;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by vihaanverma on 16/01/18.
 */

public class CitiesPresenter implements CitiesContract.Presenter {

    @NonNull
    private CitiesContract.View mView;
    @NonNull
    private CitiesRepository mRepository;
    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeDisposable mCompositeDisposable;

    public CitiesPresenter(@NonNull CitiesRepository repository, @NonNull CitiesContract.View
            view, @NonNull BaseSchedulerProvider schedulerProvider) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
        mSchedulerProvider = schedulerProvider;
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void subscribe() {
        loadCities();
    }


    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void loadCities() {
        EspressoIdlingResource.increment();
        mCompositeDisposable.clear();
        Disposable disposable = mRepository
                .getCities()
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .doFinally(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                })
                .subscribe(cities -> {
                            processCities(cities);
                        },
                        error -> {
                            processError(error);
                        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void refreshCities() {
        mRepository.setCacheDirty();
        loadCities();
    }

    @Override
    public Single<List<City>> loadCities(String text) {
//        EspressoIdlingResource.increment();
        return mRepository
                .getCities(text)
                .doFinally(() -> {
                    if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                        EspressoIdlingResource.decrement(); // Set app as idle.
                    }
                });
    }

    private void processCities(List<City> cities) {
        mView.hideProgressView();
        mView.hideSwipeRefreshView();
        mView.showCities(cities);
    }

    private void processError(Throwable error) {
        if (error instanceof HttpException) {
            HttpException exception = (HttpException) error;
            mView.showToast(exception.getMessage(), Toast.LENGTH_SHORT);
        } else if (error instanceof UnknownHostException) {
            mView.showToast("Unable to reach network..", Toast.LENGTH_SHORT);
        }
    }
}
