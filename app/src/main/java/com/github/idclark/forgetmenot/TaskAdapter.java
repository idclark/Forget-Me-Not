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
    OnItemClickListener mItemClickListener;

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
                inflate(R.layout.tasklist_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected TextView mTitle;
        protected TextView mUpdated;
        protected CheckBox mStatus;

        public TaskViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.title);
            mUpdated = (TextView) view.findViewById(R.id.updated);
            mStatus = (CheckBox) view.findViewById(R.id.status);
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
