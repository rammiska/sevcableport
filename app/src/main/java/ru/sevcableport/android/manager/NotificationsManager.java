package ru.sevcableport.android.manager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import ru.sevcableport.android.R;
import ru.sevcableport.android.SevkabelApplication;
import ru.sevcableport.android.activity.ProximityContentActivity;
import ru.sevcableport.android.model.ProximityContent;

public class NotificationsManager {

    private static final String TAG = NotificationsManager.class.getSimpleName();

    private Context context;

    private NotificationManager notificationManager;

    public NotificationsManager(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private Notification buildNotification(ProximityContent content) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel contentChannel = new NotificationChannel(
                    "content_channel", "Things near you", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(contentChannel);
        }

        Intent intent = new Intent(context, ProximityContentActivity.class);
        intent.putExtra(ProximityContentActivity.SUBTITLE, content.getSubtitle());
        intent.putExtra(ProximityContentActivity.TEXT, content.getText());
        intent.putExtra(ProximityContentActivity.PHOTO_RESOURCE_ID, content.getPhotoResourceId());

        return new NotificationCompat.Builder(context, "content_channel")
                .setSmallIcon(content.getNotificationSmallIconResourceId())
                .setContentTitle(content.getTitle())
                .setContentText(content.getSubtitle())
                .setContentIntent(PendingIntent.getActivity(context, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    public void startMonitoring() {
        ProximityObserver proximityObserver =
                new ProximityObserverBuilder(context, ((SevkabelApplication) context).cloudCredentials)
                        .onError(new Function1<Throwable, Unit>() {
                            @Override
                            public Unit invoke(Throwable throwable) {
                                Log.e(TAG, "proximity observer error: " + throwable);
                                return null;
                            }
                        })
                        .withLowLatencyPowerMode()
                        .build();

        ProximityZone zone = new ProximityZoneBuilder()
                .forTag("proximity-test")
                .inCustomRange(3.0)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        notificationManager.notify(1, buildNotification(new ProximityContent(
                                "Латте ПРЯНАЯ ТЫКВА",
                                "Каждый день в MNTN COFFEE",
                                "Мы готовим кофе для всех, кто любит жизнь, кто вдохновлен приключениями и новыми идеями, так же как и мы, кто знает, что всегда - это \"сейчас\".\n" +
                                        "\n" +
                                        "Работаем как мобильная кофейня с открытым баром в \"Севкабель Порт\".\n" +
                                        "Кожевенная линия д.40\n" +
                                        "Каждый день с 12 до 21\n" +
                                        "Открыты для сотрудничества и интересных проектов.",
                                R.drawable.photo_10,
                                R.drawable.coffee)));
                        /*
                        switch (proximityContext.getDeviceId()) {
                            case "c3e7fd14c160bcd4c0b8f32ed52b8905": {
                                notificationManager.notify(1, buildNotification(new ProximityContent(
                                        "EASY HUMUS",
                                        "Приходите к нам пообедать скидкой 10%",
                                        "Сегодня прекрасная погода, приходите к нам пообедать/ поужинать и затем погулять\uD83D\uDE0A. С 12:00 до 22:00 проходит «Маркет у моря», на котором вы сможете найти предметы интерьера, одежды, аксессуары, парфюмерия, косметика, книги, игрушки. Кроме этого, на «Маркете у моря» также можно будет найти корнеры со сладостями, медом, свежей рыбой, фруктами и т.д. \uD83D\uDC4D\uD83C\uDFFB\uD83D\uDE01\n" +
                                                "Ждем вас\uD83D\uDE0E",
                                        R.drawable.photo_3,
                                        R.drawable.ic_silverware_fork_knife_white_24dp)));
                                break;
                            }
                            case "6ff810a8be1475d44052ce097b8b5a32": {
                                notificationManager.notify(2, buildNotification(new ProximityContent(
                                        "По следам белого медведя", "Пройди квест прямо сейчас", "Ежегодно 27 февраля отмечается Международный день полярного медведя, который в нашей стране чаще называют Днем белого медведя. Белые медведи – это вид, который находится на грани вымирания. Чтобы привлечь внимание общества к необходимости их охраны, а также для того, чтобы как можно больше людей узнали о полярных медведях, и был учрежден такой день.", R.drawable.photo_2, R.drawable.ic_bank_white_24dp)));
                                break;
                            }
                            case "0130356e4c81a9dcc251e9c9a4bc901c": {
                                break;
                            }
                        }
                       */
                        return null;
                    }
                })
                .onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext proximityContext) {
                        return null;
                    }
                })
                .build();
        proximityObserver.startObserving(zone);
    }
}
