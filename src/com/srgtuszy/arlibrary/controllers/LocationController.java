package com.srgtuszy.arlibrary.controllers;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationController implements LocationListener, android.location.LocationListener {
    private LocationManager locationManager;
    private LocationListener listener;
    private Location currentLocation;

    public LocationController(Context context, LocationListener listener) {
        this.listener = listener;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void startLocationUpdates() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void stopLocationUpdates() {
        locationManager.removeUpdates(this);
    }

    @Override
    public void onProviderDisabled(String providerName) {
    }

    @Override
    public void onProviderEnabled(String providerName) {

    }

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {

    }

    @Override
    public void onLocationChanged(Location newLocation) {
        currentLocation = newLocation;
        if (listener != null) listener.onLocationChanged(currentLocation);
    }
}
