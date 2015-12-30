package com.github.idclark.forgetmenot;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by idclark on 12/29/15.
 */
public class cardView extends FrameLayout {

    public @Bind(R.id._task_id) TextView m_ID;
    public @Bind(R.id.title)  TextView mTitle;

    public cardView(Context context) {
        super(context);
        initialize(context);
    }

    public cardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_id_cardview, this);
        ButterKnife.bind(this);
    }

}
