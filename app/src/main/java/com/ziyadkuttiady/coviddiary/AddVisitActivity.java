package com.ziyadkuttiady.coviddiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddVisitActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextStartDate, editTextEndDate, editTextStartTime, editTextEndTime, editTextStartPlace, editTextEndPlace, editTextPurpose, editTextDesc, editTextVehicleNumber;
    Button buttonAdd;
    DataBaseHelper myDbHelper;
    LinearLayoutCompat parentLayout;
    RadioButton vehicle_typeRadioButton;
    RadioGroup radioGroup;
    AutoCompleteTextView editTextVehicleCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.add_visit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



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

        String[] types = new String[]{"Bus", "Car", "Jeep", "Bike", "Cycle", "Walking", "Train", "Aeroplane", "Truck"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, types);
        editTextVehicleCategory.setAdapter(adapter);

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
                    editTextStartPlace.requestFocus();
                } else if (start_date.equals("")) {
                    editTextStartDate.setError("Enter a valid date here");
                    editTextStartDate.requestFocus();
                } else if (start_time.equals("")) {
                    editTextStartTime.setError("Enter a valid time here");
                    editTextStartTime.requestFocus();
                } else if (end_place.equals("")) {
                    editTextEndPlace.setError("Enter a valid place here");
                    editTextEndPlace.requestFocus();
                } else if (end_date.equals("")) {
                    editTextEndDate.setError("Enter a valid date here");
                    editTextEndDate.requestFocus();
                } else if (end_time.equals("")) {
                    editTextEndTime.setError("Enter a valid time here");
                    editTextEndTime.requestFocus();
                } else if (vehicle_type.equals("")) {
                    vehicle_typeRadioButton.setError("Enter a valid vehicle type here");
                    vehicle_typeRadioButton.requestFocus();
                } else if (purpose.equals("")) {
                    editTextPurpose.setError("Enter a valid purpose here");
                    editTextPurpose.requestFocus();
                } else if (category_vehicle.equals("")) {
                    editTextVehicleCategory.setError("Enter a valid vehicle category here");
                    editTextVehicleCategory.requestFocus();
                } else if (vehicle_number.equals("")) {
                    editTextVehicleNumber.setError("Enter a valid vehicle registration number here");
                    editTextVehicleNumber.requestFocus();
                    editTextVehicleNumber.setText("No");
                } else if (desc.equals("")) {
                    editTextDesc.setError("Enter a valid description here");
                    editTextDesc.requestFocus();
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
                        startActivity(new Intent(AddVisitActivity.this, HomeScreenActivity.class));
                        finish();
                        //TODO: Add More or goto main screen
                    } else {
                        Snackbar.make(parentLayout, "Something Went Wrong", Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });


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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddVisitActivity.this, HomeScreenActivity.class));
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
