package com.example.weatherapp.injection;

import com.example.weatherapp.network.DataRepository;
import com.example.weatherapp.viewmodel.MainViewModel;

import javax.inject.Singleton;
import dagger.Component;
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(DataRepository retrofitClient);
    void inject(MainViewModel mainViewModel);

}
