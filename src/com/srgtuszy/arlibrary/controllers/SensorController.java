package com.srgtuszy.arlibrary.controllers;

import android.content.Context;
import android.hardware.*;
import android.location.Location;
import com.srgtuszy.arlibrary.math.LowPassFilter;
import com.srgtuszy.arlibrary.math.Matrix;

public class SensorController implements SensorEventListener {
    private static final int SENSOR_DELAY = SensorManager.SENSOR_DELAY_NORMAL;
    private static final float GRAVITY_HIGH = 1.0f;
    private static final float GRAVITY_LOW = 0.5f;
    private static final float MAGNETIC_HIGH = 4.0f;
    private static final float MAGNETIC_LOW = 2.0f;
    private static final float ROTATION_ANGLE = -90.0f;

    private Location currentLocation;
    private SensorManager sensorManager;
    private GeomagneticField geomagneticField;

    private float temp[] = new float[9];
    private float rotation[] = new float[9];
    private float gravity[] = new float[9];
    private float magnetic[] = new float[9];

    private Matrix cameraMatrix = new Matrix();
    private Matrix magneticMatrix = new Matrix();
    private Matrix rotationMatrix = new Matrix();
    private Matrix arMatrix = new Matrix();

    public SensorController(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        float sinX = (float) Math.sin(ROTATION_ANGLE);
        float cosX = (float) Math.cos(ROTATION_ANGLE);

        rotationMatrix.set(1.0f, 0, 0, 0, cosX, -sinX, 0, sinX, cosX);
    }

    public void startSensors() {
        Sensor accelerometer = sensorManager.getDefaultSensor(SensorManager.SENSOR_ACCELEROMETER);
        Sensor magnetometer = sensorManager.getDefaultSensor(SensorManager.SENSOR_MAGNETIC_FIELD);

        if (accelerometer != null) sensorManager.registerListener(this, accelerometer, SENSOR_DELAY);
        if (magnetometer != null) sensorManager.registerListener(this, magnetometer, SENSOR_DELAY);
    }

    public void stopSensors() {
        sensorManager.unregisterListener(this);
    }

    public void setCurrentLocation(Location location) {
        if (location == null) return;

        currentLocation = location;
        float lat = (float) currentLocation.getLatitude();
        float lon = (float) currentLocation.getLongitude();
        float alt = (float) currentLocation.getAltitude();
        long time = System.currentTimeMillis();

        synchronized (magneticMatrix) {
            geomagneticField = new GeomagneticField(lat, lon, alt, time);
            double declination = -Math.toRadians(geomagneticField.getDeclination());
            float sinY = (float) Math.sin(declination);
            float cosY = (float) Math.cos(declination);

            magneticMatrix.toIdentity();
            magneticMatrix.set(cosY, 0, sinY, 0, 1.0f, 0, -sinY, 0, cosY);
            magneticMatrix.prod(rotationMatrix);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int status) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        float[] values = event.values;
        float[] lowPassFilter = new float[3];

        if (type == Sensor.TYPE_ACCELEROMETER) {
            gravity = LowPassFilter.filter(GRAVITY_LOW, GRAVITY_HIGH, values, gravity).clone();

        } else if (type == Sensor.TYPE_MAGNETIC_FIELD) {
            magnetic = LowPassFilter.filter(MAGNETIC_LOW, MAGNETIC_HIGH, values, magnetic).clone();
        }

        if (gravity != null && magnetic != null) {
            SensorManager.getRotationMatrix(temp, null, gravity, magnetic);
            SensorManager.remapCoordinateSystem(temp, SensorManager.AXIS_X, SensorManager.AXIS_MINUS_X, rotation);
            cameraMatrix.get(lowPassFilter);
            arMatrix.toIdentity();

            synchronized (magneticMatrix) {
                arMatrix.prod(magneticMatrix);
            }

            arMatrix.prod(cameraMatrix);
        }
    }
}
