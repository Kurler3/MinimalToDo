package com.miguel.minimaltodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskLisViewHolder>
    implements OnDatabaseChanged{

    private static final String LOG_TAG = "TaskListAdapter";

    private DBHelper mDatabaseHelper;
    private Context mContext;
    LinearLayoutManager llm;

    public TaskListAdapter(Context context, LinearLayoutManager linearLayoutManager){
        mContext = context;
        llm = linearLayoutManager;
        mDatabaseHelper = new DBHelper(mContext);
        DBHelper.setOnDatabaseListener(this);
    }

    public static class TaskLisViewHolder extends RecyclerView.ViewHolder{
        ImageView mTaskImage;
        TextView mTitle;
        TextView mReminderDate;
        public TaskLisViewHolder(@NonNull View itemView) {
            super(itemView);
            mTaskImage = itemView.findViewById(R.id.task_image);
            mTitle = itemView.findViewById(R.id.task_title);
            mReminderDate = itemView.findViewById(R.id.task_reminder_date);
        }
    }

    public TaskLisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new TaskLisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskLisViewHolder holder, int position) {
        Task task = mDatabaseHelper.getItemAtPosition(position);

        holder.mTaskImage.setImageResource(task.getImage());
        holder.mTitle.setText(task.getTitle());
        holder.mReminderDate.setText(task.getTitle());

    }
    @Override
    public int getItemCount() {
        return mDatabaseHelper.getCount();
    }
    @Override
    public void OnTaskInserted() {
        notifyItemInserted(getItemCount() - 1);
        llm.scrollToPosition(getItemCount() - 1);
    }

    @Override
    public void OnTaskRenamed(int taskId) {
        //Update recycler view

    }

    @Override
    public void OnTaskReminderDateChanged(int taskId) {
         //Update recycler view
    }

    @Override
    public void OnTaskRemoved(int taskId) {
         //Remove from recycler view
    }

    @Override
    public void OnTaskHasReminderChanged(int taskId) {

    }
}
