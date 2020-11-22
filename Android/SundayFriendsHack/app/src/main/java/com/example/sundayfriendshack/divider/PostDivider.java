package com.example.sundayfriendshack.divider;

import android.content.Context;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Adds spacing to each post. No spacing on last item.
 */
public class PostDivider extends RecyclerView.ItemDecoration {

    private int paddingBottomInDP;
    private final static int SPACING_IN_DP = 8;

    public PostDivider(Context context) {
        paddingBottomInDP = (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, SPACING_IN_DP,
                context.getResources().getDisplayMetrics() );
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = paddingBottomInDP;
        }
    }

}