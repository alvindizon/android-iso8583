package com.alvindizon.androidiso8583.di;


import com.alvindizon.androidiso8583.application.CustomApplication;
import com.alvindizon.androidiso8583.di.component.AppComponent;
import com.alvindizon.androidiso8583.di.component.ViewModelComponent;

public class Injector {
    public static AppComponent get() {
        return CustomApplication.get().getAppComponent();
    }

    public static ViewModelComponent getViewModelComponent() {
        return CustomApplication.get().getViewModelComponent();
    }
}
