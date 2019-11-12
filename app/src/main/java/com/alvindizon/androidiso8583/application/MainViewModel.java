package com.alvindizon.androidiso8583.application;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    @Inject
    public MainViewModel() {
        this.compositeDisposable = new CompositeDisposable();
    }
}
