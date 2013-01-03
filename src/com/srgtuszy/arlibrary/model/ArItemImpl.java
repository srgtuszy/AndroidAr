package com.srgtuszy.arlibrary.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import com.srgtuszy.arlibrary.math.CameraModel;
import com.srgtuszy.arlibrary.math.Matrix;
import com.srgtuszy.arlibrary.math.PhysicalLocationUtility;
import com.srgtuszy.arlibrary.math.Vector;

public class ArItemImpl implements ArItem {

    private Vector mSymbolVector = new Vector(0, 0, 0);
    private Vector mRelativePosition = new Vector();
    private Vector mMarkerPosition = new Vector();
    private Vector mTmpMarkerPosition = new Vector();
    private Vector mTmpVector = new Vector();
    private float[] mDistanceResults = new float[3];
    private Location mItemLocation;
    private String mTitle;
    private float mX;
    private float mY;
    private float mDistance;
    private Paint mPaint = new Paint();

    public ArItemImpl() {
        mPaint.setTextSize(15.0f);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(mTitle, 0, mTitle.length() - 1, mX, mY, mPaint);
    }

    @Override
    public void update(Location location) {
        PhysicalLocationUtility.convLocationToVector(location, mItemLocation, mRelativePosition);
        mY = mRelativePosition.getY();
        double itemLat = mItemLocation.getLatitude();
        double itemLon = mItemLocation.getLongitude();
        double curLat = location.getLatitude();
        double curLon = location.getLongitude();

        Location.distanceBetween(curLat, curLon, itemLat, itemLon, mDistanceResults);
        mDistance = mDistanceResults[0];
    }

    @Override
    public void update(Canvas canvas, Matrix rotationMatrix) {
        mTmpMarkerPosition.set(mSymbolVector);
        mTmpMarkerPosition.add(mRelativePosition);
        mTmpMarkerPosition.prod(rotationMatrix);

        CameraModel.projectPoint(mTmpMarkerPosition, mTmpVector, mDistance, canvas);
        mMarkerPosition.set(mTmpVector);

        mX = mMarkerPosition.getX();
        mY = mMarkerPosition.getY();
    }

    @Override
    public void setY(float y) {
        mY = y;
    }

    @Override
    public float getY() {
        return mY;
    }

    @Override
    public void setX(float x) {
        mX = x;
    }

    @Override
    public float getX() {
        return mX;
    }

    @Override
    public void setItemLocation(Location location) {
        mItemLocation = location;
    }

    @Override
    public Location getItemLocation() {
        return mItemLocation;
    }

    @Override
    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String toString() {
        return String.format("%s, x=%f, y=%f", mTitle, mX, mY);
    }
}
