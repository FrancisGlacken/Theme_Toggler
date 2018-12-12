package com.deltorostudios.themetoggler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;


//TODO: Figure out how a spinner can be used to change the theme usign this rough ass finish() shared pref strat

public class MainActivity extends AppCompatActivity {

    // Strings for Shared Preferences
    private static final String PREFS_FILE_NAME = "myPrefs";
    private static final String PREFS_DARK_THEME_KEY = "dark_key";
    private static final String PREFS_BLUE_THEME_KEY = "blue_key";
    private static final String PREFS_RED_THEME_KEY = "red_key";
    private static final String PREFS_SPINNER_SELECTION_KEY = "spinner_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create sharedPrefs object and editor object
        SharedPreferences preferences = getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        // Create booleans that will receive a value from sharedPreferences
        final boolean darkThemeOn = preferences.getBoolean(PREFS_DARK_THEME_KEY, false);
        final boolean blueThemeOn = preferences.getBoolean(PREFS_BLUE_THEME_KEY, false);
        final boolean redThemeOn = preferences.getBoolean(PREFS_RED_THEME_KEY, false);
        final int spinnerSelection = preferences.getInt(PREFS_SPINNER_SELECTION_KEY, 0);


        // Load the theme that was selected in the spinner, or use the default theme
        if (spinnerSelection == 1) {
            // Set Blue theme
            setTheme(R.style.AppTheme_Blue);
        } else if (spinnerSelection == 2) {
            // Set Red theme
            setTheme(R.style.AppTheme_Red);
        } else {
            setTheme(R.style.AppTheme_Dark);
        }


        // Create layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Create Spinner object and populate it with data from resources
        Spinner mySpinner = findViewById(R.id.mySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mySpinnerData, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        // Make spinner selected item equal to save position in sharedPref
        mySpinner.setSelection(spinnerSelection);

        // Create on selected listener for Spinner
        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                SharedPreferences.Editor editor = getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).edit();

                switch(position) {
                    case 0:
                        // If position = 0, save DarkTheme = true to sharedPref, other themes = false
                        editor.putInt(PREFS_SPINNER_SELECTION_KEY, position);
                        editor.putBoolean(PREFS_DARK_THEME_KEY, true);
                        editor.putBoolean(PREFS_BLUE_THEME_KEY, false);
                        editor.putBoolean(PREFS_RED_THEME_KEY, false);
                        editor.apply();

                        // If darkThemeOn = false, restart Activity
                        if (!darkThemeOn) {
                            redoTheme();
                        }
                        break;

                    case 1:
                        editor.putInt(PREFS_SPINNER_SELECTION_KEY, position);
                        editor.putBoolean(PREFS_DARK_THEME_KEY, false);
                        //Blue theme to true
                        editor.putBoolean(PREFS_BLUE_THEME_KEY, true);
                        editor.putBoolean(PREFS_RED_THEME_KEY, false);
                        editor.apply();

                        if (!blueThemeOn) {
                            redoTheme();
                        }
                        break;

                    case 2:
                        editor.putInt(PREFS_SPINNER_SELECTION_KEY, position);
                        editor.putBoolean(PREFS_DARK_THEME_KEY, false);
                        editor.putBoolean(PREFS_BLUE_THEME_KEY, false);
                        //Red theme to true
                        editor.putBoolean(PREFS_RED_THEME_KEY, true);
                        editor.apply();

                        if (!redThemeOn) {
                            redoTheme();
                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(MainActivity.this, "Do nothing as nothing was selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void redoTheme() {
        finish();
        Intent intent = getIntent();
        startActivity(intent);
    }




}

