package com.alvindizon.androidiso8583.di.component;


import com.alvindizon.androidiso8583.di.module.AppModule;
import com.alvindizon.androidiso8583.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {
        AppModule.class})
@Singleton
public interface AppComponent  {
    ViewModelComponent viewModelComponent(ViewModelModule viewModelModule);
}