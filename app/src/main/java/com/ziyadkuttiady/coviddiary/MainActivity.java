package com.ziyadkuttiady.coviddiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button add_visit, add_travel, help;
    ListView listView;
    DataBaseHelper myDbHelper;
    ArrayList<ItemHistory> historyArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_travel = findViewById(R.id.add_travel);
        add_visit = findViewById(R.id.add_visit);
        help = findViewById(R.id.help);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String name = sharedPreferences.getString("name_of_user", "no");
        String phone = sharedPreferences.getString("phone_number_of_user", "no");
        String address = sharedPreferences.getString("address_of_user", "no");

        if (name.equals("no") &&
                phone.equals("no") &&
                address.equals("no")) {
            startActivity(new Intent(MainActivity.this, PersonalDataCollectActivity.class));
        }
        myDbHelper = new DataBaseHelper(this);

        listView = findViewById(R.id.listView);

        add_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddVisitActivity.class));
            }
        });
        add_travel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTravelingActivity.class));
            }
        });



        Cursor cursor = myDbHelper.getAllData();

        //small test
        if (cursor.getCount() == 0) {
            return;
        }

        StringBuffer buffer = new StringBuffer();

        final CustomHistoryAdapter adapter = new CustomHistoryAdapter(this, historyArrayList);

        while (cursor.moveToNext()) {
//                    buffer.append("__________________________"+"\n");
            buffer.append("ID: " + cursor.getString(0) + "\n");
            buffer.append("V_DATE: " + cursor.getString(1) + "\n");
            buffer.append("START_TIME: " + cursor.getString(2) + "\n");
            buffer.append("END_TIME: " + cursor.getString(3) + "\n");
            buffer.append("PLACE: " + cursor.getString(4) + "\n");
            buffer.append("PURPOSE: " + cursor.getString(5) + "\n");
            buffer.append("DESC: " + cursor.getString(6) + "\n\n");

            historyArrayList.add(new CustomHistorySectionItem(cursor.getString(1)));
            historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(4)));
            adapter.notifyDataSetChanged();


        }

        // set adapter
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        adapter.notifyDataSetChanged();
    }


}
