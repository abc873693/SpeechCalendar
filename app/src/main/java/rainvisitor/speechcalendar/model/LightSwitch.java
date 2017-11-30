package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/10/5.
 */

public class LightSwitch extends RoomItem {

    private boolean power = false;

    public LightSwitch(String title, org.fusesource.mqtt.client.Topic topic) {
        super(title, topic);
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public void setPower(LightSwitchResponse response) {

    }

    public void setPower(SensorResponse response) {
        power = (response.getSwitchPower() == 1);
    }

    @Override
    public String toString() {
        return "LightSwitch{" +
                "power=" + power +
                '}';
    }
}
