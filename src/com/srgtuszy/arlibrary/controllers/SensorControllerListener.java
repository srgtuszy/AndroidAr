package com.srgtuszy.arlibrary.controllers;

import com.srgtuszy.arlibrary.math.Matrix;

public interface SensorControllerListener {
    public void onRotationChanged(Matrix rotationMatrix);
}
