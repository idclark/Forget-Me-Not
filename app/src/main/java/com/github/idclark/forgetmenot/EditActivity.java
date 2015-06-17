package com.github.idclark.forgetmenot;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.services.tasks.model.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends ActionBarActivity implements DatePickerDialog.OnDateSetListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        EditFragment editFragment = (EditFragment) getSupportFragmentManager()
                .findFragmentById(R.id.edit_fragment);
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
               insertNewTask(editFragment);
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

    private void insertNewTask(EditFragment editFragment) {
        Task task = new Task();
        //TODO this is horribles and only for test
        task.setId("TaskId" + Math.random());
        task.setStatus(editFragment.getTaskStatus());
        task.setTitle(editFragment.getTitleText());
        task.setDue(editFragment.getTaskDueDate());
        task.setNotes(editFragment.getTaskNotes());

        boolean insertSuccess = new TaskTableController(this).insertRow(task);
        if(insertSuccess){
            Toast.makeText(this, "Task information was saved.", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Unable to save task information.", Toast.LENGTH_SHORT).show();
        }
    }

}
