package com.example.cslapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

public class startingLocation extends AppCompatActivity {

    ListView listView;
    String[] allLocations = {
            "Additional Sports Area",
            "Attendance Office",
            "Back Field",
            "Basketball Courts",
            "Boy's Locker Room",
            "Character Garden",
            "Counselor Office",
            "ELP Room",
            "Front Lawn",
            "Girl's Locker Room",
            "Gym",
            "Library/Media Center",
            "Lunch Shelter",
            "Office (General)",
            "Pickleball Courts",
            "Quad",
            "Room 101-111",
            "Room 200",
            "Room 202",
            "Room 301",
            "Room 308",
            "Room 401-414",
            "Room 501-513",
            "Small Gym"
    };

    String[] PopularDestinations = {
            "Front Lawn",
            "Character Garden",
            "Quad",
            "Lunch Shelter",
    };
    String[] AdministrationBuildings = {
            "Office (General)",
            "Attendance Office",
            "Counselor Office"

    };
    String[] SportsFacilities = {
            "Gym",
            "Small Gym",
            "Girl's Locker Room",
            "Boy's Locker Room",
            "Basketball Courts",
            "Pickleball Courts",
            "Additional Sports Area",
            "Back Field",
    };
    String[] ParkingLots = {};

    String[] InstructionalBuildings = {
            "Room 101-111",
            "Room 200",
            "Room 202",
            "Room 301",
            "Room 308",
            "Room 401-414",
            "Room 501-513"
    };


    ArrayAdapter<String> arrayAdapter;
    public static String selectedItem;

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
        setContentView(R.layout.activity_starting_location);

        listView = findViewById(R.id.routeLIstView);


        if (CatagoryDepartments.ButtonClicked == 1){
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allLocations);
        }
        else if (CatagoryDepartments.ButtonClicked == 3){
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, PopularDestinations);
        }
        else if (CatagoryDepartments.ButtonClicked == 4){
            if (SearchByDepartment.buttonClicked == 1){
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AdministrationBuildings);
            }
            if (SearchByDepartment.buttonClicked == 2){
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, InstructionalBuildings);
            }
            if (SearchByDepartment.buttonClicked == 3){
                System.out.println("Hello");
                Toast.makeText(getApplicationContext(),"Awh. Seems like you hit a roadblock. This feature isn't available yet. Our development team is working day and night to improve the service. Be sure to check again next time and maybe it'll be fixed then :)", Toast.LENGTH_LONG).show();
            }
            if (SearchByDepartment.buttonClicked == 4){
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ParkingLots);
            }
            if (SearchByDepartment.buttonClicked == 5){
                System.out.println("Hello1");
                Toast.makeText(getApplicationContext(),"Awh. Seems like you hit a roadblock. This feature isn't available yet. Our development team is working day and night to improve the service. Be sure to check again next time and maybe it'll be fixed :)", Toast.LENGTH_LONG).show();
            }
            if (SearchByDepartment.buttonClicked == 6){
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, SportsFacilities);
            }
            else{
                System.out.println("Error");
            }

        }
        else{
            System.out.println("if statement error");
        }

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) parent.getItemAtPosition(position);

                System.out.println(selectedItem);

                if (selectedItem == null){
                    Toast.makeText(getApplicationContext(),"Please select a choice", Toast.LENGTH_SHORT).show();
                }
                finish();
             }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuitem.getActionView();
        searchView.setQueryHint("Choose Starting Location");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                arrayAdapter.getFilter().filter(newText);


                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
