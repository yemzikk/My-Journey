package com.ziyadkuttiady.coviddiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddVisitActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextStartDate, editTextEndDate, editTextStartTime, editTextEndTime, editTextStartPlace, editTextEndPlace, editTextPurpose, editTextDesc, editTextVehicleNumber, editTextVehicleCategory;
    Button buttonAdd;
    DataBaseHelper myDbHelper;
    LinearLayoutCompat parentLayout;
    RadioButton vehicle_typeRadioButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.add_visit);
        setSupportActionBar(toolbar);

        myDbHelper = new DataBaseHelper(this);

        editTextStartDate = findViewById(R.id.editTextStartingPointDate);
        editTextEndDate = findViewById(R.id.editTextDestinationDate);
        editTextStartTime = findViewById(R.id.editTextStartingPointTime);
        editTextEndTime = findViewById(R.id.editTextDestinationTime);
        editTextStartPlace = findViewById(R.id.editTextStartingPointPlace);
        editTextEndPlace = findViewById(R.id.editTextDestinationPlace);
        editTextPurpose = findViewById(R.id.editTextPurpose);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextVehicleNumber = findViewById(R.id.editTextVehicleNo);
        editTextVehicleCategory = findViewById(R.id.editTextVehicleCategory);

        parentLayout = findViewById(R.id.parent);

        editTextStartDate.setOnClickListener(AddVisitActivity.this);
        editTextEndDate.setOnClickListener(AddVisitActivity.this);
        editTextStartTime.setOnClickListener(AddVisitActivity.this);
        editTextEndTime.setOnClickListener(AddVisitActivity.this);

        radioGroup = findViewById(R.id.radioButtonGroup);

        buttonAdd = findViewById(R.id.buttonAddHistory);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                vehicle_typeRadioButton = findViewById(selectedId);
                String start_date = editTextStartDate.getText().toString();
                String end_date = editTextEndDate.getText().toString();
                String start_time = editTextStartTime.getText().toString();
                String end_time = editTextEndTime.getText().toString();
                String start_place = editTextStartPlace.getText().toString();
                String end_place = editTextEndPlace.getText().toString();
                String purpose = editTextPurpose.getText().toString();
                String desc = editTextDesc.getText().toString();
                String category_vehicle = editTextVehicleCategory.getText().toString();
                String vehicle_number = editTextVehicleNumber.getText().toString();
                String vehicle_type = vehicle_typeRadioButton.getText().toString();


                if (start_place.equals("")) {
                    editTextStartPlace.setError("Enter a valid place here");
                } else if (start_date.equals("")) {
                    editTextStartDate.setError("Enter a valid date here");
                } else if (start_time.equals("")) {
                    editTextStartTime.setError("Enter a valid time here");
                } else if (end_place.equals("")) {
                    editTextEndPlace.setError("Enter a valid place here");
                } else if (end_date.equals("")) {
                    editTextEndDate.setError("Enter a valid date here");
                } else if (end_time.equals("")) {
                    editTextEndTime.setError("Enter a valid time here");
                } else if (vehicle_type.equals("")) {
                    vehicle_typeRadioButton.setError("Enter a valid vehicle type here");
                } else if (purpose.equals("")) {
                    editTextPurpose.setError("Enter a valid purpose here");
                } else if (category_vehicle.equals("")) {
                    editTextVehicleCategory.setError("Enter a valid vehicle category here");
                } else if (vehicle_number.equals("")) {
                    editTextVehicleNumber.setError("Enter a valid vehicle registration number here");
                } else if (desc.equals("")) {
                    editTextDesc.setError("Enter a valid description here");
                } else {
                    boolean isInserted = myDbHelper.insertData(
                            start_date,
                            end_date,
                            start_time,
                            end_time,
                            start_place,
                            end_place,
                            purpose,
                            desc,
                            vehicle_type,
                            category_vehicle,
                            vehicle_number
                    );
                    if (isInserted) {
                        Snackbar.make(parentLayout, "Data Inserted", Snackbar.LENGTH_LONG).show();
//                    viewAll();
                        startActivity(new Intent(AddVisitActivity.this, HomeScreenActivity.class));
                        //TODO: Add More or goto main screen
                    } else {
                        Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });


    }

    public void viewAll() {

        Cursor cursor = myDbHelper.getAllData();

        //small test
        if (cursor.getCount() == 0) {
            showMessage("Error", "Nothing Found In DataBase");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while (cursor.moveToNext()) {
//                    buffer.append("__________________________"+"\n");
            buffer.append("ID: " + cursor.getString(0) + "\n");
            buffer.append("V_DATE: " + cursor.getString(1) + "\n");
            buffer.append("START_TIME: " + cursor.getString(2) + "\n");
            buffer.append("END_TIME: " + cursor.getString(3) + "\n");
            buffer.append("PLACE: " + cursor.getString(4) + "\n");
            buffer.append("PURPOSE: " + cursor.getString(5) + "\n");
            buffer.append("DESC: " + cursor.getString(6) + "\n\n");
        }
        showMessage("All Data", buffer.toString());

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        int mYear, mMonth, mDay, mHourFrom, mMinuteFrom, mHourTo, mMinuteTo;

        if (v == editTextStartDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        }
        if (v == editTextEndDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        }
        if (v == editTextStartTime) {

            // Get Current Time
            final Calendar cf = Calendar.getInstance();
            mHourFrom = cf.get(Calendar.HOUR_OF_DAY);
            mMinuteFrom = cf.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialogf = new TimePickerDialog(this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            int hour = hourOfDay % 12;
                            editTextStartTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                    minute, hourOfDay < 12 ? "AM" : "PM"));
                        }
                    }, mHourFrom, mMinuteFrom, false);
            timePickerDialogf.show();
        }
        if (v == editTextEndTime) {

            // Get Current Time
            final Calendar ct = Calendar.getInstance();
            mHourTo = ct.get(Calendar.HOUR_OF_DAY);
            mMinuteTo = ct.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialogt = new TimePickerDialog(this, R.style.DialogTheme,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            int hour = hourOfDay % 12;
                            editTextEndTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                    minute, hourOfDay < 12 ? "AM" : "PM"));

                        }
                    }, mHourTo, mMinuteTo, false);
            timePickerDialogt.show();
        }
    }

}
