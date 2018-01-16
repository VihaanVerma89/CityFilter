package com.cityfilter.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by vihaan on 30/10/17.
 */

public class ActivityUtils {

    public static void showFragment(FragmentManager supportFragmentManager, int id, Fragment fragment)
    {
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.replace(id, fragment);
        ft.commit();
    }
}
