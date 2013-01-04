package com.srgtuszy.arlibrary.activities;

import android.app.Activity;
import android.hardware.Camera;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.srgtuszy.arlibrary.ArLibrary;
import com.srgtuszy.arlibrary.ArLibraryListener;
import com.srgtuszy.arlibrary.math.Matrix;
import com.srgtuszy.arlibrary.views.ArView;
import com.srgtuszy.arlibrary.views.CameraView;

public abstract class ArActivity extends Activity implements ArLibraryListener {

    protected ArView arView;
    protected CameraView cameraView;
    protected FrameLayout frameLayout;
    protected ArLibrary arLibrary;
    protected Camera mCamera;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        arLibrary.stopAr();
        mCamera.stopPreview();
    }

    @Override
    protected void onResume() {
        super.onResume();
        arLibrary.startAr();
        mCamera.startPreview();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frameLayout = new FrameLayout(this);
        setContentView(frameLayout);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT);

        mCamera = Camera.open();

        arView = new ArView(this);
        arView.setLayoutParams(params);

        cameraView = new CameraView(this, mCamera);
        cameraView.setLayoutParams(params);

        frameLayout.addView(cameraView);
        frameLayout.addView(arView);

        arLibrary = new ArLibrary(getApplicationContext());
        arLibrary.setListener(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        arView.setCurrentLocation(location);
    }

    @Override
    public void onRotationChanged(Matrix rotationMatrix) {
        arView.setRotationMatrix(rotationMatrix);
    }
}
