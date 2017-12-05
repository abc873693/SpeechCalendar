package rainvisitor.speechcalendar.base;

import android.app.Application;
import android.arch.persistence.room.Room;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rainvisitor.speechcalendar.callback.VoiceCallback;
import rainvisitor.speechcalendar.libs.AppDatabase;
import rainvisitor.speechcalendar.libs.MQTTHelper;
import rainvisitor.speechcalendar.model.AirConditioner;
import rainvisitor.speechcalendar.model.Author;
import rainvisitor.speechcalendar.model.LightDimming;
import rainvisitor.speechcalendar.model.LightSwitch;
import rainvisitor.speechcalendar.model.Message;
import rainvisitor.speechcalendar.model.RoomInfo;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.TV;

/**
 * Created by Ray on 2017/9/23.
 */

public class BaseApplication extends Application {

    private VoiceCallback voiceCallback;

    private List<RoomItem> roomItemList;

    private AppDatabase db;

    private String userID = "11111";

    private String assistantID = "99999";

    private Author assistant = new Author(assistantID, "Assistant", "Assistant");

    private Author user = new Author(userID, "user", "user");

    @Override
    public void onCreate() {
        super.onCreate();
        initRoomDatabase();
        MQTTHelper.init();
        setDeviceData();
    }

    private void initRoomDatabase() {
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();
    }

    private void setDeviceData() {
        roomItemList = new ArrayList<>();
        roomItemList.add(new RoomInfo("即時資訊", new Topic(MQTTHelper.TOPIC_SENSOR, QoS.EXACTLY_ONCE)));
        roomItemList.add(new LightDimming("調光燈", new Topic(MQTTHelper.TOPIC_LIGHT_DIMMING, QoS.EXACTLY_ONCE)));
        roomItemList.add(new LightSwitch("層板燈", new Topic(MQTTHelper.TOPIC_LIGHT_SWITCH, QoS.EXACTLY_ONCE)));
        roomItemList.add(new TV("電視", new Topic(MQTTHelper.TOPIC_TV, QoS.EXACTLY_ONCE)));
        roomItemList.add(new AirConditioner("冷氣", new Topic(MQTTHelper.TOPIC_AIR_CONDITIONER, QoS.EXACTLY_ONCE)));
    }

    public AppDatabase getDB() {
        return db;
    }

    public List<RoomItem> getRoomItemList() {
        return roomItemList;
    }

    public void setRoomItemList(List<RoomItem> roomItemList) {
        this.roomItemList = roomItemList;
    }

    public VoiceCallback getVoiceCallback() {
        return voiceCallback;
    }

    public void setVoiceCallback(VoiceCallback voiceCallback) {
        this.voiceCallback = voiceCallback;
    }

    public String getUserID() {
        return userID;
    }

    public String getAssistantID() {
        return assistantID;
    }

    public Author getAssistant() {
        return assistant;
    }

    public Author getUser() {
        return user;
    }

    public void addUserMessage(String word) {
        getDB().messageDao().insertAll(new Message(userID, word, user, new Date().getTime()));
    }

    public void addAssistantMessage(String word) {
        getDB().messageDao().insertAll(new Message(assistantID, word, assistant, new Date().getTime()));
    }
}
