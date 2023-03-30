package com.example.weatherapp.injection;

import android.app.Application;

public class AppController extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildAppComponent();
    }
    public static AppComponent getAppComponent() {
        return appComponent;
    }
    public static AppComponent buildAppComponent() {
        return DaggerAppComponent.builder().appModule(new AppModule()).build();
    }
}
