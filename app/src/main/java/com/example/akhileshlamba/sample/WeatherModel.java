package com.example.akhileshlamba.sample;

import android.graphics.Bitmap;

/**
 * Created by akhileshlamba on 18/3/18.
 */

public class WeatherModel {

    private String temperature;
    private Bitmap image;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
