package com.alvindizon.androidiso8583.di.module;

import androidx.lifecycle.ViewModel;

import com.alvindizon.androidiso8583.core.ViewModelFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import dagger.MapKey;
import dagger.Module;
import dagger.Provides;

/*
    NB: ViewModels with default constructors (zero argument constructors) do not need ViewModelFactories
 */
@Module
public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }

    @Provides
    ViewModelFactory provideViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        return new ViewModelFactory(providerMap);
    }

//    @Provides
//    @IntoMap
//    @ViewModelKey(MainViewModel.class)
//    ViewModel provideMainViewModel(TcpRepository tcpRepository) {
//        return new MainViewModel(tcpRepository);
//    }


}