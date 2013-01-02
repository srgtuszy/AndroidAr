package com.srgtuszy.arlibrary;

import android.content.Context;
import android.location.Location;
import com.srgtuszy.arlibrary.controllers.LocationController;
import com.srgtuszy.arlibrary.controllers.LocationListener;
import com.srgtuszy.arlibrary.controllers.SensorController;

public class ArLibrary {
    private static LocationController locationController;
    private static SensorController sensorController;

    public static void initialize(Context context) {

        locationController = new LocationController(context, new LocationListener() {

            @Override
            public void onLocationChanged(Location newLocation) {

            }
        });
    }
}
