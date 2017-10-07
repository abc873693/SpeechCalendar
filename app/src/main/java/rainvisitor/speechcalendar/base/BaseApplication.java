package rainvisitor.speechcalendar.base;

import android.app.Application;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.ArrayList;
import java.util.List;

import rainvisitor.speechcalendar.libs.DB;
import rainvisitor.speechcalendar.libs.MQTTHelper;
import rainvisitor.speechcalendar.model.AirConditioner;
import rainvisitor.speechcalendar.model.LightDimming;
import rainvisitor.speechcalendar.model.LightSwitch;
import rainvisitor.speechcalendar.model.RoomInfo;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.TV;

/**
 * Created by Ray on 2017/9/23.
 */

public class BaseApplication extends Application {

    private List<RoomItem> roomItemList;

    private DB db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DB(getApplicationContext());
        MQTTHelper.init();
        setDeviceData();
    }

    private void setDeviceData() {
        roomItemList = new ArrayList<>();
        roomItemList.add(new RoomInfo("即時資訊", new Topic(MQTTHelper.TOPIC_SENSOR, QoS.EXACTLY_ONCE)));
        roomItemList.add(new LightDimming("調光燈", new Topic(MQTTHelper.TOPIC_LIGHT_DIMMING, QoS.EXACTLY_ONCE)));
        roomItemList.add(new LightSwitch("層板燈", new Topic(MQTTHelper.TOPIC_LIGHT_SWITCH, QoS.EXACTLY_ONCE)));
        roomItemList.add(new TV("電視", new Topic(MQTTHelper.TOPIC_TV, QoS.EXACTLY_ONCE)));
        roomItemList.add(new AirConditioner("冷氣", new Topic(MQTTHelper.TOPIC_AIR_CONDITIONER, QoS.EXACTLY_ONCE)));
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }

    public List<RoomItem> getRoomItemList() {
        return roomItemList;
    }

    public void setRoomItemList(List<RoomItem> roomItemList) {
        this.roomItemList = roomItemList;
    }
}
