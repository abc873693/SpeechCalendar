package rainvisitor.speechcalendar;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rainvisitor.speechcalendar.callback.BackgroundCallback;
import rainvisitor.speechcalendar.libs.AppDatabase;
import rainvisitor.speechcalendar.libs.MQTTHelper;
import rainvisitor.speechcalendar.libs.Utils;
import rainvisitor.speechcalendar.model.CallbackResponse;
import rainvisitor.speechcalendar.model.Event;

public class DeviceService extends Service {

    MQTTHelper mqttHelper;

    AppDatabase db;

    public DeviceService() {
        Log.e("DeviceService", "");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("onBind", "");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("onStartCommand", flags + " " + startId);
        mqttHelper = new MQTTHelper();
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic(MQTTHelper.TOPIC_CALLBACK, QoS.EXACTLY_ONCE));
        mqttHelper.subscribe(topicList, new BackgroundCallback() {
            @Override
            public void onError() {

            }

            @Override
            public void onFailure(Throwable throwable) {

            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onResponse(CallbackResponse response) {
                //TODO 通知
                Log.e("response", response.toString());
                // 建立NotificationCompat.Builder物件
                db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "database-name").allowMainThreadQueries().build();
                int id = Integer.parseInt(response.getID());
                Event event = db.eventDao().getByID(id);
                event.setStatus(1);
                db.eventDao().insert(event);
                Notification.InboxStyle style = new Notification.InboxStyle();
                //style.addLine(remoteMessage.getNotification().getBody());
                style.setSummaryText(Utils.ConvertTime(new Date()));
                Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
                // 設定圖片與簡介
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_priority_high);
                bigPictureStyle.bigPicture(bitmap).setSummaryText("大圖片");
                long[] vib = {50, 400, 200, 400, 50, 400};
                @SuppressLint("WrongConstant") Notification builder =
                        new Notification.Builder(DeviceService.this)
                                .setSmallIcon(R.drawable.ic_priority_high)
                                .setWhen(System.currentTimeMillis())
                                .setContentTitle("已" + event.getText())
                                .setContentText("已" + event.getText())
                                .setStyle(style)
                                .setVibrate(vib)
                                .setCategory(NotificationCompat.CATEGORY_PROMO)
                                .setPriority(Notification.PRIORITY_HIGH)
                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                                .build();
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                // 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
                manager.notify(0, builder);
                db.close();
            }
        });
        return START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("onUnbind", "");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("onRebind", "");
        super.onRebind(intent);
    }
}
