package rainvisitor.speechcalendar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.ArrayList;
import java.util.List;

import rainvisitor.speechcalendar.callback.BackgroundCallback;
import rainvisitor.speechcalendar.libs.MQTTHelper;
import rainvisitor.speechcalendar.model.SensorResponse;

public class DeviceService extends Service {

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
        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic(MQTTHelper.TOPIC_SENSOR, QoS.EXACTLY_ONCE));
        MQTTHelper.subscribe(topicList, new BackgroundCallback() {
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
            public void onResponse(SensorResponse response) {
                //TODO 通知
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
