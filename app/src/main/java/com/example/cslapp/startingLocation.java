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
    String[] allLocations = {"Activities Office", "ASB Room", "Aquatics Center / Pool", "Assistant Principal's Office", "Attendance Office", "Basketball Courts", "Beckner's Gym", "College and Career Center", "Conference Room", "Counselor's Office", "Dance Studio", "Dr. Doug's Learning Center", "Football Field / Track", "Gym", "In-school Tennis Courts", "Locker Rooms", "Lunch Shelter", "Media Center", "Nurse's Office", "Parking Lot - Staff", "Parking Lot - Student / North",  "Principal's Office", "Psychologist's Office", "Rooms 101-107", "Room 108-112", "Rooms 113-116", "Rooms 201-207", "Rooms 208-212", "Rooms 213-216", "Rooms 401-409", "Rooms 501-507", "Room 601", "Rooms 702-709", "Senate Room", "Soccer Field", "Softball Field", "Sports Director's Office", "Tennis Courts", "Weight Room"};
    String[] PopularDestinations = {"ASB Room", "College and Career Center", "Counselor's Office", "Football Field / Track", "Gym", "Lunch Shelter", "Media Center", "Nurse's Office"};
    String[] AdministrationBuildings = {"College and Career Center", "Counselor's Office", "Nurse's Office", "Psychologist's Office", "Attendance Office", "Principal's Office", "Assistant Principal's Office", "Sports Director's Office", "Activities Office", "Conference Room", "Senate Room"};
    String[] SportsFacilities = {"Aquatics Center / Pool", "Basketball Courts", "Beckner's Gym", "Football Field/Track", "In-school Tennis Courts", "Locker Rooms", "Soccer Field", "Softball Field", "Tennis Courts", "Weight Room", "Dance Studio"};
    String[] ParkingLots = {"Student / North Parking Lot", "Staff Parking Lot"};
    String[] InstructionalBuildings = {"Rooms 101-107", "Rooms 201-207", "Rooms 108-112, 208-212", "Rooms 113-116, 213-216", "Rooms 702-709, Weight Room, Dance Studio", "Rooms 401-409", "Rooms 501-507", "Dr. Doug's Learning Center"};
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
