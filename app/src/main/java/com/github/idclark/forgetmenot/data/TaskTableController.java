package com.github.idclark.forgetmenot.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.api.services.tasks.model.Task;

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
        values.put(TaskContract.TaskEntry.COLUMN_TASK_TITLE,task.getTitle());
        values.put(TaskContract.TaskEntry.COLUMN_TASK_DUE, task.getDue().toString());
        values.put(TaskContract.TaskEntry.COLUMN_TASK_NOTES, task.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values) > 0;
        db.close();
        return createSuccessful;
    }
}
