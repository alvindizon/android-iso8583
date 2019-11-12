package com.alvindizon.androidiso8583.di.module;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Context provideApplicationContext() {
        return application.getApplicationContext();
    }
}