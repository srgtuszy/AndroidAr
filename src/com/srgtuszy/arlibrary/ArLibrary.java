package com.srgtuszy.arlibrary;

import android.content.Context;
import android.location.Location;
import com.srgtuszy.arlibrary.controllers.LocationController;
import com.srgtuszy.arlibrary.controllers.LocationListener;
import com.srgtuszy.arlibrary.controllers.SensorController;
import com.srgtuszy.arlibrary.controllers.SensorControllerListener;
import com.srgtuszy.arlibrary.math.Matrix;

public class ArLibrary implements LocationListener, SensorControllerListener {
    private LocationController mLocationController;
    private SensorController mSensorController;
    private ArLibraryListener mListener;

    public ArLibrary(Context context) {
        mLocationController = new LocationController(context, this);
        mSensorController = new SensorController(context);
        mSensorController.setListener(this);
    }

    public void setListener(ArLibraryListener listener) {
        mListener = listener;
    }

    public void startAr() {
        mLocationController.startLocationUpdates();
        mSensorController.startSensors();
    }

    public void stopAr() {
        mLocationController.stopLocationUpdates();
        mSensorController.stopSensors();
    }

    @Override
    public void onLocationChanged(Location newLocation) {
        mSensorController.setCurrentLocation(newLocation);
        if (mListener != null) mListener.onLocationChanged(newLocation);
    }

    @Override
    public void onRotationChanged(Matrix rotationMatrix) {
        if (mListener != null) mListener.onRotationChanged(rotationMatrix);
    }
}
