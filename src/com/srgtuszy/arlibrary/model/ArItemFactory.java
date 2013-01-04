package com.srgtuszy.arlibrary.model;

import android.location.Location;

public class ArItemFactory {

    public static ArItem fromTitle(String title, Location location) {
        ArItemImpl item = new ArItemImpl();
        item.setItemLocation(location);
        item.setTitle(title);

        return item;
    }
}
