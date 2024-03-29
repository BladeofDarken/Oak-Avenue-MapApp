package com.example.cslapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CatagoryDepartments extends AppCompatActivity {

    public static String MyCurrentLocation;
    public static int ButtonClicked;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_catagory_departments);

        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button10 = findViewById(R.id.button10);
        Button button11 = findViewById(R.id.button11);

        TextView changeText = findViewById(R.id.textView4);

        if(MainActivity.MainButtonPressed == 1){
            changeText.setText("STARTING LOCATION");
        }
        else if (MainActivity.MainButtonPressed == 2){
            changeText.setText("DESTINATION");
        }
        else{
            changeText.setText("INVALID");
        }

        button8.setOnClickListener(new View.OnClickListener() { // Search All
            @Override
            public void onClick(View v) {
                ButtonClicked = 1;
                changeActivityWithResult();
                finish();

            }
        });
        button9.setOnClickListener(new View.OnClickListener() { // My Current Location
            @Override
            public void onClick(View v) {

                if (MainActivity.MainButtonPressed == 2){
                    Toast.makeText(getApplicationContext(), "Error 3 : Hmm are you trying to use your current location as a marker ? Hint : Go back to the homepage and select Starting Location to use this functionality" , Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), "Error 3 : Hmm are you trying to use your current location as a marker ? Hint : Go back to the homepage and select Starting Location to use this functionality" , Toast.LENGTH_LONG).show();
                }
                else{
                    MyCurrentLocation = "My Current Location";
                    ButtonClicked = 2;
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result", MyCurrentLocation);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }



            }
        });
        button10.setOnClickListener(new View.OnClickListener() { // Popular Destinations
            @Override
            public void onClick(View v) {
                ButtonClicked = 3;
                changeActivityWithResult();
                finish();


            }
        });
        button11.setOnClickListener(new View.OnClickListener() { // Search By Department
            @Override
            public void onClick(View v) {
                ButtonClicked = 4;
                changeActivityToDepartments();
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        System.out.println("Back Key Pressed from Catagory Departments");
        startingLocation.selectedItem = "FILLER";
        System.out.println(startingLocation.selectedItem);
        super.onBackPressed();
    }

    private void changeActivityToDepartments(){
        Intent i = new Intent(this, SearchByDepartment.class);
        startActivity(i);
    }


    private void changeActivityWithResult() {
        int LAUNCH_SECOND_ACTIVITY = 1;
        Intent i = new Intent(this, startingLocation.class);
        i.putExtra("typeOfButton", ButtonClicked);
        startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
    }

    private void changeActivity(){
        Intent i = new Intent(this, startingLocation.class);
        startActivity(i);
    }

}