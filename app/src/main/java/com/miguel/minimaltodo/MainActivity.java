package com.miguel.minimaltodo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    RecyclerView mTaskList;
    FloatingActionButton mAddTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitializeViews();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mTaskList.setLayoutManager(layoutManager);

        TaskListAdapter adapter = new TaskListAdapter(getApplicationContext(), layoutManager);

        mTaskList.setAdapter(adapter);


        mAddTaskBtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity.this, AddTask.class);
            startActivityForResult(i, AddTask.ADD_TASK_REQUEST_CODE);
        });
    }
    public void InitializeViews(){
        mTaskList = findViewById(R.id.task_list);
        mAddTaskBtn = findViewById(R.id.add_task_Btn);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If a new task was created then add the task to the database. This will update the recycler view due to the listener.
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings_actionbar_item:
                //open settings activity
                break;
            case R.id.about_actionbar_item:
                //open about activity
                break;
        }
        return true;
    }
}