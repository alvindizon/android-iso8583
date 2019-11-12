package com.alvindizon.androidiso8583.application;

import android.app.Application;

import com.alvindizon.androidiso8583.di.component.AppComponent;
import com.alvindizon.androidiso8583.di.component.DaggerAppComponent;
import com.alvindizon.androidiso8583.di.component.ViewModelComponent;
import com.alvindizon.androidiso8583.di.module.AppModule;
import com.alvindizon.androidiso8583.di.module.ViewModelModule;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class CustomApplication extends Application {
    private static CustomApplication INSTANCE;

    AppComponent appComponent;
    ViewModelComponent viewModelComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        INSTANCE = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        viewModelComponent = appComponent.viewModelComponent(new ViewModelModule());
    }

    public static CustomApplication get() {
        return INSTANCE;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public ViewModelComponent getViewModelComponent() {
        return viewModelComponent;
    }
}
