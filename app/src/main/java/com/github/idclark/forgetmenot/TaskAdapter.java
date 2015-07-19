package com.github.idclark.forgetmenot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.services.tasks.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by idclark on 5/13/15.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    OnItemClickListener mItemClickListener;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder taskViewHolder, int i) {
        final Task task = taskList.get(i);
        taskViewHolder.m_ID.setText(task.getId());
        taskViewHolder.mTitle.setText(task.getTitle());
        taskViewHolder.mDue.setText(task.getDue().toString());
        if (task.getStatus().equals("completed")) {
            taskViewHolder.mStatus.setChecked(true);
        } else taskViewHolder.mStatus.setChecked(false);

        taskViewHolder.mDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleteSuccess = new TaskTableController(v.getContext()).deletTaskByID(task.getId());

                if (deleteSuccess) {
                    Toast.makeText(v.getContext(), "Task was successfully deleted", Toast.LENGTH_SHORT).show();
                    int index = taskList.indexOf(task);
                    notifyItemRemoved(index + 1);

                } else {
                    Toast.makeText(v.getContext(), "Unable to delete Task", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tasklist_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    private String formatDueDate(String queryResponse) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US);
        Date result = new Date();
        try {
            result = sdf.parse(queryResponse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView mTitle;
        protected TextView mDue;
        protected CheckBox mStatus;
        protected TextView m_ID;
        protected TextView mDeleteIcon;

        public TaskViewHolder(View view) {
            super(view);
            m_ID = (TextView) view.findViewById(R.id._task_id);
            mTitle = (TextView) view.findViewById(R.id.title);
            mDue = (TextView) view.findViewById(R.id.due);
            mStatus = (CheckBox) view.findViewById(R.id.status);
            mDeleteIcon = (TextView) view.findViewById(R.id.delete_icon);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }
}
