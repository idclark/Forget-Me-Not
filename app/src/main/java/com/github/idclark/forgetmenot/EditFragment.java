package com.github.idclark.forgetmenot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A placeholder fragment containing a simple view.
 */
public class EditFragment extends Fragment implements View.OnClickListener {

    CheckBox mCheckBox;
    EditText mTitleText;
    EditText mDueDate;
    EditText mTaskNotes;
    String taskID;
    TextView mDeleteText;

    public EditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        mDeleteText = (TextView) view.findViewById(R.id.delete_task);
        mDeleteText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        String id = getTaskID();
        new TaskTableController(getActivity()).deletTaskByID(id);
        Snackbar.make(view, R.string.db_delete_success, Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Task task = new Task();
                        if (getTaskID() != null) {
                            task.setId(getTaskID());
                        } else {
                            task.setId("TaskId" + Math.random());
                        }
                        task.setStatus(getTaskStatus());
                        task.setTitle(getTitleText());
                        task.setDue(getTaskDueDate());
                        task.setNotes(getTaskNotes());
                        new TaskTableController(getActivity()).insertNewRow(task);
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }
                })
                //.setCallback
                .show();
                //startActivity(new Intent(getActivity(), MainActivity.class));
    }

    public void setTaskID(String id) {
        taskID = id;
    }
    public void setTaskStatus(Boolean status) {
        mCheckBox = (CheckBox) getActivity().findViewById(R.id.edit_status);
        if (status) {
            mCheckBox.setChecked(true);
        }
    }

    public void setTaskTitle(String title) {
        mTitleText = (EditText) getActivity().findViewById(R.id.task_title);
        mTitleText.setText(title);
    }

    public void setmTaskNotes(String notes) {
        mTaskNotes = (EditText) getActivity().findViewById(R.id.task_notes);
        mTaskNotes.setText(notes);
    }

    public void setmDueDate(String dueDate) {
        DateTimeUtils utils = new DateTimeUtils(getActivity().getApplicationContext());
        mDueDate = (EditText) getActivity().findViewById(R.id.task_due_date);
        mDueDate.setText(utils.formatDueDate(dueDate));
    }

    public String getTaskID() {
        return taskID;
    }

    public String getTaskStatus() {
        mCheckBox = (CheckBox) getActivity().findViewById(R.id.edit_status);
        if (mCheckBox.isChecked()) {
            return getString(R.string.task_status_complete);
        } else return getString(R.string.task_status_not_complete);

    }

    public String getTitleText() {
        mTitleText = (EditText) getActivity().findViewById(R.id.task_title);
        return mTitleText.getText().toString();
    }

    public String getTaskNotes() {
        mTaskNotes = (EditText) getActivity().findViewById(R.id.task_notes);
        return mTaskNotes.getText().toString();
    }

    public DateTime getTaskDueDate() {
        mDueDate = (EditText) getActivity().findViewById(R.id.task_due_date);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy, h:mm a", Locale.US);
        Date parseDate;
        try {
        parseDate = sdf.parse(mDueDate.getText().toString());
    } catch (ParseException e) {
            Log.getStackTraceString(e);
            return null;
        }
        return new DateTime(parseDate);
    }
}
