package com.github.idclark.forgetmenot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.services.tasks.model.Task;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    public static String EXTRA_TITLE = "com.github.idclark.TITLE";
    public static String EXTRA_DUE = "com.github.idclark.DUE";
    public static String EXTRA_NOTES = "com.github.idclark.NOTES";
    public static String EXTRA_STATUS = "com.github.idclark.STATUS";
    public static String EXTRA_ID = "com.github.idclark.ID";

    FloatingActionButton mFabView;
    TextView mTitleView;
    TextView mNotesView;
    TextView mDueDate;
    CheckBox mCheckBox;
    Toolbar toolbar;

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DateTimeUtils utils = new DateTimeUtils(getActivity().getApplicationContext());

        TaskTableController controller = new TaskTableController(getActivity());
        final String taskID = getArguments().getString("TASK_ID");
        Task detailTask = controller.getTaskByID(taskID);
        detailTask.getId();
        detailTask.getTitle();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        mTitleView = (TextView) rootView.findViewById(R.id.title);
        mTitleView.setText(detailTask.getTitle());
        toolbar = (Toolbar) getActivity().findViewById(R.id.tool_bar);
        toolbar.setTitle(detailTask.getTitle());
        mNotesView = (TextView) rootView.findViewById(R.id.notes);
        mNotesView.setText(detailTask.getNotes());
        mDueDate = (TextView) rootView.findViewById(R.id.date_string);
        mDueDate.setText(utils.formatDueDate(detailTask.getDue().toString()));
        mCheckBox = (CheckBox) rootView.findViewById(R.id.status);
        detailTask.getStatus();
        if (detailTask.getStatus().equals(getString(R.string.task_status_complete))) {
            mCheckBox.setChecked(true);
        } else
        mCheckBox.setChecked(false);

        mFabView = (FloatingActionButton) rootView.findViewById(R.id.edit_button);
        mFabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mTitleView.getText().toString();
                String notes = mNotesView.getText().toString();
                String dueDate = mDueDate.getText().toString();
                Boolean status = mCheckBox.isChecked();

                Intent editTaskIntent = new Intent(v.getContext(), EditActivity.class);
                editTaskIntent.putExtra(EXTRA_TITLE, title);
                editTaskIntent.putExtra(EXTRA_DUE, dueDate);
                editTaskIntent.putExtra(EXTRA_NOTES, notes);
                editTaskIntent.putExtra(EXTRA_STATUS, status);
                editTaskIntent.putExtra(EXTRA_ID, taskID);
                startActivity(editTaskIntent);
            }
        });
        return rootView;
    }
}
