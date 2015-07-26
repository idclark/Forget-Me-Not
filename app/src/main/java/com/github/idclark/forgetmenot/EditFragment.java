package com.github.idclark.forgetmenot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.api.client.util.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A placeholder fragment containing a simple view.
 */
public class EditFragment extends Fragment {

    CheckBox mCheckBox;
    EditText mTitleText;
    EditText mDueDate;
    EditText mTaskNotes;
    String taskID;

    public EditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
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
        mDueDate = (EditText) getActivity().findViewById(R.id.task_due_date);
        //TODO do some date formatting here
        mDueDate.setText(dueDate);
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
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
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
