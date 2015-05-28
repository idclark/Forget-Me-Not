package com.github.idclark.forgetmenot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    FloatingActionButton mFabView;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.task_detail, container, false);
        mFabView = (FloatingActionButton) rootView.findViewById(R.id.edit_button);
        mFabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editTaskIntent = new Intent(v.getContext(), EditActivity.class);
                //TODO collect fields and populate edit task
                startActivity(editTaskIntent);
            }
        });
        return rootView;
    }

}
