package com.miguel.minimaltodo;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTask extends AppCompatActivity {
    public static final int ADD_TASK_REQUEST_CODE = 2;
    public static final String DATE_PICKER = "DatePicker";
    TextInputLayout mDateInputLayout, mHourIntervalInputLayout;
    EditText mTitleInput, mDateInput, mHourInterval;
    AutoCompleteTextView mCategoryChoice;
    SwitchMaterial mSwitch;
    Button mCreateTaskBtn;
    public static final SimpleDateFormat dateFormater = new SimpleDateFormat("EEEE dd, MMMM, yyyy");
    ImageButton mCancelTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task_layout);

        InitializeViews();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line
        ,category_list);

        mCategoryChoice.setAdapter(arrayAdapter);

        //Creating DatePicker
        MaterialDatePicker.Builder datePickerBuilder = MaterialDatePicker.Builder.datePicker();
        datePickerBuilder.setTitleText("Select Date");
        MaterialDatePicker datePicker = datePickerBuilder.build();

        datePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Long>) selection -> {
            if(CheckDate(selection)){
                mDateInput.setText(dateFormater.format(selection));
            }else{
                //Show a snackbar
            }
        });

        //Buttons
        mCreateTaskBtn.setOnClickListener(view -> {
            if(CheckDataInputted()){
                //return a new task with the values inputted

            }
        });
        mCancelTask.setOnClickListener(view -> {
            //destroy the activity
        });

        // Responding to clicking on end icons of input texts
        mDateInputLayout.setEndIconOnClickListener(view -> {
            //Launches a DatePicker
            datePicker.show(getSupportFragmentManager(), DATE_PICKER);
        });

        mHourIntervalInputLayout.setEndIconOnClickListener(view -> {
            //Launches a TimePicker
        });

    }
    private void InitializeViews(){
        mTitleInput = findViewById(R.id.new_task_title_input);
        mDateInput = findViewById(R.id.new_reminder_date_input);
        mHourInterval = findViewById(R.id.new_hour_interval_input);
        mCategoryChoice = findViewById(R.id.new_category_input);
        mSwitch = findViewById(R.id.has_reminder_switch);
        mCreateTaskBtn = findViewById(R.id.create_task_btn);
        mCancelTask = findViewById(R.id.cancel_createTask_Btn);
        mDateInputLayout = findViewById(R.id.new_reminder_date_input_layout);
        mHourIntervalInputLayout = findViewById(R.id.new_hour_interval_input_layout);

        //Date originally should be the same day
        Calendar c = Calendar.getInstance();
        mDateInput.setText(dateFormater.format(c.getTime()));
        mHourInterval.setText("08:00");


    }
    private boolean CheckDataInputted(){
        if(mTitleInput.getText().toString().isEmpty()) return false;
        return true;
    }
    private boolean CheckDate(Long selection){
        Date dateChosen = new Date(selection);
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        c.setTime(dateChosen);
        if(currentYear > c.get(Calendar.YEAR)) return false;
        return true;
    }

    public static final String[] category_list = new String[]{"Food", "Life", "Workout",
        "Study", "Groceries", "Others"};

}
