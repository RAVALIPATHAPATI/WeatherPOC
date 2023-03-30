package com.example.weatherapp.viewmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.weatherapp.model.WeatherMapResponseModel;
import com.example.weatherapp.network.DataRepository;
import com.example.weatherapp.network.RetrofitAPIInterface;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {

    @InjectMocks
    MainViewModel viewModel;

    @Mock
    DataRepository repository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    MockWebServer mockWebServer;
    Retrofit retrofit;

    RetrofitAPIInterface service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockWebServer = new MockWebServer();
        retrofit = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RetrofitAPIInterface.class);

        viewModel = org.mockito.Mockito.mock(MainViewModel.class);
    }

    @Test
    public void testApiSuccess() throws IOException {

        mockWebServer.enqueue(new MockResponse().setBody("{\n" +
                        "\"coord\":{\"lon\":-0.1257,\"lat\":51.5085},\"weather\":[{\"id\":804,\"main\":\"Clouds\"," +
                        "\"description\":\"overcastclouds\",\"icon\":\"04n\"}],\"base\":\"stations\"," +
                        "\"main\":{\"temp\":282.28,\"feels_like\":280.56,\"temp_min\":282.28," +
                        "\"temp_max\":282.28,\"pressure\":1011,\"humidity\":96,\"sea_level\":1011,\"grnd_level\":1008}," +
                        "\"visibility\":10000,\"wind\":{\"speed\":3.1,\"deg\":206,\"gust\":8.68}," +
                        "\"clouds\":{\"all\":100},\"dt\":1680068456,\"sys\":{\"country\":\"GB\",\"sunrise\":1680068609," +
                        "\"sunset\":1680114423},\"timezone\":3600,\"id\":2643743,\"name\":\"London\",\"cod\":200}"
                ));

        Call<WeatherMapResponseModel> call = service.retrieveList("London", "16ba2c20e0063cc1ed0de7ba84a1f62c", "metric");
        Response<WeatherMapResponseModel> dataModel = call.execute();

        assertNotNull(dataModel);
        assertEquals("overcastclouds", dataModel.body().getWeather().get(0).getDescription());
        assertEquals("London", dataModel.body().getName());
    }

    @Test
    public void testApiFailure() throws IOException {

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(""));

        Call<WeatherMapResponseModel> call = service.retrieveList("", "", "");
        Response<WeatherMapResponseModel> dataModel = call.execute();

        assertNull(dataModel.body());
    }

    @After
    public void tearDown() {
        //Finish web server
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
