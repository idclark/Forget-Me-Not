package com.github.idclark.forgetmenot;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.services.tasks.model.Task;

public class EditActivity extends AppCompatActivity {

    public static String EXTRA_TITLE = "com.github.idclark.TITLE";
    public static String EXTRA_DUE = "com.github.idclark.DUE";
    public static String EXTRA_NOTES = "com.github.idclark.NOTES";
    public static String EXTRA_STATUS = "com.github.idclark.STATUS";
    public static String EXTRA_ID = "com.github.idclark.ID";

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        EditFragment editFragment = (EditFragment) getSupportFragmentManager()
                .findFragmentById(R.id.edit_fragment);
        Bundle data = getIntent().getExtras();
        if (data != null) {
            editFragment.setTaskID(data.getString(EXTRA_ID));
            editFragment.setTaskStatus(data.getBoolean(EXTRA_STATUS));
            editFragment.setTaskTitle(data.getString(EXTRA_TITLE));
            editFragment.setmTaskNotes(data.getString(EXTRA_NOTES));
            editFragment.setmDueDate(data.getString(EXTRA_DUE));
        }

        EditText mEditDueDate = (EditText) findViewById(R.id.task_due_date);
        mEditDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDateAndTimePicker(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch (item.getItemId()) {
           case R.id.action_settings:
               return false;
           case R.id.action_save:
               EditFragment editFragment = (EditFragment)getSupportFragmentManager()
                       .findFragmentById(R.id.edit_fragment);
               createOrUpdateTask(editFragment);
               startActivity(new Intent(this, MainActivity.class));
               return true;
           case R.id.action_delete:
               startActivity(new Intent(this, MainActivity.class));
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    public void launchDateAndTimePicker(View view) {
        DialogUtils utils = new DialogUtils(this);
        utils.showDateAndTimeDialog(this);
    }

    /**
     *
     * This is used in two contexts,
     * 1. If we create a new Task from the cardview, we go to an empty editFragment
     * 2. If we edit an existing task from the DetailFragment, the editFragment will be populated
     * with the existing task fields.
     * @param editFragment
     */
    //TODO, this method does too much, break it up for seperate methods to write new rows
    //and update existing rows
    private void createOrUpdateTask(EditFragment editFragment) {
        Task task = new Task();
        if (editFragment.getTaskID() != null) {
            task.setId(editFragment.getTaskID());
            task.setStatus(editFragment.getTaskStatus());
            task.setTitle(editFragment.getTitleText());
            task.setDue(editFragment.getTaskDueDate());
            task.setNotes(editFragment.getTaskNotes());
            boolean updateSuccess = new TaskTableController(this).updateExistingTask(task);
            if (updateSuccess) {
                //Toast.makeText(this, getString(R.string.bd_save_correct), Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.edit_fragment), getString(R.string.db_save_correct), Snackbar.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, getString(R.string.db_save_error), Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.edit_fragment), getString(R.string.db_save_error), Snackbar.LENGTH_LONG).show();
            }

    } else {
            //TODO this is horribles and only for test
            task.setId("TaskId" + Math.random());
            task.setStatus(editFragment.getTaskStatus());
            task.setTitle(editFragment.getTitleText());
            //TODO this breaks with an existing datetime string, mm/dd/yyyy HH:mm:sss z
            task.setDue(editFragment.getTaskDueDate());
            task.setNotes(editFragment.getTaskNotes());

            boolean insertSuccess = new TaskTableController(this).insertNewRow(task);
            if (insertSuccess) {
                //Toast.makeText(this, getString(R.string.bd_save_correct), Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.edit_fragment), getString(R.string.db_save_correct), Snackbar.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, getString(R.string.db_save_error), Toast.LENGTH_SHORT).show();
                Snackbar.make(findViewById(R.id.edit_fragment), getString(R.string.db_save_error), Snackbar.LENGTH_LONG);
            }
        }
    }
}
