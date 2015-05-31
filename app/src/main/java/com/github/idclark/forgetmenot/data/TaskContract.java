package com.github.idclark.forgetmenot.data;

import android.provider.BaseColumns;

/**
 * Created by idclark on 5/30/15.
 */
public class TaskContract {

    public TaskContract(){

    }

    public static final class TaskEntry implements BaseColumns {

        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_TASK_ID = "_id";
        public static final String COLUMN_TASK_TITLE = "title";
        public static final String COLUMN_TASK_UPDATED = "updated";
        public static final String COLUMN_TASK_SELFLINK = "selfLink";
        public static final String COLUMN_TASK_PARENT = "parent";
        public static final String COLUMN_TASK_POSITION = "position";
        public static final String COLUMN_TASK_NOTES = "notes";
        public static final String COLUMN_TASK_STATUS = "status";
        public static final String COLUMN_TASK_DUE = "due";
        public static final String COLUMN_TASK_COMPLETED = "completed";
        public static final String COLUMN_TASK_DELETED = "deleted";
        public static final String COLUMN_TASK_HIDDEN = "hidden";
    }
}
