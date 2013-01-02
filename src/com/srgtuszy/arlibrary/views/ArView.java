package com.srgtuszy.arlibrary.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.srgtuszy.arlibrary.model.ArItem;

import java.util.List;

public class ArView extends View {
    private List<ArItem> arItems;

    public ArView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setArItems(List<ArItem> items) {
        arItems = items;
    }

    @Override
    public void onDraw(Canvas canvas) {

    }
}
