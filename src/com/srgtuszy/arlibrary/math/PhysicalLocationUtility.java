package com.srgtuszy.arlibrary.math;

import android.location.Location;

/**
 * This class is used to represent a physical locations which have a latitude,
 * longitude, and alitude.
 *
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class PhysicalLocationUtility {

    private static float[] x = new float[1];
    private static double y = 0.0d;
    private static float[] z = new float[1];


    public static synchronized void convLocationToVector(Location org, Location gp, Vector v) {
        if (org == null || gp == null || v == null)
            throw new NullPointerException("Location, PhysicalLocationUtility, and Vector cannot be NULL.");

        Location.distanceBetween(org.getLatitude(), org.getLongitude(),
                gp.getLatitude(), org.getLongitude(),
                z);

        Location.distanceBetween(org.getLatitude(), org.getLongitude(),
                org.getLatitude(), gp.getLongitude(),
                x);
        y = gp.getAltitude() - org.getAltitude();
        if (org.getLatitude() < gp.getLatitude())
            z[0] *= -1;
        if (org.getLongitude() > gp.getLongitude())
            x[0] *= -1;

        v.set(x[0], (float) y, z[0]);
    }
}