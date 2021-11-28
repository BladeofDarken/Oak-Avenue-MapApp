package com.example.cslapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class  SearchByDepartment extends AppCompatActivity {

    public static int buttonClicked;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_search_by_department);


        buttonClicked = 0;

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button7 = findViewById(R.id.button7);

        TextView changeText = findViewById(R.id.textView7);

        if (MainActivity.MainButtonPressed == 1) {
            changeText.setText("STARTING LOCATION");
        } else if (MainActivity.MainButtonPressed == 2) {
            changeText.setText("DESTINATION");
        } else {
            changeText.setText("INVALID");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = 1;
                changeActivityToStartingLocation();
                finish();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = 2;
                changeActivityToStartingLocation();
                finish();

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = 3;
                Toast.makeText(getApplicationContext(),"Awh. Seems like you hit a roadblock. This feature isn't available yet. Our development team is working to improve the service. Be sure to check again next time and maybe it'll be fixed then :)", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Awh. Seems like you hit a roadblock. This feature isn't available yet. Our development team is working day to improve the service. Be sure to check again next time and maybe it'll be fixed then :)", Toast.LENGTH_SHORT).show();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = 4;
                changeActivityToStartingLocation();
                finish();

            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = 5;
                Toast.makeText(getApplicationContext(),"Awh. Seems like you hit a roadblock. This feature isn't available yet. Our development team is working to improve the service. Be sure to check again next time and maybe it'll be fixed then :)", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Awh. Seems like you hit a roadblock. This feature isn't available yet. Our development team is working to improve the service. Be sure to check again next time and maybe it'll be fixed then :)", Toast.LENGTH_SHORT).show();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked = 6;
                changeActivityToStartingLocation();
                finish();

            }
        });

    }
    private void changeActivityToStartingLocation(){
        Intent i = new Intent(this, startingLocation.class);
        startActivity(i);
    }
}