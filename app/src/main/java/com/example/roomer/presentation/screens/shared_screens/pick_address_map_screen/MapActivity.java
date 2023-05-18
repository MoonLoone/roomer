package com.example.roomer.presentation.screens.shared_screens.pick_address_map_screen;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.app.ActivityCompat;

import com.example.roomer.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private Point myLocation;
    public static final String LATITUDE_EXTRA = "com.example.yandexmaptest.LATITUDE_EXTRA";
    public static final String LONGITUDE_EXTRA = "com.example.yandexmaptest.LONGITUDE_EXTRA";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_map);
        findViewById(R.id.btnConfirm).setOnClickListener(view -> finish());
        mapView = (MapView) findViewById(R.id.mapView);
        askLocationPermission();

        MapKit mapKit = MapKitFactory.getInstance();
        if (checkLatLongWereGiven(getIntent())) {
            Point initPoint = new Point(getIntent().getDoubleExtra(LATITUDE_EXTRA, 0), getIntent().getDoubleExtra(LONGITUDE_EXTRA, 0));
            drawMyLocationMark(initPoint, mapView);
            mapView.getMap().move(
                    new CameraPosition(initPoint, 12.0f, 0.0f, 0.0f),
                    new Animation(Animation.Type.SMOOTH, 2),
                    null);
        } else {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationUpdated(@NonNull Location location) {
                    myLocation = location.getPosition();
                    mapView.getMap().move(
                            new CameraPosition(myLocation, 12.0f, 0.0f, 0.0f),
                            new Animation(Animation.Type.SMOOTH, 2),
                            null);
                }

                @Override
                public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {}
            };
            mapKit.createLocationManager().requestSingleUpdate(locationListener);
        }
        mapView.getMap().addInputListener(new InputListener() {
            @Override
            public void onMapTap(@NonNull Map map, @NonNull Point point) {
                map.getMapObjects().clear();
                drawMyLocationMark(point, mapView);
            }

            @Override
            public void onMapLongTap(@NonNull Map map, @NonNull Point point) {}
        });
    }

    private void drawMyLocationMark(Point point, MapView mapview) {
        double latitude = round(point.getLatitude(), 4);
        double longitude = round(point.getLongitude(), 4);
        mapview.getMap().getMapObjects().addPlacemark(point, ImageProvider.fromResource(this, R.drawable.location_point));
        AppCompatEditText latitudeTv = findViewById(R.id.tvLatitude);
        AppCompatEditText longitudeTv = findViewById(R.id.tvLongitude);
        latitudeTv.setText(String.valueOf(latitude));
        longitudeTv.setText(String.valueOf(longitude));
        setCoordinatesResult(latitude, longitude);
    }

    private void setCoordinatesResult(double latitude, double longitude) {
        Intent data = new Intent();
        data.putExtra(LATITUDE_EXTRA, latitude);
        data.putExtra(LONGITUDE_EXTRA, longitude);
        setResult(RESULT_OK, data);
    }

    private boolean checkLatLongWereGiven(Intent intent) {
        double latitude = intent.getDoubleExtra(LATITUDE_EXTRA, 0);
        double longitude = intent.getDoubleExtra(LONGITUDE_EXTRA, 0);
        return latitude + longitude != 0;
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    private void askLocationPermission() {
        if (
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    0
            );
        }
    }

    public static Intent newIntent(Context packageContext, double latitude , double longitude) {
        Intent newIntent = new Intent(packageContext, MapActivity.class);
        newIntent.putExtra(LATITUDE_EXTRA, latitude);
        newIntent.putExtra(LONGITUDE_EXTRA, longitude);
        return newIntent;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bigDecim = BigDecimal.valueOf(value);
        bigDecim = bigDecim.setScale(places, RoundingMode.HALF_UP);
        return bigDecim.doubleValue();
    }
}
