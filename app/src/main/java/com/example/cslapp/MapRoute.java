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
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Objects;


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

    private static ArrayList<GeoPoint> waypoints = new ArrayList<GeoPoint>();

    private OSRMRoadManager roadManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        System.out.println("Point 1");
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

        IMapController mapController = map.getController();
        GeoPoint point = new GeoPoint(34.117996037118125, -118.06497059621705);
        mapController.setCenter(point);
        mapController.setZoom(18.7);
        map.setMultiTouchControls(true);


        roadManager = new OSRMRoadManager(this, MY_USER_AGENT);
        roadManager.setMean(OSRMRoadManager.MEAN_BY_FOOT);

        newMarker.setIcon(getDrawable(R.drawable.greencurrentlocation));



        requestPermissionsIfNecessary(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        });


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Point 2");
                // mapController.setCenter(locationOverlay.getMyLocation());
                getLastLocation();

            }
        });

        newMethod();

        // zoomToCenterAroundMarkers(waypoints); // not working

    } // end of onCreate Method

    private void zoomToCenterAroundMarkers(ArrayList<GeoPoint> waypoints){


/*
        double north = -90;
        double south = 90;
        double west = 180;
        double east = -180;

        for (GeoPoint point : waypoints) {
            north = Math.max(point.getLatitude(), north);
            south = Math.min(point.getLatitude(), south);

            west = Math.min(point.getLongitude(), west);
            east = Math.max(point.getLongitude(), east);
        }



        BoundingBox box = BoundingBox.fromGeoPoints(waypoints);
        map.zoomToBoundingBox(box, true);

 */
    }



    private void newMethod() {

        //Instructional Buildings
        GeoPoint building1001 = new GeoPoint(34.11750490859198, -118.0636860310302);
        GeoPoint building1002 = new GeoPoint(34.117697431296456, -118.06367660778572);
        GeoPoint building1003 = new GeoPoint(34.117944604990726, -118.06362681312181);
        GeoPoint DrDougLearningCenter = new GeoPoint(34.11868230876788, -118.06611361556728);
        GeoPoint building700s = new GeoPoint(34.11879993429907, -118.06470320821654);
        GeoPoint building400s = new GeoPoint(34.11870191973566, -118.06386627158332);
        GeoPoint building500s = new GeoPoint(34.11889904744651, -118.06364119028532);


        // Faculty Buildings
        GeoPoint ASBRoom = new GeoPoint(34.118359217371854, -118.06394942494653);
        GeoPoint AssistantPrincipalsOffice = new GeoPoint(34.118118500427876, -118.0643308244969);
        GeoPoint CollegeAndCareerCenter = new GeoPoint(34.11811743850523, -118.06391616864046);
        GeoPoint PsychologistOffice = new GeoPoint(34.1181353782522, -118.06363595205275);
        GeoPoint AttendanceOffice = new GeoPoint(34.118071152851904, -118.06411672807296);
        GeoPoint ConferenceRoom = new GeoPoint(34.11809812001104, -118.06440262869094);
        GeoPoint NursesOffice = new GeoPoint(34.11817437078218, -118.06441657885566);
        GeoPoint SenateRoom = new GeoPoint(34.117952941166635, -118.06440349892497);



        //Sports & Recreation Buildings
        GeoPoint DanceStudio = new GeoPoint(34.11887553710273, -118.06504083612498);
        GeoPoint PerformingArts = new GeoPoint(34.11787689031118, -118.0649145241567);
        GeoPoint MediaCenter = new GeoPoint(34.11762892568293, -118.06442207806045);
        GeoPoint TennisCourts = new GeoPoint(34.117486144986756, -118.0658385353368);
        GeoPoint SoftballField = new GeoPoint(34.11820837278962, -118.06593097989567);
        GeoPoint LockerRooms = new GeoPoint(34.118251371129254, -118.06511096690667);
        GeoPoint InSchoolTennisCourts = new GeoPoint(34.118747213590446, -118.06527458073221);
        GeoPoint LunchShelter = new GeoPoint(34.118965698942525, -118.06405871664559);
        GeoPoint CafeteriaStaffLounge = new GeoPoint(34.11900300000708, -118.06412307729107);
        GeoPoint FootballField = new GeoPoint(34.119963658918834, -118.06505593848995);
        GeoPoint Room601 = new GeoPoint(34.11858230313357, -118.06419770167129);
        GeoPoint OutdoorBasketballCourts = new GeoPoint(34.11821040516513, -118.0648133098100);
        GeoPoint BecknerGym = new GeoPoint(34.118362625393765, -118.0648084618049);
        GeoPoint WeightRoom = new GeoPoint(34.11898995655553, -118.06496588924037);


        // Parking structures
        GeoPoint StaffParkingLot = new GeoPoint(34.117508629660776, -118.06534490570859);
        GeoPoint NorthParkingLot = new GeoPoint(34.119545448920874, -118.06414766931512);

        //Other Buildings
        GeoPoint SeniorCircle = new GeoPoint(34.11846193728855, -118.06444404741711);

        //Entrances
        GeoPoint FrontGate = new GeoPoint(34.11783147622169, -118.06465583951488);


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
                case "Activities Office":
                case "ASB Room":
                    startMarker.setPosition(ASBRoom);
                    waypoints.add(ASBRoom);
                    break;
                case "Assistant Principal's Office":
                case "Principal's Office":
                case "Sports Director's Office":
                    startMarker.setPosition(AssistantPrincipalsOffice);
                    waypoints.add(AssistantPrincipalsOffice);
                    break;
                case "Attendance Office":
                    startMarker.setPosition(AttendanceOffice);
                    waypoints.add(AttendanceOffice);
                    break;
                case "Basketball Courts":
                    startMarker.setPosition(OutdoorBasketballCourts);
                    waypoints.add(OutdoorBasketballCourts);
                    break;
                case "Beckner's Gym":
                case "Gym":
                    startMarker.setPosition(BecknerGym);
                    waypoints.add(BecknerGym);
                    break;
                case "College and Career Center":
                case "Counselor's Office":
                    startMarker.setPosition(CollegeAndCareerCenter);
                    waypoints.add(CollegeAndCareerCenter);
                    break;
                case "Conference Room":
                    startMarker.setPosition(ConferenceRoom);
                    waypoints.add(ConferenceRoom);
                    break;
                case "Dance Studio":
                    startMarker.setPosition(DanceStudio);
                    waypoints.add(DanceStudio);
                    break;
                case "Dr. Doug's Learning Center":
                    startMarker.setPosition(DrDougLearningCenter);
                    waypoints.add(DrDougLearningCenter);
                    break;
                case "Football Field / Track":
                    startMarker.setPosition(FootballField);
                    waypoints.add(FootballField);
                    break;
                case "In-school Tennis Courts":
                    startMarker.setPosition(InSchoolTennisCourts);
                    waypoints.add(InSchoolTennisCourts);
                    break;
                case "Locker Rooms":
                    startMarker.setPosition(LockerRooms);
                    waypoints.add(LockerRooms);
                    break;
                case "Lunch Shelter":
                    startMarker.setPosition(LunchShelter);
                    waypoints.add(LunchShelter);
                    break;
                case "Media Center":
                    startMarker.setPosition(MediaCenter);
                    waypoints.add(MediaCenter);
                    break;
                case "Nurse's Office":
                    startMarker.setPosition(NursesOffice);
                    waypoints.add(NursesOffice);
                    break;
                case "Psychologist's Office":
                    startMarker.setPosition(PsychologistOffice);
                    waypoints.add(PsychologistOffice);
                    break;
                case "Rooms 101-107":
                case "Rooms 201-207":
                    startMarker.setPosition(building1001);
                    waypoints.add(building1001);
                    break;
                case "Room 108-112":
                case "Rooms 208-212":
                    startMarker.setPosition(building1002);
                    waypoints.add(building1002);
                    break;
                case "Rooms 113-116":
                case "Rooms 213-216":
                    startMarker.setPosition(building1003);
                    waypoints.add(building1003);
                    break;
                case "Rooms 401-409":
                    startMarker.setPosition(building400s);
                    waypoints.add(building400s);
                    break;
                case "Rooms 501-507":
                    startMarker.setPosition(building500s);
                    waypoints.add(building500s);
                    break;
                case "Room 601":
                    startMarker.setPosition(Room601);
                    waypoints.add(Room601);
                    break;
                case "Rooms 702-709":
                    startMarker.setPosition(building700s);
                    waypoints.add(building700s);
                    break;
                case "Soccer Field":
                    startMarker.setPosition(FootballField);
                    waypoints.add(FootballField);
                    break;
                case "Softball Field":
                    startMarker.setPosition(StaffParkingLot);
                    waypoints.add(StaffParkingLot);
                    break;
                case "Staff Parking Lot":
                    startMarker.setPosition(SenateRoom);
                    waypoints.add(SenateRoom);
                    break;
                case "Student / North Parking Lot":
                    startMarker.setPosition(NorthParkingLot);
                    waypoints.add(NorthParkingLot);
                    break;
                case "Tennis Courts":
                    startMarker.setPosition(TennisCourts);
                    waypoints.add(TennisCourts);
                    break;
                case "Weight Room":
                    startMarker.setPosition(WeightRoom);
                    waypoints.add(WeightRoom);
                    break;

            }
        }

        if (MainActivity.dataretrieve2 != null) {
            switch (MainActivity.dataretrieve2) {
                case "My Current Location":
                    break;
                case "Activities Office":
                case "ASB Room":
                    startMarker2.setPosition(ASBRoom);
                    waypoints.add(ASBRoom);
                    break;
                case "Assistant Principal's Office":
                case "Principal's Office":
                case "Sports Director's Office":
                    startMarker2.setPosition(AssistantPrincipalsOffice);
                    waypoints.add(AssistantPrincipalsOffice);
                    break;
                case "Attendance Office":
                    startMarker2.setPosition(AttendanceOffice);
                    waypoints.add(AttendanceOffice);
                    break;
                case "Basketball Courts":
                    startMarker2.setPosition(OutdoorBasketballCourts);
                    waypoints.add(OutdoorBasketballCourts);
                    break;
                case "Beckner's Gym":
                case "Gym":
                    startMarker2.setPosition(BecknerGym);
                    waypoints.add(BecknerGym);
                    break;
                case "College and Career Center":
                case "Counselor's Office":
                    startMarker2.setPosition(CollegeAndCareerCenter);
                    waypoints.add(CollegeAndCareerCenter);
                    break;
                case "Conference Room":
                    startMarker2.setPosition(ConferenceRoom);
                    waypoints.add(ConferenceRoom);
                    break;
                case "Dance Studio":
                    startMarker2.setPosition(DanceStudio);
                    waypoints.add(DanceStudio);
                    break;
                case "Dr. Doug's Learning Center":
                    startMarker2.setPosition(DrDougLearningCenter);
                    waypoints.add(DrDougLearningCenter);
                    break;
                case "Football Field / Track":
                    startMarker2.setPosition(FootballField);
                    waypoints.add(FootballField);
                    break;
                case "In-school Tennis Courts":
                    startMarker2.setPosition(InSchoolTennisCourts);
                    waypoints.add(InSchoolTennisCourts);
                    break;
                case "Locker Rooms":
                    startMarker2.setPosition(LockerRooms);
                    waypoints.add(LockerRooms);
                    break;
                case "Lunch Shelter":
                    startMarker2.setPosition(LunchShelter);
                    waypoints.add(LunchShelter);
                    break;
                case "Media Center":
                    startMarker2.setPosition(MediaCenter);
                    waypoints.add(MediaCenter);
                    break;
                case "Nurse's Office":
                    startMarker2.setPosition(NursesOffice);
                    waypoints.add(NursesOffice);
                    break;
                case "Psychologist's Office":
                    startMarker2.setPosition(PsychologistOffice);
                    waypoints.add(PsychologistOffice);
                    break;
                case "Rooms 101-107":
                case "Rooms 201-207":
                    startMarker2.setPosition(building1001);
                    waypoints.add(building1001);
                    break;
                case "Room 108-112":
                case "Rooms 208-212":
                    startMarker2.setPosition(building1002);
                    waypoints.add(building1002);
                    break;
                case "Rooms 113-116":
                case "Rooms 213-216":
                    startMarker2.setPosition(building1003);
                    waypoints.add(building1003);
                    break;
                case "Rooms 401-409":
                    startMarker2.setPosition(building400s);
                    waypoints.add(building400s);
                    break;
                case "Rooms 501-507":
                    startMarker2.setPosition(building500s);
                    waypoints.add(building500s);
                    break;
                case "Room 601":
                    startMarker2.setPosition(Room601);
                    waypoints.add(Room601);
                    break;
                case "Rooms 702-709":
                    startMarker2.setPosition(building700s);
                    waypoints.add(building700s);
                    break;
                case "Soccer Field":
                    startMarker2.setPosition(FootballField);
                    waypoints.add(FootballField);
                    break;
                case "Softball Field":
                    startMarker2.setPosition(StaffParkingLot);
                    waypoints.add(StaffParkingLot);
                    break;
                case "Staff Parking Lot":
                    startMarker2.setPosition(SenateRoom);
                    waypoints.add(SenateRoom);
                    break;
                case "Student / North Parking Lot":
                    startMarker2.setPosition(NorthParkingLot);
                    waypoints.add(NorthParkingLot);
                    break;
                case "Tennis Courts":
                    startMarker2.setPosition(TennisCourts);
                    waypoints.add(TennisCourts);
                    break;
                case "Weight Room":
                    startMarker2.setPosition(WeightRoom);
                    waypoints.add(WeightRoom);
                    break;

            }
        }

        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        startMarker2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        map.getOverlays().add(startMarker);
        map.getOverlays().add(startMarker2);

        routeUpdate();
    }

    private void routeUpdate(){

        for (int i = 0; i < waypoints.size(); i++){
            System.out.println(i);
            System.out.println(waypoints.get(i));
        }

        Road road = roadManager.getRoad(waypoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
        map.getOverlays().add(0, roadOverlay);
        // map.invalidate();


/*
        Drawable nodeIcon = getResources().getDrawable(R.drawable.marker_node);
        for (int i = 0; i < road.mNodes.size(); i++) {
            RoadNode node = road.mNodes.get(i);
            Marker nodeMarker = new Marker(map);
            nodeMarker.setPosition(node.mLocation);
            nodeMarker.setIcon(nodeIcon);
            nodeMarker.setTitle("Step " + i);
            map.getOverlays().add(nodeMarker);
            nodeMarker.setSnippet(node.mInstructions);
            nodeMarker.setSubDescription(Road.getLengthDurationText(this, node.mLength, node.mDuration));

            int ManueverID = node.mManeuverType;

            switch (ManueverID) {
                case 0:
                    Drawable icon = getResources().getDrawable(R.drawable.ic_empty);
                    nodeMarker.setImage(icon);
                    break;
                case 1:
                    Drawable icon1 = getResources().getDrawable(R.drawable.ic_continue);
                    nodeMarker.setImage(icon1);
                    break;
                case 3:
                    Drawable icon2 = getResources().getDrawable(R.drawable.ic_slight_left);
                    nodeMarker.setImage(icon2);
                    break;
                case 4:
                    Drawable icon3 = getResources().getDrawable(R.drawable.ic_turn_left);
                    nodeMarker.setImage(icon3);
                    break;
                case 6:
                    Drawable icon4 = getResources().getDrawable(R.drawable.ic_slight_right);
                    nodeMarker.setImage(icon4);
                    break;
                case 7:
                    Drawable icon5 = getResources().getDrawable(R.drawable.ic_turn_right);
                    nodeMarker.setImage(icon5);
                    break;
                case 12:
                    Drawable icon6 = getResources().getDrawable(R.drawable.ic_u_turn);
                    nodeMarker.setImage(icon6);
                    break;

            }


        }

 */


    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

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
                int size = waypoints.size();

                System.out.println("MyCurrentLocation");
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
        System.out.println("Point 2");
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


