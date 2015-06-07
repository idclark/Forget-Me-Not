package com.github.idclark.forgetmenot;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A placeholder fragment containing a simple view.
 */
public class EditFragment extends Fragment {

    EditText mTitleText;
    EditText mDueDate;
    EditText mTaskNotes;

    public EditFragment() {
    }

    public void OnCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return false;
            case R.id.action_save:
                insertNewTask((EditText)getView());
                return true;
            case R.id.action_delete:
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertNewTask(EditText view) {
        Task task = new Task();
        mTitleText = ((EditText) view.findViewById(R.id.task_title));
        mDueDate = ((EditText) view.findViewById(R.id.task_due_date));
        mTaskNotes = ((EditText) view.findViewById(R.id.task_notes));

        task.setTitle(mTitleText.getText().toString());

        //Holy Shit do the date dance.
        try {
            SimpleDateFormat sdf = new SimpleDateFormat();
            Date taskDueDate = sdf.parse(mDueDate.getText().toString());
            DateTime googleDate = new DateTime(taskDueDate);
            task.setDue(googleDate);
        } catch (ParseException ex) {
            Logger.getLogger("EDITFRAGMENT").log(Level.SEVERE, null, ex);
        }

        task.setNotes(mTaskNotes.getText().toString());

        boolean insertSuccess = new TaskTableController(getActivity()).insertRow(task);
        if(insertSuccess){
            Toast.makeText(getActivity(), "Task information was saved.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "Unable to save task information.", Toast.LENGTH_SHORT).show();
        }
    }

}
