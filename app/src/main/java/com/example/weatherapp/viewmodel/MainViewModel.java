package com.example.weatherapp.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.injection.AppController;
import com.example.weatherapp.model.WeatherMapResponseModel;
import com.example.weatherapp.network.DataRepository;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    public MutableLiveData<WeatherMapResponseModel> weatherMapResponseModelMutableLiveData = new MutableLiveData<>();
    @Inject
    DataRepository repository;

    public MainViewModel() {
        super();
    }

    public void fetchList(String city, String unit) {
        AppController.getAppComponent().inject(this);
        weatherMapResponseModelMutableLiveData = repository.getList(city, unit);
    }

    public LiveData<WeatherMapResponseModel> getListObservable() {
        return weatherMapResponseModelMutableLiveData;
    }
}