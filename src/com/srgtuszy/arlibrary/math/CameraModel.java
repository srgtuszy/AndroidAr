package com.srgtuszy.arlibrary.math;

import android.graphics.Canvas;

public class CameraModel {
    private static final float[] tmp1 = new float[3];
    private static final float[] tmp2 = new float[3];

    public static void projectPoint(Vector orgPoint, Vector prjPoint, float angle, Canvas canvas) {
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        float dist = (width / 2) / (float) Math.tan(angle / 2);
        orgPoint.get(tmp1);
        tmp2[0] = (dist * tmp1[0] / -tmp1[2]);
        tmp2[1] = (dist * tmp1[1] / -tmp1[2]);
        tmp2[2] = (tmp1[2]);
        tmp2[0] = (tmp2[0] + width / 2);
        tmp2[1] = (-tmp2[1] + height / 2);
        prjPoint.set(tmp2);
    }
}