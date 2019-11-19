package br.com.developer.allefsousa.adorofilmes;

import android.app.Application;

import com.amplitude.api.Amplitude;


/**
 * @author allef.santos on 2019-09-06
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Amplitude.getInstance().initialize(this, "7998be8d0de28476d91f784b6917997e").enableForegroundTracking(this);
    }
}
