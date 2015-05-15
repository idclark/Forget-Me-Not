package com.github.idclark.forgetmenot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by idclark on 5/13/15.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public void onBindViewHolder(TaskViewHolder taskViewHolder, int i) {
        Task task = taskList.get(i);
        taskViewHolder.mTitle.setText(task.title);
        taskViewHolder.mUpdated.setText(task.updated);
        taskViewHolder.mStatus.setChecked(false);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.taskcard_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        protected TextView mTitle;
        protected TextView mUpdated;
        protected CheckBox mStatus;

        public TaskViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.title);
            mUpdated = (TextView) view.findViewById(R.id.updated);
            mStatus = (CheckBox) view.findViewById(R.id.status);
        }
    }
}
