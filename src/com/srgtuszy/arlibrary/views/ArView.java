package com.srgtuszy.arlibrary.views;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;
import android.view.View;
import com.srgtuszy.arlibrary.math.Matrix;
import com.srgtuszy.arlibrary.model.ArItem;

import java.util.List;

public class ArView extends View {
    private List<ArItem> arItems;
    private Matrix mRotationMatrix;
    private Location mCurrentLocation;
    protected float cameraAngle = 180;

    public ArView(Context context) {
        super(context);
    }

    public void setArItems(List<ArItem> items) {
        arItems = items;
        invalidate();
    }

    public synchronized void setRotationMatrix(Matrix rotationMatrix) {
        mRotationMatrix = rotationMatrix;
        invalidate();
    }

    public synchronized void setCurrentLocation(Location location) {
        mCurrentLocation = location;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mRotationMatrix == null || mCurrentLocation == null || arItems == null) return;

        for (ArItem item : arItems) {
            item.update(mCurrentLocation);
            item.update(canvas, mRotationMatrix, cameraAngle);
            item.draw(canvas);
        }
    }

    public float getCameraAngle() {
        return cameraAngle;
    }

    public void setCameraAngle(float cameraAngle) {
        this.cameraAngle = cameraAngle;
    }
}
