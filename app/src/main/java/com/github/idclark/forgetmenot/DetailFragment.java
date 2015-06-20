package com.github.idclark.forgetmenot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    public static String EXTRA_TITLE = "com.github.idclark.TITLE";
    public static String EXTRA_DUE = "com.github.idclark.DUE";
    public static String EXTRA_NOTES = "com.github.idclark.NOTES";
    public static String EXTRA_STATUS = "com.github.idclark.STATUS";

    FloatingActionButton mFabView;
    TextView mTitleView;
    TextView mNotesView;
    TextView mDueDate;
    CheckBox mCheckBox;


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
                mTitleView = (TextView) v.findViewById(R.id.task_title);
                String title = mTitleView.getText().toString();

                mNotesView = (TextView) v.findViewById(R.id.notes);
                String notes = mNotesView.getText().toString();

                mDueDate = (TextView) v.findViewById(R.id.task_due_date);
                String dueDate = mDueDate.getText().toString();

                mCheckBox = (CheckBox) v.findViewById(R.id.checkbox);
                Boolean status = mCheckBox.isChecked();

                Intent editTaskIntent = new Intent(v.getContext(), EditActivity.class);
                editTaskIntent.putExtra(EXTRA_TITLE, title);
                editTaskIntent.putExtra(EXTRA_DUE, dueDate);
                editTaskIntent.putExtra(EXTRA_NOTES, notes);
                editTaskIntent.putExtra(EXTRA_STATUS, status);
                startActivity(editTaskIntent);
            }
        });
        return rootView;
    }

}
