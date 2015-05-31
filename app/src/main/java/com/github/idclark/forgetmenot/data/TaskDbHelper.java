package com.github.idclark.forgetmenot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.github.idclark.forgetmenot.data.TaskContract.TaskEntry;
/**
 * Created by idclark on 5/30/15.
 */
public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tasks.db";
    private static final int DB_Version = 1;

    public TaskDbHelper(Context context) {
        super(context, DB_NAME,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TASK_TABLE =
                "CREATE TABLE " + TaskEntry.TABLE_NAME + " (" +
                        TaskEntry._ID + " INTEGER PRIMARY KEY," +
                        TaskEntry.COLUMN_TASK_ID + "TEXT NOT NULL, " +
                        TaskEntry.COLUMN_TASK_TITLE + "TEXT NOT NULL, " +
                        TaskEntry.COLUMN_TASK_COMPLETED + "TEXT, " +
                        TaskEntry.COLUMN_TASK_NOTES + "TEXT, " +
                        TaskEntry.COLUMN_TASK_STATUS + "TEXT NOT NULL, " +
                        TaskEntry.COLUMN_TASK_DUE + "TEXT, " +
                        TaskEntry.COLUMN_TASK_UPDATED + "TEXT, " +
                        TaskEntry.COLUMN_TASK_PARENT + "TEXT, " +
                        TaskEntry.COLUMN_TASK_DELETED + "TEXT, " +
                        TaskEntry.COLUMN_TASK_SELFLINK + "TEXT, " +
                        TaskEntry.COLUMN_TASK_POSITION + "TEXT, " +
                        TaskEntry.COLUMN_TASK_HIDDEN + "TEXT" + ")";

        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
