package com.example.weatherapp.network;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.model.WeatherMapResponseModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DataRepositoryTest {

    @Mock
    RetrofitAPIInterface webService;

    @InjectMocks
    DataRepository dataRepository;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule =
            new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dataRepository = Mockito.mock(DataRepository.class);
    }

    @Test
    public void getList() {

        MutableLiveData<WeatherMapResponseModel> data = new MutableLiveData<>();

        WeatherMapResponseModel dataModel = Mockito.spy(WeatherMapResponseModel.class);
        data.setValue(dataModel);

        //Setting how up the mock behaves
        Mockito.doReturn(data).when(dataRepository).getList("London","metric");

        webService.retrieveList("London","16ba2c20e0063cc1ed0de7ba84a1f62c","metric");
        Mockito.verify(webService).retrieveList("London","16ba2c20e0063cc1ed0de7ba84a1f62c","metric");

        Assert.assertEquals(data, dataRepository.getList("London","metric"));
    }
}