package com.example.weatherapp.network;

import com.example.weatherapp.model.WeatherMapResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPIInterface {

    @GET("weather")
    Call<WeatherMapResponseModel> retrieveList(@Query("q") String city,
                                               @Query("appid") String apiKey, @Query("units") String unit);
}
