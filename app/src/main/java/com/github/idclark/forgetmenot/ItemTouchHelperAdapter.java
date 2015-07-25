package com.github.idclark.forgetmenot;

/**
 * Created by idclark on 7/25/15.
 */
public interface ItemTouchHelperAdapter {

    void onItemDismiss(int position);
    void onItemMove(int fromPosition, int toPosition);
}
