package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/10/5.
 */

public class AirConditioner extends RoomItem {

    private boolean power = false;

    public AirConditioner(String title, org.fusesource.mqtt.client.Topic topic) {
        super(title, topic);
    }

    public boolean isPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public void setPower(AirConditionerResponse response) {

    }

    public void setPower(SensorResponse response) {
        power = response.getSwitchPower() == 1;
    }

    @Override
    public String toString() {
        return "AirConditioner{" +
                "power=" + power +
                '}';
    }
}
