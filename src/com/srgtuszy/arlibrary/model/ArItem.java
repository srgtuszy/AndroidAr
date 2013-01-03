package com.srgtuszy.arlibrary.model;

import android.graphics.Canvas;
import android.location.Location;
import com.srgtuszy.arlibrary.math.Matrix;

public interface ArItem {
    public void draw(Canvas canvas);

    public void update(Location location);

    public void update(Canvas canvas, Matrix rotationMatrix);

    public void setY(float y);

    public float getY();

    public void setX(float x);

    public float getX();

    public void setItemLocation(Location location);

    public Location getItemLocation();

    public void setTitle(String title);

    public String getTitle();
}
