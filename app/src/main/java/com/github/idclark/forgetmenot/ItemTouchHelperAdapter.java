package com.github.idclark.forgetmenot;

import android.support.v7.widget.RecyclerView;

/**
 * Created by idclark on 7/25/15.
 */
public interface ItemTouchHelperAdapter {

    void onItemDismiss(int position, RecyclerView recyclerView);
    void onItemMove(int fromPosition, int toPosition);
}
