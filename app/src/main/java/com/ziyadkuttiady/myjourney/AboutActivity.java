package com.ziyadkuttiady.myjourney;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
        //Page Content setting
        @SuppressLint("ResourceType") View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.logo)
//                .addItem(new Element().setTitle("About This App"))
                .setDescription(getString(R.string.descriptionApp))
                .addGroup("Connect with us")
                .addEmail("ssfstateit@gmail.com")
                .addWebsite("http://www.ssfkerala.org/")
                .addFacebook("https://www.facebook.com/KeralaStateSSF/")
                .addTwitter("https://twitter.com/SSFKerala")
                .addYoutube("https://www.youtube.com/channel/UCXX70Oz9Pe6mADWURP7EmQQ")
                .addPlayStore("https://play.google.com/store/apps/details?id=com.ziyadkuttiady.myjourney")
                .addInstagram("https://www.instagram.com/ssfkerala/")
//                .addGitHub("#")
                .addItem(new Element().setTitle("Developed by IT CELL SSF Kerala"))
                .addItem(new Element().setTitle("Version 1.0.0"))
                .addItem(getCopyRightsElement())
                .create();

        setContentView(aboutPage);
        TextView description = findViewById(R.id.description);
        description.setGravity(Gravity.START);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }


    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}