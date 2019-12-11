package br.com.developer.allefsousa.adorofilmes;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.amplitude.api.Amplitude;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mixpanel.android.mpmetrics.MixpanelAPI;


/**
 * @author allef.santos on 2019-09-06
 */
public class AppApplication extends MultiDexApplication {
    public static FirebaseAnalytics mFirebaseAnalytics;



    @Override
    public void onCreate() {
        super.onCreate();
        Amplitude.getInstance().initialize(this, "7998be8d0de28476d91f784b6917997e").enableForegroundTracking(this);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }


}
