package com.example.weatherapp.network;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.injection.AppController;
import com.example.weatherapp.model.WeatherMapResponseModel;
import com.example.weatherapp.utils.ApiConstants;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    @Inject
    RetrofitAPIInterface retrofitAPIInterface;

    private static MutableLiveData<WeatherMapResponseModel> data = new MutableLiveData<>();

    public DataRepository() {

    }

    public MutableLiveData<WeatherMapResponseModel> getList(String city, String unit) {
        AppController.getAppComponent().inject(this);

       Call<WeatherMapResponseModel> call = retrofitAPIInterface.retrieveList(city , ApiConstants.API_KEY, ApiConstants.UNIT );
         call.enqueue(new Callback<WeatherMapResponseModel>() {

             @Override
             public void onResponse(Call<WeatherMapResponseModel> call, Response<WeatherMapResponseModel> response) {
                 data.setValue(response.body());
             }

             @Override
            public void onFailure(Call<WeatherMapResponseModel> call, Throwable t) {
           }
         });

        return data;
    }
}
