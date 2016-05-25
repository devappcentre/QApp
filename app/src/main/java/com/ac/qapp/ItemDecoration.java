package com.ac.qapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by raghav on 7/5/16.
 */
public class ItemDecoration extends RecyclerView.ItemDecoration {

    private int margin;

    public ItemDecoration(int space) {
        this.margin = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {


        outRect.set(margin, margin, margin, margin);


    }
}