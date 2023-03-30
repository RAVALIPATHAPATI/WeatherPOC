package com.example.weatherapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.utils.PermissionUtils;
import com.example.weatherapp.viewmodel.MainViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    MainViewModel mViewModel;
    ActivityMainBinding mBinding;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    String lastSearchCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        lastSearchCity = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("Last Search", "");

        if(lastSearchCity != null) {
            callApi(mViewModel, lastSearchCity);
            observeViewModel(mViewModel);
            viewVisible();
        } else {
            viewInvisible();
        }
        mBinding.searchView.setActivated(true);
        mBinding.searchView.setQueryHint("Search with city name");
        mBinding.searchView.onActionViewExpanded();
        mBinding.searchView.setIconified(false);
        mBinding.searchView.clearFocus();
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lastSearchCity = query;
                viewVisible();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("Last Search", lastSearchCity).apply();
                callApi(mViewModel, query);
                observeViewModel(mViewModel);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewInvisible();
                return false;
            }
        });

        getLocation();

    }

    public void viewVisible(){
        mBinding.temp.setVisibility(View.VISIBLE);
        mBinding.maxTemp.setVisibility(View.VISIBLE);
        mBinding.minTemp.setVisibility(View.VISIBLE);
        mBinding.img.setVisibility(View.VISIBLE);
        mBinding.desc.setVisibility(View.VISIBLE);
        mBinding.cityName.setVisibility(View.VISIBLE);
        mBinding.tempTile.setVisibility(View.VISIBLE);
        mBinding.minTempTile.setVisibility(View.VISIBLE);
        mBinding.maxTempTile.setVisibility(View.VISIBLE);
    }

    public void viewInvisible(){
        mBinding.temp.setVisibility(View.INVISIBLE);
        mBinding.maxTemp.setVisibility(View.INVISIBLE);
        mBinding.minTemp.setVisibility(View.INVISIBLE);
        mBinding.img.setVisibility(View.INVISIBLE);
        mBinding.desc.setVisibility(View.INVISIBLE);
        mBinding.cityName.setVisibility(View.INVISIBLE);
        mBinding.tempTile.setVisibility(View.INVISIBLE);
        mBinding.minTempTile.setVisibility(View.INVISIBLE);
        mBinding.maxTempTile.setVisibility(View.INVISIBLE);
    }

    private void callApi(MainViewModel mViewModel, String query) {
        // fetch Data to be displayed
        mViewModel.fetchList(query,"");
    }
    private void observeViewModel(final MainViewModel mViewModel) {
        // Update when the data changes
        mViewModel.getListObservable().observe(this, data -> {
            if (data != null) {
                mBinding.setData(data);
            }
        });
    }

    public void getLocation()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (PermissionUtils.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
        } else {
            PermissionUtils.requestPermissions(this, MY_PERMISSIONS_REQUEST_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(this, "Location Permission denied: Enable in settings", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}