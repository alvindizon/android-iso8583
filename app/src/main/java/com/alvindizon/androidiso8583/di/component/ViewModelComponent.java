package com.alvindizon.androidiso8583.di.component;


import com.alvindizon.androidiso8583.application.MainActivity;
import com.alvindizon.androidiso8583.di.module.ViewModelModule;

import dagger.Subcomponent;

@Subcomponent(modules = ViewModelModule.class)
public interface ViewModelComponent {
    void inject(MainActivity activity);
}
