package com.github.idclark.forgetmenot;

import android.content.Context;
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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        final Task task = taskList.get(i);
        taskViewHolder.m_ID.setText(task.getId());
        taskViewHolder.mTitle.setText(task.getTitle());
        taskViewHolder.mDue.setText(task.getDue().toString());
        if (task.getStatus().equals("complete")) {
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
    public void onItemDismiss(int position) {
        Task task = taskList.get(position);
        String id = task.getId();
        boolean deleteSuccess = new TaskTableController(mContext).deletTaskByID(id);
        if (deleteSuccess) {
            taskList.remove(position);
            Toast.makeText(mContext, R.string.bd_save_correct, Toast.LENGTH_SHORT).show();
            notifyItemRemoved(position);
        } else {
            Toast.makeText(mContext, R.string.db_save_error, Toast.LENGTH_SHORT).show();
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

        public TaskViewHolder(View view) {
            super(view);
            m_ID = (TextView) view.findViewById(R.id._task_id);
            mTitle = (TextView) view.findViewById(R.id.title);
            mDue = (TextView) view.findViewById(R.id.due);
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
