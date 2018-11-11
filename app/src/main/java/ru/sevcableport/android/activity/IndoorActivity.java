package ru.sevcableport.android.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.estimote.indoorsdk.EstimoteCloudCredentials;
import com.estimote.indoorsdk.IndoorLocationManagerBuilder;
import com.estimote.indoorsdk_module.algorithm.OnPositionUpdateListener;
import com.estimote.indoorsdk_module.algorithm.ScanningIndoorLocationManager;
import com.estimote.indoorsdk_module.cloud.CloudCallback;
import com.estimote.indoorsdk_module.cloud.EstimoteCloudException;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManager;
import com.estimote.indoorsdk_module.cloud.IndoorCloudManagerFactory;
import com.estimote.indoorsdk_module.cloud.Location;
import com.estimote.indoorsdk_module.cloud.LocationPosition;
import com.estimote.indoorsdk_module.view.IndoorLocationView;

import ru.sevcableport.android.R;

import static ru.sevcableport.android.SevkabelConst.ESTIMOTE_APP_ID;
import static ru.sevcableport.android.SevkabelConst.ESTIMOTE_APP_TOKEN;

public class IndoorActivity extends AppCompatActivity {

    private EstimoteCloudCredentials cloudCredentials =
            new EstimoteCloudCredentials(ESTIMOTE_APP_ID, ESTIMOTE_APP_TOKEN);

    private IndoorLocationView indoorLocationView;

    private ScanningIndoorLocationManager indoorLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_indoor);

        indoorLocationView = findViewById(R.id.indoor_view);

        getLocationFromIndoorCloudManager();
    }

    private void getLocationFromIndoorCloudManager() {
        IndoorCloudManager cloudManager = new IndoorCloudManagerFactory().create(this, cloudCredentials);
        cloudManager.getLocation("vk3-n23", new CloudCallback<Location>() {
            @Override
            public void success(Location location) {
                startPositioningIndoorLocationManager(location);
            }

            @Override
            public void failure(@NonNull EstimoteCloudException e) {
            }
        });
    }

    private void startPositioningIndoorLocationManager(Location location) {
        indoorLocationView.setLocation(location);

        indoorLocationManager = new IndoorLocationManagerBuilder(
                IndoorActivity.this, location, cloudCredentials)
                .withDefaultScanner()
                .build();

        indoorLocationManager.setOnPositionUpdateListener(new OnPositionUpdateListener() {
            @Override
            public void onPositionUpdate(@NonNull LocationPosition locationPosition) {
                indoorLocationView.updatePosition(locationPosition);
            }

            @Override
            public void onPositionOutsideLocation() {
                indoorLocationView.hidePosition();
            }
        });

        indoorLocationManager.startPositioning();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (indoorLocationManager != null) {
            indoorLocationManager.stopPositioning();
        }
    }
}
