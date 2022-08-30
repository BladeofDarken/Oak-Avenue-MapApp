package com.example.cslapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int LOCATION_PERMISSION_CODE = 101;


    public static class ActivityClass {
        public static int VALUE;
        public static String result;


    }

    public static int MainButtonPressed;
    public static int ServerButton;

    public static String dataretrieve;
    public static String dataretrieve2;

    private static String selectedValue;
    private static String selectedValue2;


    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Button button5;

    Thread newThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#32D125"));
        actionBar.setBackgroundDrawable(colorDrawable);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        MainButtonPressed = 0;
        ServerButton = 1001;


        button1 = findViewById(R.id.currentlocation);
        button2 = findViewById(R.id.btnRound2);
        button3 = findViewById(R.id.btnRound3);
        button4 = findViewById(R.id.s1);
        button5 = findViewById(R.id.s2);




        button1.setOnClickListener(new View.OnClickListener() { // Starting Location
            @Override
            public void onClick(View v) {

                ActivityClass.VALUE = 1;
                MainButtonPressed = 1;
                runnable();
                changeToCategoryDepartmentsActivity();
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);

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
                variablesTransferred();


            }

        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Server 1 Clicked!");
                ServerButton = 1001;


            }

        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Server 2 Clicked!");
                ServerButton = 1002;
            }

        });



        checkFirstRun();

    }

    public void checkFirstRun() {
        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Terms and Conditions Acknowledgement");
            builder.setMessage("Thank you for using our map. Please review our privacy policy. Keep in mind, everything from this app is through AI software, so please use your own discretion. The map will only bring you to an approximate location, and if you find any bugs, please help us and report them. Happy travels!");
            builder.setPositiveButton("I UNDERSTAND",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_PERMISSION_CODE);
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();

            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("isFirstRun", false)
                    .apply();
        }

    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission }, requestCode);
        }
        else {
            System.out.println("Permission is already granted");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(MainActivity.this, "Location Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }

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
        else if (MainActivity.ServerButton == 1002 && dataretrieve.equals("My Current Location")){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setCancelable(false);
            builder.setTitle("ERROR 426");
            builder.setMessage("Due to a shortage of server resources, Server 1 is the only server which can be used for My Current Location. Server 1 has been selected for your convenience. If you wish to use Server 2, please select from the Main Menu");
            builder.setPositiveButton("I UNDERSTAND, LETS PROCEED",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.ServerButton = 1001;
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            System.out.println(dataretrieve);
            System.out.println(MainActivity.ServerButton);
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





