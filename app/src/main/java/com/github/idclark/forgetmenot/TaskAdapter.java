package com.github.idclark.forgetmenot;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.github.idclark.forgetmenot.data.TaskTableController;
import com.google.api.services.tasks.model.Task;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by idclark on 5/13/15.
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>
                         implements ItemTouchHelperAdapter {

    private List<Task> taskList;
    OnItemClickListener mItemClickListener;
    private Context mContext;

    public TaskAdapter(List<Task> taskList, Context context) {
        this.taskList = taskList;
        this.mContext = context;
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder taskViewHolder, int i) {
        DateTimeUtils utils = new DateTimeUtils(mContext);
        final Task task = taskList.get(i);
        taskViewHolder.m_ID.setText(task.getId());
        taskViewHolder.mTitle.setText(task.getTitle());
        taskViewHolder.mDue.setText((utils.formatDueDate(task.getDue().toString())));
        if (task.getStatus().equals("completed")) {
            taskViewHolder.mStatus.setChecked(true);
        } else taskViewHolder.mStatus.setChecked(false);
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.tasklist_layout, viewGroup, false);

        return new TaskViewHolder(itemView);
    }

    @Override
    public void onItemDismiss(final int position, RecyclerView recyclerView) {
        final Task task = taskList.get(position);
        String id = task.getId();
        boolean deleteSuccess = new TaskTableController(mContext).deletTaskByID(id);
        if (deleteSuccess) {
            taskList.remove(position);
            Snackbar.make(recyclerView, R.string.db_delete_success, Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Boolean status = new TaskTableController(mContext).insertNewRow(task);
                            taskList.add(task);
                            notifyItemInserted(position);
                        }
                    }).show();
            notifyItemRemoved(position);
        } else {
            Toast.makeText(mContext, R.string.db_delete_fail, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemMove(int from, int to) {
        Collections.swap(taskList, from, to);
        notifyItemMoved(from, to);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.title) protected TextView mTitle;
        @Bind(R.id.due) protected TextView mDue;
        @Bind(R.id.status) protected CheckBox mStatus;
        @Bind(R.id._task_id) protected TextView m_ID;

        public TaskViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

    }
}
