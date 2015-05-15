package com.github.idclark.forgetmenot;

/**
 * Created by idclark on 5/13/15.
 */
public class Task {

    protected String _id;
    protected String title;
    //make this datetime
    protected String updated;
    protected String selfLink;
    protected String parent;
    protected String position;
    protected String notes;
    protected String status;
    protected String due;
    //make this datetime
    protected String completed;
    protected Boolean deleted;
    protected Boolean hidden;

    protected static final String TITLE_PREFIX = "title_";
    protected static final String UPDATED_PREFIX = "modified at_";
    protected static final String NOTES_PREFIX = "these are notes that wiil be displayed __";

}
