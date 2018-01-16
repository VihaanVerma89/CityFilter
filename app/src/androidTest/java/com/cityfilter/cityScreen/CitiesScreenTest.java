package com.cityfilter.cityScreen;

import android.media.MediaCodecInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.remote.InteractionResponse;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cityfilter.R;
import com.cityfilter.data.CitiesRemoteDataSource;
import com.cityfilter.data.CitiesRepository;
import com.cityfilter.data.Injection;
import com.cityfilter.network.ApiClient;
import com.cityfilter.network.models.City;
import com.cityfilter.ui.cityScreen.CitiesActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;

/**
 * Created by vihaanverma on 16/01/18.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CitiesScreenTest {

    private List<City> mCities;
    @Rule
    public ActivityTestRule<CitiesActivity> mCitiesActivityTestRule=
            new ActivityTestRule<CitiesActivity>(CitiesActivity.class){

                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    Injection.provideCitiesRepository(InstrumentationRegistry.getTargetContext()).deleteAllCities();

                    CitiesRemoteDataSource remoteDataSource = CitiesRemoteDataSource.getInstance();
                    mCities = remoteDataSource.getCities().blockingGet();
                }
            };

    @Before
    public void setUp() throws Exception {
        IdlingRegistry.getInstance().register(
                mCitiesActivityTestRule.getActivity().getCountingIdlingResource());
    }

    @After
    public void tearDown() throws Exception {
        IdlingRegistry.getInstance().unregister(
                mCitiesActivityTestRule.getActivity().getCountingIdlingResource());
        CitiesRepository.destroyInstance();
    }

    @Test
    public void testRandomCity(){
        Random random = new Random();
        int cityIndex = random.nextInt(mCities.size());
        City city = mCities.get(cityIndex);
        onView(ViewMatchers.withId(R.id.citiesRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(cityIndex));
        onView(withText(city.getName())).check(matches(isDisplayed()));
    }

    @Test
    public void testAllCity(){
        for(int i=0;i<mCities.size();i++)
        {
            City city = mCities.get(i);
            onView(ViewMatchers.withId(R.id.citiesRecyclerView))
                    .perform(RecyclerViewActions.scrollToPosition(i));
            onView(withText(city.getName())).check(matches(isDisplayed()));
        }
    }


}
