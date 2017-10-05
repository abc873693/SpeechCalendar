package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Topic;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import rainvisitor.speechcalendar.callback.RoomCallback;
import rainvisitor.speechcalendar.model.AirConditionerResponse;
import rainvisitor.speechcalendar.model.LightDimmingResponse;
import rainvisitor.speechcalendar.model.LightSwitchResponse;
import rainvisitor.speechcalendar.model.RoomInfoResponse;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.TVResponse;

/**
 * Created by Ray on 2017/10/5.
 */

public class MQTTHelper {

    private static MQTT mqtt = new MQTT();

    public static CallbackConnection connection;

    private static final String HOST = "192.168.200.50";
    private static final int PORT = 63101;

    private static final String USERNAME = "icpsi";
    private static final String PASSWORD = "59209167";

    public static final String TOPIC_SENSOR = "demo/home/1709181907/sensor";
    public static final String TOPIC_LIGHT_SWITCH = "demo/home/1709181907/dimming";
    public static final String TOPIC_LIGHT_DIMMING = "demo/home/1709181907/BattenLighting";
    public static final String TOPIC_TV = "demo/home/1709181907/TV";
    public static final String TOPIC_AIR_CONDITIONER = "demo/home/1709181907/AC";

    public static void init() {
        try {
            mqtt.setHost(HOST, PORT);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        mqtt.setUserName(USERNAME);
        mqtt.setPassword(PASSWORD);
        connection = mqtt.callbackConnection();
    }

    public static void subscribe(final Activity activity, final List<RoomItem> roomItems, final RoomCallback callback) {
        connection.listener(new Listener() {

            public void onDisconnected() {
            }

            public void onConnected() {
            }

            public void onPublish(UTF8Buffer topic, Buffer payload, Runnable ack) {

                // You can now process a received message from a topic.
                // Once process execute the ack runnable.
                ack.run();
                String data = null, topicText = null;
                try {
                    data = new String(payload.getData(), "UTF8");
                    topicText = new String(topic.getData(), "UTF8");
                } catch (UnsupportedEncodingException e) {
                    callback.onFailure(e);
                }
                assert data != null;
                int start = data.indexOf('{');
                final String content = data.substring(start);
                final String finalTopicText = topicText;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        assert finalTopicText != null;
                        int position = -1;
                        for (int i = 0; i < roomItems.size(); i++) {
                            if (finalTopicText.equals(roomItems.get(i).getTopicText()))
                                position = i;
                        }
                        if (position == -1) callback.onError();
                        if (finalTopicText.contains(TOPIC_SENSOR)) {
                            final RoomInfoResponse roomInfoResponse = new Gson().fromJson(content, RoomInfoResponse.class);
                            callback.onRoomInfo(roomInfoResponse, position);
                        } else if (finalTopicText.contains(TOPIC_LIGHT_DIMMING)) {
                            final LightDimmingResponse lightDimmingResponse = new Gson().fromJson(content, LightDimmingResponse.class);
                            callback.onLightDimming(lightDimmingResponse, position);
                        } else if (finalTopicText.contains(TOPIC_LIGHT_SWITCH)) {
                            final LightSwitchResponse lightSwitchResponse = new Gson().fromJson(content, LightSwitchResponse.class);
                            callback.onLightSwitch(lightSwitchResponse, position);
                        } else if (finalTopicText.contains(TOPIC_TV)) {
                            final TVResponse tvResponse = new Gson().fromJson(content, TVResponse.class);
                            callback.onTv(tvResponse, position);
                        } else if (finalTopicText.contains(TOPIC_AIR_CONDITIONER)) {
                            final AirConditionerResponse airConditionerResponse = new Gson().fromJson(content, AirConditionerResponse.class);
                            callback.onAirConditioner(airConditionerResponse, position);
                        } else {
                            callback.onError();
                        }
                    }
                });
            }

            public void onFailure(Throwable value) {
                // connection.close(null); // a connection failure occured.
                callback.onFailure(value);
            }
        });
        connection.connect(new Callback<Void>() {
            public void onFailure(Throwable value) {
                //  result.failure(value); // If we could not connect to the server.
                callback.onFailure(value);
            }

            // Once we connect..
            public void onSuccess(Void v) {

                // Subscribe to a topic
                List<Topic> topicList = new ArrayList<>();
                for (RoomItem item : roomItems) {
                    topicList.add(item.getTopic());
                }
                Topic[] topics = topicList.toArray(new Topic[topicList.size()]);
                connection.subscribe(topics, new Callback<byte[]>() {
                    public void onSuccess(byte[] qoses) {
                        // The result of the subscribe request.
                        String sh = null;
                        try {
                            sh = new String(qoses, "US-ASCII");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Log.e("messageh", sh);
                    }

                    public void onFailure(Throwable value) {
                        //connection.close(null); // subscribe failed.
                    }
                });
                // Send a message to a topic
            }
        });
    }
}
