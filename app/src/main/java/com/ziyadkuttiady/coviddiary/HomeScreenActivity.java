package com.ziyadkuttiady.coviddiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity {
    ListView listView;
    DataBaseHelper myDbHelper;
    ArrayList<ItemHistory> historyArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this,AddVisitActivity.class));
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeScreenActivity.this);
        String name = sharedPreferences.getString("name_of_user", "no");
        String phone = sharedPreferences.getString("phone_number_of_user", "no");
        String address = sharedPreferences.getString("address_of_user", "no");

        if (name.equals("no") &&
                phone.equals("no") &&
                address.equals("no")) {
            startActivity(new Intent(HomeScreenActivity.this, PersonalDataCollectActivity.class));
        }
        myDbHelper = new DataBaseHelper(this);

        listView = findViewById(R.id.listViewHistory);


        Cursor cursor = myDbHelper.getAllData();

        //small test
        if (cursor.getCount() == 0) {
            return;
        }

        StringBuffer buffer = new StringBuffer();
        String today = new SimpleDateFormat("dd-M-yyyy").format(new Date());

        final CustomHistoryAdapter adapter = new CustomHistoryAdapter(this, historyArrayList);

        String lastAdded = "";
        while (cursor.moveToNext()) {
//                    buffer.append("__________________________"+"\n");
            buffer.append("ID: " + cursor.getString(0) + "\n");
            buffer.append("V_DATE: " + cursor.getString(1) + "\n");
            buffer.append("START_TIME: " + cursor.getString(2) + "\n");
            buffer.append("END_TIME: " + cursor.getString(3) + "\n");
            buffer.append("PLACE: " + cursor.getString(4) + "\n");
            buffer.append("PURPOSE: " + cursor.getString(5) + "\n");
            buffer.append("DESC: " + cursor.getString(6) + "\n\n");

//                historyArrayList.add(new CustomHistorySectionItem(cursor.getString(1)));
            if (lastAdded.equals(cursor.getString(2))){
                historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5)+" - "+cursor.getString(6), cursor.getString(3)+" - "+cursor.getString(4)));
            }else{
                if (cursor.getString(1).equals(today)){
                    historyArrayList.add(new CustomHistorySectionItem("Today's Activity"));
                    historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5)+" - "+cursor.getString(6), cursor.getString(3)+" - "+cursor.getString(4)));
                }else{
                    historyArrayList.add(new CustomHistorySectionItem(cursor.getString(2)));
                    historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5)+" - "+cursor.getString(6), cursor.getString(3)+" - "+cursor.getString(4)));

                }


            }
//            historyArrayList.add(new CustomHistorySectionItem(cursor.getString(1)));
//            historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(4)));
            adapter.notifyDataSetChanged();
            lastAdded = cursor.getString(1);


        }

        // set adapter
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        adapter.notifyDataSetChanged();
    }

}
