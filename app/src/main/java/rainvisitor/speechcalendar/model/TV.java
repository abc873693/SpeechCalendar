package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/10/5.
 */

public class TV extends RoomItem {

    private boolean power = false;

    public TV(String title, org.fusesource.mqtt.client.Topic topic) {
        super(title, topic);
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }
    public void setPower(TVResponse response) {

    }

    @Override
    public String toString() {
        return "TV{" +
                "power=" + power +
                '}';
    }
}
