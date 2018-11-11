package ru.sevcableport.android;

import android.app.Application;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;

import ru.sevcableport.android.manager.NotificationsManager;

import static ru.sevcableport.android.SevkabelConst.ESTIMOTE_APP_ID;
import static ru.sevcableport.android.SevkabelConst.ESTIMOTE_APP_TOKEN;

public class SevkabelApplication extends Application {

    public EstimoteCloudCredentials cloudCredentials = new EstimoteCloudCredentials(
            ESTIMOTE_APP_ID, ESTIMOTE_APP_TOKEN);

    private NotificationsManager notificationsManager;

    public void enableBeaconNotifications() {
        notificationsManager = new NotificationsManager(this);
        notificationsManager.startMonitoring();
    }
}
