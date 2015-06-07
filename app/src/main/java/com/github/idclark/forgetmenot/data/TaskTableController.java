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
        values.put("_id",task.getId());
        values.put("title",task.getTitle());
        values.put("due", task.getDue().toString());
        values.put("notes", task.getNotes());

        SQLiteDatabase db = this.getWritableDatabase();
        boolean createSuccessful = db.insert(TaskContract.TaskEntry.TABLE_NAME, null, values) > 0;
        db.close();
        return createSuccessful;
    }
}
