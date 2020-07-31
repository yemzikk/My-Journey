package com.ziyadkuttiady.coviddiary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class HomeScreenActivity extends AppCompatActivity implements CustomBottomSheet.BottomSheetListener, NavigationView.OnNavigationItemSelectedListener {
    public static ArrayList<ItemHistory> historyArrayList = new ArrayList<>();
    public static int position;
    public static String id;
    final int FILE_SELECT_CODE = 0;
    ListView listView;
    DataBaseHelper myDbHelper;
    View navi_view;
    TextView nameTextView, phoneTextView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeScreenActivity.this);
        String name = sharedPreferences.getString("name_of_user", "no");
        String phone = sharedPreferences.getString("phone_number_of_user", "no");
        String address = sharedPreferences.getString("address_of_user", "no");

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, "Permission is revoked", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
        }

        NavigationView navigationView = findViewById(R.id.nav_view);
        navi_view = navigationView.getHeaderView(0);

        nameTextView = navi_view.findViewById(R.id.nameofuser);
        phoneTextView = navi_view.findViewById(R.id.phoneofuser);

        nameTextView.setText(name);
        phoneTextView.setText(phone);

        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(HomeScreenActivity.this, AddVisitActivity.class));
            }
        });


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
                if (cursor.getString(2).equals(today)) {
                    historyArrayList.add(new CustomHistorySectionItem("Today's Activity", cursor.getString(0)));
                    historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5) + " -> " + cursor.getString(6), cursor.getString(3) + " - " + cursor.getString(4), cursor.getString(0)));
                } else {
                    historyArrayList.add(new CustomHistorySectionItem(cursor.getString(2), cursor.getString(0)));
                    historyArrayList.add(new CustomHistoryEntryItem(cursor.getString(5) + " -> " + cursor.getString(6), cursor.getString(3) + " - " + cursor.getString(4), cursor.getString(0)));

                }

            }

            adapter.notifyDataSetChanged();
            lastAdded = cursor.getString(2);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_export:
                exportToExcelSheet();
                break;
            case R.id.nav_share:
                shareThisApp();
                break;
            case R.id.nav_help:
                break;
            case R.id.nav_settings:
                startActivity(new Intent(HomeScreenActivity.this, SettingsActivity.class));
                break;
            case R.id.nav_rate:
                rateUsOnPlayStore();
                break;
            case R.id.nav_about:
                startActivity(new Intent(HomeScreenActivity.this, AboutActivity.class));
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent homeScreenIntent = new Intent(Intent.ACTION_MAIN);
            homeScreenIntent.addCategory(Intent.CATEGORY_HOME);
            homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeScreenIntent);
            super.onBackPressed();
        }
    }

    public void exportToExcelSheet() {
        Cursor cursor = myDbHelper.getAllData();

        File sd = new File(Environment.getExternalStorageDirectory() + "/Covid Diary");
        String csvFile = "Covid Diary.xls";

        File directory = new File(sd.getAbsolutePath());
        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, csvFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(file, wbSettings);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Excel sheet name. 0 represents first sheet
        WritableSheet sheet = workbook.createSheet("My Travel History", 0);
        // column and row
        try {
            sheet.addCell(new Label(0, 0, "S.No"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(1, 0, "From Place"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(2, 0, "From Date"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(3, 0, "From Time"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(4, 0, "To Place"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(5, 0, "To Date"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(6, 0, "To Time"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(7, 0, "Purpose"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(8, 0, "Vehicle Type"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(9, 0, "Vehicle Category"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(10, 0, "Vehicle Registration Number"));
        } catch (WriteException e) {
            e.printStackTrace();
        }
        try {
            sheet.addCell(new Label(11, 0, "Description"));
        } catch (WriteException e) {
            e.printStackTrace();
        }

        if (cursor.moveToFirst()) {
            do {
                String sno = cursor.getString(0);
                String start_date = cursor.getString(1);
                String end_date = cursor.getString(2);
                String start_time = cursor.getString(3);
                String end_time = cursor.getString(4);
                String start_place = cursor.getString(5);
                String end_place = cursor.getString(6);
                String purpose = cursor.getString(7);
                String desc = cursor.getString(8);
                String vehicle_type = cursor.getString(9);
                String vehicle_category = cursor.getString(10);
                String vehicle_number = cursor.getString(11);


                int i = cursor.getPosition() + 1;
                try {
                    sheet.addCell(new Label(0, i, sno));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(1, i, start_place));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(2, i, start_date));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(3, i, start_time));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(4, i, end_place));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(5, i, end_date));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(6, i, end_time));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(7, i, purpose));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(8, i, vehicle_type));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(9, i, vehicle_category));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(10, i, vehicle_number));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
                try {
                    sheet.addCell(new Label(11, i, desc));
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());

            int position = cursor.getPosition() + 5;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(HomeScreenActivity.this);
            String name = sharedPreferences.getString("name_of_user", "no");
            String phone = sharedPreferences.getString("phone_number_of_user", "no");
            String address = sharedPreferences.getString("address_of_user", "no");
            try {
                sheet.addCell(new Label(0, position, "Name"));
            } catch (WriteException e) {
                e.printStackTrace();
            }
            try {
                sheet.addCell(new Label(1, position, name));
            } catch (WriteException e) {
                e.printStackTrace();
            }

            position++;

            try {
                sheet.addCell(new Label(0, position, "Phone"));
            } catch (WriteException e) {
                e.printStackTrace();
            }
            try {
                sheet.addCell(new Label(1, position, phone));
            } catch (WriteException e) {
                e.printStackTrace();
            }

            position++;
            try {
                sheet.addCell(new Label(0, position, "Address"));
            } catch (WriteException e) {
                e.printStackTrace();
            }
            try {
                sheet.addCell(new Label(1, position, address));
            } catch (WriteException e) {
                e.printStackTrace();
            }

        }

        //closing cursor
        cursor.close();
        try {
            workbook.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            workbook.close();
        } catch (IOException | WriteException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplication(),
                "Data Exported in a Excel Sheet" + directory, Toast.LENGTH_LONG).show();
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri xlsFileUri = Uri.parse(directory + "/Covid Diary.xls");
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, xlsFileUri);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public void rateUsOnPlayStore() {
        Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
        }
    }

    public void shareThisApp() {

        String shareBody = getString(R.string.sharelink);

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Covid Diary (Open it in Google Play Store to Download the Application)");

        share.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(share, "Share via"));
    }

}
