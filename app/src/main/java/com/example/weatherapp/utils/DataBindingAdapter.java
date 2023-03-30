package com.example.weatherapp.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DataBindingAdapter {
    static final String url = "https://openweathermap.org/img/wn/";
    @BindingAdapter("android:imgSrc")
    public static void setImageUri(ImageView view, String id) {
        // Avoid loading previously failed image

            Glide.with(view.getContext())
                    .load(url + id + ".png")
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            view.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            view.setVisibility(View.VISIBLE);
                            return false;
                        }
                    })
                    .into(view);
        }
    }
