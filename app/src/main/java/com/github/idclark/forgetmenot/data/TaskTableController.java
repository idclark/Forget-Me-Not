package com.github.idclark.forgetmenot.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.api.client.util.DateTime;
import com.google.api.services.tasks.model.Task;

import com.github.idclark.forgetmenot.data.TaskContract.TaskEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by idclark on 6/6/15.
 */
public class TaskTableController extends TaskDbHelper {

    public TaskTableController(Context context) {
        super(context);
    }

    /***
     *
     * @param task
     * @return
     */
    public boolean insertRow(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COLUMN_TASK_STATUS, task.getStatus());
        values.put(TaskContract.TaskEntry.COLUMN_TASK_ID,task.getId());
        values.put(TaskContract.TaskEntry.COLUMN_TASK_TITLE, task.getTitle());
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DUE, task.getDue().toString());
        values.put(TaskContract.TaskEntry.COLUMN_TASK_NOTES, task.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public List<Task> getAllTasksForUser() {
        final String QUERY_ALL_RECORDS = "SELECT * FROM " + TaskEntry.TABLE_NAME;

        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_ALL_RECORDS, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Task task = new Task();
                int TaskId = cursor.getColumnIndex(TaskEntry.COLUMN_TASK_ID);
                task.setId(cursor.getString(TaskId));
                task.setTitle(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_TITLE)));
                task.setDue(new DateTime(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DUE))));
                task.setNotes(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NOTES)));
                task.setStatus(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_STATUS)));
                taskList.add(task);
            }
        } else {
            cursor.close();
            db.close();
            return taskList;
        }
        cursor.close();
        db.close();
        return taskList;
    }

    public Task getTaskByID(String Id) {
        final String SINGLE_TASK_QUERY = "SELECT * FROM " + TaskEntry.TABLE_NAME +
                " WHERE " + TaskEntry.COLUMN_TASK_ID +  "=" +"'"+ Id+"'"+";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SINGLE_TASK_QUERY, null);

        Task task = new Task();
        while (cursor.moveToNext()){

            task.setId(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_ID)));
            task.setTitle(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_TITLE)));
            task.setStatus(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_STATUS)));
            task.setDue(new DateTime(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_DUE))));
            task.setNotes(cursor.getString(cursor.getColumnIndex(TaskEntry.COLUMN_TASK_NOTES)));
        }
        cursor.close();
        return task;
    }
}
