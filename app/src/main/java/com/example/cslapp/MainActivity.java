package com.example.cslapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {


    public static class ActivityClass {
        public static int VALUE;
        public static String result;


    }

    public static int MainButtonPressed;

    public static String dataretrieve;
    public static String dataretrieve2;

    private static String selectedValue;
    private static String selectedValue2;


    Button button1;
    Button button2;
    Button button3;

    Thread newThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        MainButtonPressed = 0;


        button1 = findViewById(R.id.currentlocation);
        button2 = findViewById(R.id.btnRound2);
        button3 = findViewById(R.id.btnRound3);



        button1.setOnClickListener(new View.OnClickListener() { // Starting Location
            @Override
            public void onClick(View v) {

                ActivityClass.VALUE = 1;
                MainButtonPressed = 1;
                runnable();
                changeToCategoryDepartmentsActivity();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() { // Destination
            @Override
            public void onClick(View v) {
                ActivityClass.VALUE = 2;
                MainButtonPressed = 2;

                runnable2();

                changeToCategoryDepartmentsActivity();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PrivacyMessage();
                variablesTransferred();


            }

        });



    }

    private void PrivacyMessage(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Are you sure to Exit")
//set message
                .setMessage("Exiting will call finish() method")
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        finish();
                    }
                })
//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }


    private void variablesTransferred(){

            dataretrieve = selectedValue;
            dataretrieve2 = selectedValue2;
        System.out.println("Data Retrieve = " + dataretrieve);
        System.out.println("Data Retrieve2 = " + dataretrieve2);

        if (dataretrieve == null){
            Toast.makeText(getApplicationContext(),"Please select a starting location before continuing", Toast.LENGTH_SHORT).show();
        }
        else if (dataretrieve2 == null){
            Toast.makeText(getApplicationContext(),"Please select a destination before continuing", Toast.LENGTH_SHORT).show();
        }
        else if (dataretrieve.equals(dataretrieve2)){
            Toast.makeText(getApplicationContext(),"Seems like your starting location is the same as your destination. You want to travel to where you are now? \uD83D\uDE09 ", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(MainActivity.this, MapRoute.class);
            startActivity(intent);
        }
    }

    private void runnable() {
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                while (startingLocation.selectedItem == null) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    button1.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            // System.out.println("Selected Item" + startingLocation.selectedItem);
                                if (startingLocation.selectedItem != null){
                                    if (startingLocation.selectedItem == "FILLER"){
                                        // System.out.println("8");
                                        startingLocation.selectedItem = null;
                                    }
                                    else{
                                        button1.setText(startingLocation.selectedItem);
                                        selectedValue = startingLocation.selectedItem;
                                        startingLocation.selectedItem = null;
                                        // System.out.println("6" + startingLocation.selectedItem);
                                        System.out.println("2" + "Final Value : " + selectedValue);
                                    }


                                }
                                if (CatagoryDepartments.ButtonClicked == 2){

                                    String MyCurrentLocation = "My Current Location";

                                    startingLocation.selectedItem = MyCurrentLocation;
                                    button1.setText(startingLocation.selectedItem);
                                    selectedValue = startingLocation.selectedItem;
                                    // System.out.println("3" + startingLocation.selectedItem);
                                    // startingLocation.selectedItem = null;
                                    System.out.println("Final Value : " + selectedValue);
                                    CatagoryDepartments.ButtonClicked = 0;
                                    // System.out.println("4" + startingLocation.selectedItem);
                                }

                            // System.out.println("5" + startingLocation.selectedItem);

                        }

                    });
                }
            }
        };

       newThread  = new Thread(myRunnable);
       newThread.start();
    }


    private void runnable2() {

        Runnable CheckForDestinationButtonClick = new Runnable() {
            @Override
            public void run() {
                while (startingLocation.selectedItem == null) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    button2.post(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            System.out.println("Selected Item" + startingLocation.selectedItem);
                            if (startingLocation.selectedItem != null){
                                if (startingLocation.selectedItem == "FILLER"){
                                    System.out.println("8");
                                    startingLocation.selectedItem = null;
                                }
                                else{
                                    button2.setText(startingLocation.selectedItem);
                                    selectedValue2 = startingLocation.selectedItem;
                                    startingLocation.selectedItem = null;
                                    System.out.println("6" + startingLocation.selectedItem);
                                    System.out.println("2" + "Final Value : " + selectedValue2);
                                }

                            }
                            if (CatagoryDepartments.ButtonClicked == 2){

                                String MyCurrentLocation = "My Current Location";

                                startingLocation.selectedItem = MyCurrentLocation;
                                button2.setText(startingLocation.selectedItem);
                                selectedValue2 = startingLocation.selectedItem;
                                System.out.println("3" + startingLocation.selectedItem);
                                // startingLocation.selectedItem = null;
                                System.out.println("Final Value : " + selectedValue2);
                                CatagoryDepartments.ButtonClicked = 0;
                                System.out.println("4" + startingLocation.selectedItem);

                            }
                            System.out.println("5" + startingLocation.selectedItem);

                        }

                    });
                }
            }
        };

        newThread  = new Thread(CheckForDestinationButtonClick);
        newThread.start();
    }

    private void changeToCategoryDepartmentsActivity() {
        Intent intent = new Intent(this, CatagoryDepartments.class);
        startActivity(intent);

    }

}





