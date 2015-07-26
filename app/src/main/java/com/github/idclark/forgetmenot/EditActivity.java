package com.github.idclark.forgetmenot;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.services.tasks.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener
                                              , TimePickerDialog.OnTimeSetListener{

    public static String EXTRA_TITLE = "com.github.idclark.TITLE";
    public static String EXTRA_DUE = "com.github.idclark.DUE";
    public static String EXTRA_NOTES = "com.github.idclark.NOTES";
    public static String EXTRA_STATUS = "com.github.idclark.STATUS";
    public static String EXTRA_ID = "com.github.idclark.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
               return true;
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        //do some stuff for example write on log and update TextField on activity
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        ((EditText) findViewById(R.id.task_due_date)).setText(sdf.format(calendar.getTime()));
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        String format = "HH:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(hour, minute);
        String date = ((EditText) findViewById(R.id.task_due_date)).getText().toString();
        ((EditText) findViewById(R.id.task_due_date)).setText(date + " " + sdf.format(calendar.getTime()));
    }

    public void showTimePickerDialog(View v) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    /**
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
                Toast.makeText(this, getString(R.string.bd_save_correct), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.db_save_error), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, getString(R.string.bd_save_correct), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.db_save_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
