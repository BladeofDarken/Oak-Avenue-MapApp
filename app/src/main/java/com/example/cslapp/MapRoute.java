package com.example.cslapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.*;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.GraphHopperRoadManager;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.MyMath;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import java.lang.Math;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;


public class MapRoute extends AppCompatActivity {

    private static final String MY_USER_AGENT = " ";
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;

    private double UserLatitude = 9999999999.9;
    private double UserLongitude = 9999999999.9;

    Marker newMarker;

    int minLat = Integer.MAX_VALUE;
    int maxLat = Integer.MIN_VALUE;
    int minLong = Integer.MAX_VALUE;
    int maxLong = Integer.MIN_VALUE;

    GeoPoint updatedUserLocation;

    IMapController mapController;

    private static ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();

    private OSRMRoadManager roadManager = null;
    public static RoadManager roadManager1 = null;
    public static RoadManager roadManager2 = null;
    public static RoadManager roadManager3 = null;


    GeoPoint getStartingMarker;
    GeoPoint getDestinationMarker;

    private static int timesZoomed = 2;

    private static double lat3 = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



        setContentView(R.layout.activity_map_route);

        Button button4 = findViewById(R.id.currentlocation);
        Button button5 = findViewById(R.id.destination);
        ImageButton button6 = findViewById(R.id.button6);


        if (MainActivity.dataretrieve != null && MainActivity.dataretrieve2 != null) {
            button4.setText(MainActivity.dataretrieve);
            button5.setText(MainActivity.dataretrieve2);

        }


        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        newMarker = new Marker(map);

        mapController = map.getController();
        GeoPoint point = new GeoPoint(34.117996037118125, -118.06497059621705);
        mapController.setCenter(point);
        mapController.setZoom(18.7);
        map.setMultiTouchControls(true);


        roadManager = new OSRMRoadManager(this, MY_USER_AGENT);
        roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT);


        roadManager1 = new GraphHopperRoadManager("b03b2897-af3e-47b4-83ca-f8cfefad541b", true);
        roadManager2 = new GraphHopperRoadManager("86aebc74-0f9f-4cda-8fec-ce82f71ad17c", true);
        roadManager3 = new GraphHopperRoadManager("7f4a401a-2a10-409f-99b3-bd674b4b5baa", true);

        roadManager1.addRequestOption("vehicle=foot");
        roadManager1.addRequestOption("optimize=true");




        newMarker.setIcon(getDrawable(R.drawable.greencurrentlocation));


        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mapController.setCenter(locationOverlay.getMyLocation());
                getLastLocation();

            }
        });

        newMethod();

    }



    private void newMethod() {
        // Instructional Buildings
        GeoPoint Building100s = new GeoPoint(34.11861424769509, -118.06906156575225);
        GeoPoint LibraryMediaCenter = new GeoPoint(34.11883591166017, -118.06881167567428);
        GeoPoint Room200 = new GeoPoint(34.118970996809104, -118.06868799197987);
        GeoPoint Room202 = new GeoPoint(34.11872658423966, -118.06878458425345);
        GeoPoint Room301 = new GeoPoint(34.11850312581686, -118.0697219358078);
        GeoPoint Room308 = new GeoPoint(34.11890754010351, -118.06983413560383);
        GeoPoint Building400s = new GeoPoint(34.11908958224379, -118.0689397025037);
        GeoPoint Building500s = new GeoPoint(34.11928128454475, -118.06898768887885);
        GeoPoint ELPRoom = new GeoPoint(34.118946124468785, -118.07023235031818);


        // AdministrationBuildings
        GeoPoint Office_General = new GeoPoint(34.11920532426637, -118.06823423699191);
        GeoPoint AttendanceOffice = new GeoPoint(34.11920532426637, -118.06823423699191);
        GeoPoint CounselorOffice = new GeoPoint(34.11933799253073, -118.06827379666952);


        // PopularLocations
        GeoPoint FrontLawn = new GeoPoint(34.1189907002026, -118.06813176069927);
        GeoPoint CharacterGarden = new GeoPoint(34.1190229998501, -118.06878275032294);
        GeoPoint Quad = new GeoPoint(34.11882981736492, -118.06916092383042);
        GeoPoint LunchShelter = new GeoPoint(34.11877170814532, -118.0695518499457);


        // Sports Facilities
        GeoPoint Gym = new GeoPoint(34.11868025107202, -118.0698752396658);
        GeoPoint SmallGym = new GeoPoint(34.11863846054736, -118.07000961822838);
        GeoPoint GirlsLockerRoom = new GeoPoint(34.11847412122788, -118.07002345675019);
        GeoPoint BoysLockerRoom = new GeoPoint(34.1188310998218, -118.07012275164075);
        GeoPoint BasketballCourts = new GeoPoint(34.11818865800368, -118.0702576260365);
        GeoPoint PickleballCourts = new GeoPoint(34.11859875588911, -118.07040099683263);
        GeoPoint AdditionalPickleBasketballCourt = new GeoPoint(34.11893308170183, -118.07050740984411);
        GeoPoint BackField = new GeoPoint(34.11844249722543, -118.07093569478131);



        // NEW SECTION STARTS HERE
        Marker startMarker = new Marker(map);
        startMarker.setIcon(getDrawable(R.drawable.greenlocation));
        startMarker.setSnippet("Starting Location");
        Marker startMarker2 = new Marker(map);
        startMarker2.setIcon(getDrawable(R.drawable.redlocation));
        startMarker2.setSnippet("Destination");

        if (MainActivity.dataretrieve != null) {
            switch (MainActivity.dataretrieve) {
                case "My Current Location":
                    break;
                case "Additional Sports Area":
                    startMarker.setPosition(AdditionalPickleBasketballCourt);
                    waypoints.add(AdditionalPickleBasketballCourt);
                    break;
                case "Attendance Office":
                    startMarker.setPosition(AttendanceOffice);
                    waypoints.add(AttendanceOffice);
                    break;
                case "Back Field":
                    startMarker.setPosition(BackField);
                    waypoints.add(BackField);
                    break;
                case "Basketball Courts":
                    startMarker.setPosition(BasketballCourts);
                    waypoints.add(BasketballCourts);
                    break;
                case "Boy's Locker Room":
                    startMarker.setPosition(BoysLockerRoom);
                    waypoints.add(BoysLockerRoom);
                    break;
                case "Character Garden":
                    startMarker.setPosition(CharacterGarden);
                    waypoints.add(CharacterGarden);
                    break;
                case "Counselor Office":
                    startMarker.setPosition(CounselorOffice);
                    waypoints.add(CounselorOffice);
                    break;
                case "ELP Room":
                    startMarker.setPosition(ELPRoom);
                    waypoints.add(ELPRoom);
                    break;
                case "Front Lawn":
                    startMarker.setPosition(FrontLawn);
                    waypoints.add(FrontLawn);
                    break;
                case "Girl's Locker Room":
                    startMarker.setPosition(GirlsLockerRoom);
                    waypoints.add(GirlsLockerRoom);
                    break;
                case "Gym":
                    startMarker.setPosition(Gym);
                    waypoints.add(Gym);
                    break;
                case "Library/Media Center":
                    startMarker.setPosition(LibraryMediaCenter);
                    waypoints.add(LibraryMediaCenter);
                    break;
                case "Lunch Shelter":
                    startMarker.setPosition(LunchShelter);
                    waypoints.add(LunchShelter);
                    break;
                case "Office (General)":
                    startMarker.setPosition(Office_General);
                    waypoints.add(Office_General);
                    break;
                case "Pickleball Courts":
                    startMarker.setPosition(PickleballCourts);
                    waypoints.add(PickleballCourts);
                    break;
                case "Quad":
                    startMarker.setPosition(Quad);
                    waypoints.add(Quad);
                    break;
// left off here
                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;

                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;

                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;

                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;

                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;

                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;
                case "":
                    startMarker.setPosition();
                    waypoints.add();
                    break;


            }
        }


        if (MainActivity.dataretrieve2 != null) {
            switch (MainActivity.dataretrieve2) {
                case "ASB Room":
                    startMarker2.setPosition();
                    waypoints.add();
                    break;

                // start here

            }
        }

        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        getStartingMarker = startMarker.getPosition();
        getDestinationMarker = startMarker2.getPosition();

        map.getOverlays().add(startMarker);
        map.getOverlays().add(startMarker2);

        if (CatagoryDepartments.ButtonClicked == 2){
            System.out.println("My Current Location Button Clicked");
        }
        else{
            mapZoom();
        }



        routeUpdate();
    }

    private void mapZoom(){

        double averageLat;
        double averageLong;
        double difference;

        double long3;




        double lat1 = getStartingMarker.getLatitude();
        double lat2 = getDestinationMarker.getLatitude();



        double long1 = getStartingMarker.getLongitude();
        double long2 = getDestinationMarker.getLongitude();


        if (timesZoomed == 1){
            lat3 = updatedUserLocation.getLatitude();
            long3 = updatedUserLocation.getLongitude();
            averageLat = (lat3 + lat2) / 2;
            averageLong = (long3 + long2) / 2;
        }
        else{
            averageLat = (lat1 + lat2) / 2;
            averageLong = (long1 + long2) / 2;
        }

        System.out.println("Average Lat" + averageLat);
        System.out.println("Average Long" + averageLong);


        GeoPoint AverageGeoPoint = new GeoPoint(averageLat, averageLong);


        if (timesZoomed == 1){
            System.out.println("Lat 2 : " + lat2);
            System.out.println("Lat 3 : " + lat3);
            difference = (Math.abs(lat2 - lat3));
            System.out.println("Difference" + difference);

        }
        else{
            difference = (Math.abs(lat1 - lat2));

        }


        double part1 = (360.0 / difference);
        System.out.println("Part 1 : " + part1);
        double result = (Math.log(part1) / Math.log(2));
        System.out.println("Final" + (result));


        if (timesZoomed == 1){
            System.out.println("Nothing changed");
            mapController.setZoom(result);
        }
        else{
            if (result < 18.3){
                System.out.println("Zoom is less than 18.5");
                mapController.setZoom(18.3);
            }
            else if (result > 20.9){
                System.out.println("Zoom is greater than 20.9");
                mapController.setZoom(20.9);
            }
            else{
                mapController.setZoom(result);
            }
        }

        mapController.setCenter(AverageGeoPoint);
    }



    private void routeUpdate(){

        Random rd = new Random();
        int int1 = rd.nextInt(3);

        System.out.println(waypoints);

        if (MainActivity.ServerButton == 1001){
            Road road = roadManager.getRoad(waypoints);
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
            map.getOverlays().add(0, roadOverlay);
        }
        else if (MainActivity.ServerButton == 1002){
            if (int1 == 1){
                Road road = roadManager1.getRoad(waypoints);
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                map.getOverlays().add(0, roadOverlay);
            }
            if (int1 == 2){
                Road road = roadManager2.getRoad(waypoints);
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                map.getOverlays().add(0, roadOverlay);
            }
            if (int1 == 0){
                Road road = roadManager3.getRoad(waypoints);
                Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
                map.getOverlays().add(0, roadOverlay);
            }
            else{
                System.out.println("Error");
            }


        }
        else{
            System.out.println("Error");
        }


        // map.invalidate();


    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {
                System.out.println("Location is enabled");

                // getting last
                // location from
                // FusedLocationClient
                // object
                if (UserLatitude == 9999999999.9 || UserLongitude == 9999999999.9){
                    requestNewLocationData();
                }else{
                    GeoPoint updatedUserLocation = new GeoPoint(UserLatitude, UserLongitude);
                    newMarker.setPosition(updatedUserLocation);
                    map.getOverlays().add(newMarker);
                    System.out.println("Message1");
                }


            } else {
                Toast.makeText(this, "Please turn on your location" , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {


        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(3000);
        // mLocationRequest.setFastestInterval(2000);
        // mLocationRequest.setNumUpdates(10);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            UserLatitude = (mLastLocation.getLatitude());
            UserLongitude = (mLastLocation.getLongitude());

            updatedUserLocation = new GeoPoint(UserLatitude, UserLongitude);

            newMarker.setPosition(updatedUserLocation);
            map.getOverlays().add(newMarker);


            if (Objects.equals(MainActivity.dataretrieve, "My Current Location")){

                if (timesZoomed == 1){
                    System.out.println("Inside timeZoomed if statement");
                    mapZoom();
                    timesZoomed = timesZoomed - 1;
                }
                if (timesZoomed > 1){
                    timesZoomed = timesZoomed - 1;
                    System.out.println("Time Zoomed" + timesZoomed);
                }

                int size = waypoints.size();


                if (waypoints.size() > 0){
                    if (waypoints.size() == 1) {
                        waypoints.add(updatedUserLocation);

                    }
                    if (waypoints.size() == 3){
                        waypoints.set(size - 1, updatedUserLocation);
                    }
                    map.getOverlays().remove(0);
                    routeUpdate();
                }

            }

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onBackPressed() {
        System.out.println("Back Key Pressed!");
        map.getOverlayManager().clear();
        waypoints.clear();
        finish();
        // super.onBackPressed();
    }




    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        map.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }

    }

    private void requestPermissionsIfNecessary(String[] permissions) {

        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                permissionsToRequest.add(permission);
            }
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this,
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }



    }
}


