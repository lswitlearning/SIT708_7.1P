package com.example.task7_1p;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    private RadioGroup radioGroup; // Group containing radio buttons
    private RadioButton radioLost; // RadioButton for 'Lost'
    private RadioButton radioFound; // RadioButton for 'Found'
    private EditText nameEditText; // EditText for name
    private EditText phoneEditText; // EditText for phone number
    private EditText descriptionEditText; // EditText for description
    private EditText locationEditText; // EditText for location
    private DatePicker datePicker; // DatePicker for selecting a date
    private Button saveButton; // Button to save the item

    private DatabaseHelper dbHelper; // Helper to interact with the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity); // Set the layout for this activity

        dbHelper = new DatabaseHelper(this); // Initialize the database helper

        // Initialize view components
        radioGroup = findViewById(R.id.radioGroup);
        radioLost = findViewById(R.id.radio_lost); // 'Lost' RadioButton
        radioFound = findViewById(R.id.radio_found); // 'Found' RadioButton
        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        locationEditText = findViewById(R.id.locationEditText);
        datePicker = findViewById(R.id.datePicker);
        saveButton = findViewById(R.id.savebutton);

        // Ensure all components are initialized properly
        if (radioGroup == null || radioLost == null || radioFound == null || nameEditText == null ||
                phoneEditText == null || descriptionEditText == null || locationEditText == null || datePicker == null || saveButton == null) {
            Toast.makeText(this, "Initialization error", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity to avoid issues
            return; // Exit the method
        }

        // Set the click event for the save button
        saveButton.setOnClickListener(v -> {
            // Ensure a RadioButton is selected
            int checkedId = radioGroup.getCheckedRadioButtonId(); // Get the ID of the selected RadioButton
            RadioButton selectedButton = findViewById(checkedId); // Find the selected RadioButton

            if (selectedButton == null) {
                Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
                return; // Exit if no RadioButton is selected
            }

            // Get input data from the form
            String postType = selectedButton.getText().toString(); // Text from the selected RadioButton
            String name = nameEditText.getText().toString(); // Name from the EditText
            String phone = phoneEditText.getText().toString(); // Phone number from the EditText
            String description = descriptionEditText.getText().toString(); // Description from the EditText
            String location = locationEditText.getText().toString(); // Location from the EditText

            // Get the date from the DatePicker
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1; // Month is zero-based
            int year = datePicker.getYear();

            String formattedDate = year + "-" + month + "-" + day; // Format the date as a string

            // Insert the data into the database
            dbHelper.insertItem(postType, name, phone, description, formattedDate, location);

            // Notify the user and return to the main activity
            Toast.makeText(CreateActivity.this, "Item saved", Toast.LENGTH_SHORT).show();
            finish(); // Close the current activity
        });
    }
}
