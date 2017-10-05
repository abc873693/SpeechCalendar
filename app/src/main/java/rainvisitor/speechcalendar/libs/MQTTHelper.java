package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import rainvisitor.speechcalendar.callback.RoomCallback;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.SensorResponse;

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
                        if (finalTopicText.contains(TOPIC_SENSOR)) {
                            final SensorResponse roomInfoResponse = new Gson().fromJson(content, SensorResponse.class);
                            callback.onResponse(roomInfoResponse);
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

    public static void publish(final Context context, String topic, String content) {
        connection.publish(topic, content.getBytes(), QoS.AT_LEAST_ONCE, false, new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable value) {
                Toast.makeText(context, "失敗", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
