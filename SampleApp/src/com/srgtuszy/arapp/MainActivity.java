package com.srgtuszy.arapp;

import android.location.Location;
import android.os.Bundle;
import com.srgtuszy.arlibrary.activities.ArActivity;
import com.srgtuszy.arlibrary.model.ArItem;
import com.srgtuszy.arlibrary.model.ArItemFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ArActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Location location = new Location("Fake provider");
        location.setLatitude(52.240362665091524);
        location.setLongitude(21.006717681884766);
        location.setAltitude(100);

        ArItem item = ArItemFactory.fromTitle("Centrum", location);

        List<ArItem> items = new ArrayList<ArItem>();
        items.add(item);
        arView.setArItems(items);
    }
}
