package com.srgtuszy.arlibrary;

import android.location.Location;
import com.srgtuszy.arlibrary.math.Matrix;

public interface ArLibraryListener {
    public void onLocationChanged(Location location);

    public void onRotationChanged(Matrix rotationMatrix);
}
