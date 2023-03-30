package com.example.weatherapp.injection;

import com.example.weatherapp.network.RetrofitAPIInterface;
import com.example.weatherapp.network.DataRepository;
import com.example.weatherapp.utils.ApiConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {
    @Provides
    @Singleton
    public RetrofitAPIInterface webService(Retrofit retrofit) {
        return retrofit.create(RetrofitAPIInterface.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofit(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.WEATHER_MAP_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    @Singleton
    public GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    public DataRepository repository() {
        return new DataRepository();
    }
}
