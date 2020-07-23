package com.ziyadkuttiady.coviddiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalDataCollectActivity extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    String name_of_user,phone_number_of_user,address_of_user;
    EditText nameEditText,phoneEditText,addressEditText;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_collect);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PersonalDataCollectActivity.this);
        editor = sharedPreferences.edit();

        nameEditText = findViewById(R.id.editTextName);
        phoneEditText = findViewById(R.id.editTextPhone);
        addressEditText = findViewById(R.id.editTextAddress);

        continueButton = findViewById(R.id.buttonContinue);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_user = nameEditText.getText().toString();
                phone_number_of_user = phoneEditText.getText().toString();
                address_of_user = addressEditText.getText().toString();
                if (name_of_user.equals("")){
                    nameEditText.setError("Enter a valid name here");
                }else if (!android.util.Patterns.PHONE.matcher(phone_number_of_user).matches()){
                    phoneEditText.setError("Enter a valid phone number");
                }else if (address_of_user.equals("")&&address_of_user.length() >=10){
                    addressEditText.setError("Enter a valid address here");
                }else{
                    editor.putString("name_of_user", name_of_user);
                    editor.putString("phone_number_of_user", phone_number_of_user);
                    editor.putString("address_of_user", address_of_user);
                    editor.apply();
                    startActivity(new Intent(PersonalDataCollectActivity.this,MainActivity.class));
                }

            }
        });


    }
}
