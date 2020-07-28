package com.ziyadkuttiady.coviddiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeScreenActivity extends AppCompatActivity implements CustomBottomSheet.BottomSheetListener {
    ListView listView;
    DataBaseHelper myDbHelper;
    public static ArrayList<ItemHistory> historyArrayList = new ArrayList<>();
    public static int position;
    public static String id;


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
                startActivity(new Intent(HomeScreenActivity.this, AddVisitActivity.class));
                finish();
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

        @SuppressLint("SimpleDateFormat") String today = new SimpleDateFormat("dd-M-yyyy").format(new Date());

        final CustomHistoryAdapter adapter = new CustomHistoryAdapter(this, historyArrayList);
        listView.setAdapter(adapter);

        historyArrayList.clear();
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        listView.refreshDrawableState();

        String lastAdded = "";
        while (cursor.moveToNext()) {

            if (lastAdded.equals(cursor.getString(2))) {
                historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5) + " -> " + cursor.getString(6), cursor.getString(3) + " - " + cursor.getString(4), cursor.getString(0)));
            } else {
                if (cursor.getString(1).equals(today)) {
                    historyArrayList.add(new CustomHistorySectionItem("Today's Activity", cursor.getString(0)));
                    historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5) + " - " + cursor.getString(6), cursor.getString(3) + " - " + cursor.getString(4), cursor.getString(0)));
                } else {
                    historyArrayList.add(new CustomHistorySectionItem(cursor.getString(2), cursor.getString(0)));
                    historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5) + " - " + cursor.getString(6), cursor.getString(3) + " - " + cursor.getString(4), cursor.getString(0)));

                }


            }

            adapter.notifyDataSetChanged();
            lastAdded = cursor.getString(1);


        }

        // set adapter
        listView.setTextFilterEnabled(true);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                TextView textView = view.findViewById(R.id.tvItemTitle);
//                String charSequence = (textView).getText().toString();
                if (!historyArrayList.get(i).isSection()) {
                    TextView textView = view.findViewById(R.id.tvItemTitle);
                    String charSequence = (textView).getText().toString();
                    position = i;
                    id = historyArrayList.get(i).getId();
//                    Toast.makeText(HomeScreenActivity.this, "Clicked " + charSequence, Toast.LENGTH_SHORT).show();

//                    Intent mIntent = new Intent(HomeScreenActivity.this, AddVisitActivity.class);
//                    startActivity(mIntent);
//                    Toast.makeText(HomeScreenActivity.this, "Clicked " + historyArrayList.get(i).getId(), Toast.LENGTH_SHORT).show();
                    CustomBottomSheet customBottomSheet = new CustomBottomSheet();
                    customBottomSheet.show(getSupportFragmentManager(), "CustomBottomSheet");
                }
            }
        });
    }

    @Override
    public void onButtonClicked(String text) {
        if (text.equals("edit")) {
            startActivity(new Intent(HomeScreenActivity.this, EditHistoryActivity.class));
        }
    }
}
