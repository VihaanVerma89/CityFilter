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
import android.support.v4.util.Pair;
import android.support.v4.widget.TextViewCompat;

import com.cityfilter.R;
import com.cityfilter.data.CitiesRemoteDataSource;
import com.cityfilter.data.CitiesRepository;
import com.cityfilter.data.Injection;
import com.cityfilter.network.ApiClient;
import com.cityfilter.network.models.City;
import com.cityfilter.ui.cityScreen.CitiesActivity;
import com.cityfilter.utils.CityUtil;

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
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
    public void testRandomCityIsShown(){
//        Random random = new Random();
//        int i = random.nextInt(mCities.size());
//        City city = mCities.get(i);
//        testCityIsShown(city,i);
        Pair<Integer, City> pair = getRandomCity();
        testCityIsShown(pair.first,pair.second);
//        onView(ViewMatchers.withId(R.id.citiesRecyclerView))
//                .perform(RecyclerViewActions.scrollToPosition(cityIndex));
//        onView(withText(city.getName())).check(matches(isDisplayed()));
    }

    private Pair<Integer, City> getRandomCity(){
        Random random = new Random();
        int i = random.nextInt(mCities.size());
        City city = mCities.get(i);
        return new Pair<>(i, city);
    }

    private void testCityIsShown(int position, City city)
    {
        onView(ViewMatchers.withId(R.id.citiesRecyclerView))
                .perform(RecyclerViewActions.scrollToPosition(position));
        onView(withText(city.getName())).check(matches(isDisplayed()));
    }

    @Test
    public void testAllCitesAreShown(){
        for(int i=0;i<mCities.size();i++)
        {
            testCityIsShown(i, mCities.get(i));
        }
    }

    @Test
    public void testToast(){
        Pair<Integer, City> pair= getRandomCity();
        String toastText = CityUtil.getCitySelectionText(pair.second);

        onView(ViewMatchers.withId(R.id.citiesRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(pair.first, click()));
        onView(withText(toastText))
                .inRoot(withDecorView(not(is(mCitiesActivityTestRule.getActivity().getWindow()
                        .getDecorView()))))
                .check(matches(isDisplayed()));
    }


}
