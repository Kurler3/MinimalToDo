package com.miguel.minimaltodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        //To-Do
        public TaskLisViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public TaskLisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create Resource File
         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item,parent,false);
        return new TaskLisViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskLisViewHolder holder, int position) {
       //To-DO
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
    public void OnTaskRenamed() {
        //Update Database and recycler view
    }

    @Override
    public void OnTaskReminderDateChanged() {
         //Update Database and recycler view
    }

    @Override
    public void OnTaskRemoved() {
         //Remove from database, recycler view
    }
}
