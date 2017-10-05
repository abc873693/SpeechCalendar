package rainvisitor.speechcalendar.model;

import org.fusesource.mqtt.client.Topic;

import java.io.UnsupportedEncodingException;

/**
 * Created by Ray on 2017/10/5.
 */

public class RoomItem {

    private String Title;
    private Topic Topic;

    public RoomItem(String title) {
        Title = title;
    }

    public RoomItem(String title, org.fusesource.mqtt.client.Topic topic) {
        Title = title;
        Topic = topic;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public org.fusesource.mqtt.client.Topic getTopic() {
        return Topic;
    }

    public void setTopic(org.fusesource.mqtt.client.Topic topic) {
        Topic = topic;
    }

    public String getTopicText(){
        try {
            return new String(Topic.name().getData(), "UTF8");
        } catch (UnsupportedEncodingException e) {
            return "error";
        }
    }
}
