package com.ziyadkuttiady.coviddiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class AddVisitActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextDate,editTextStTime,editTextEndTime,editTextPlace,editTextPurpose,editTextDesc;
    Button buttonAdd;
    DataBaseHelper myDbHelper;
    NestedScrollView parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        myDbHelper = new DataBaseHelper( this);

        editTextDate =findViewById(R.id.editTextDate);
        editTextStTime =findViewById(R.id.editTextStartTime);
        editTextEndTime =findViewById(R.id.editTextEndTime);
        editTextPlace =findViewById(R.id.editTextPlace);
        editTextPurpose =findViewById(R.id.editTextPurpose);
        editTextDesc =findViewById(R.id.editTextDesc);

        parentLayout = findViewById(R.id.parent);

        editTextDate.setOnClickListener(AddVisitActivity.this);
        editTextStTime.setOnClickListener(AddVisitActivity.this);
        editTextEndTime.setOnClickListener(AddVisitActivity.this);


        buttonAdd = findViewById(R.id.buttonAddVisit);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v_date = editTextDate.getText().toString() ;
                String st_time = editTextStTime.getText().toString();
                String end_time = editTextEndTime.getText().toString();
                String place = editTextPlace.getText().toString();
                String purpose = editTextPurpose.getText().toString();
                String desc = editTextDesc.getText().toString();

                if(v_date.equals("")||st_time.equals("")||end_time.equals("")||place.equals("")||purpose.equals("")||desc.equals("")){
                    Snackbar.make(parentLayout,"Fill all Fields",Snackbar.LENGTH_LONG).show();
                }else{
                    boolean isInserted = myDbHelper.insertData(editTextDate.getText().toString(),
                            editTextStTime.getText().toString(),
                            editTextEndTime.getText().toString(),
                            editTextPlace.getText().toString(),
                            editTextPurpose.getText().toString(),
                            editTextDesc.getText().toString());
                    if (isInserted){
                        Snackbar.make(parentLayout,"Data Inserted",Snackbar.LENGTH_LONG).show();
//                    viewAll();
                        startActivity(new Intent(AddVisitActivity.this,MainActivity.class));
                    //TODO: Add More or goto main screen
                    }
                    else{
                        Snackbar.make(parentLayout,"Something Went Wrong",Snackbar.LENGTH_LONG).show();
                    }
                }

            }
        });


    }
    public void viewAll(){

                Cursor cursor = myDbHelper.getAllData();

                //small test
                if (cursor.getCount()==0){
                    showMessage( "Error","Nothing Found In DataBase" );
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
//                    buffer.append("__________________________"+"\n");
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("V_DATE: "+cursor.getString(1)+"\n");
                    buffer.append("START_TIME: "+cursor.getString(2)+"\n");
                    buffer.append("END_TIME: "+cursor.getString(3)+"\n");
                    buffer.append("PLACE: "+cursor.getString(4)+"\n");
                    buffer.append("PURPOSE: "+cursor.getString(5)+"\n");
                    buffer.append("DESC: "+cursor.getString(6)+"\n\n");
                }
                showMessage("All Data",buffer.toString());

            }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        int mYear, mMonth, mDay, mHourFrom, mMinuteFrom, mHourTo, mMinuteTo;

        if (v == editTextDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == editTextStTime) {

            // Get Current Time
            final Calendar cf = Calendar.getInstance();
            mHourFrom = cf.get(Calendar.HOUR_OF_DAY);
            mMinuteFrom = cf.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialogf = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            int hour = hourOfDay % 12;
                            editTextStTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
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
            TimePickerDialog timePickerDialogt = new TimePickerDialog(this,
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
