package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/10/5.
 */

public class LightDimming extends RoomItem {

    private int value = 0;

    public LightDimming(String title, org.fusesource.mqtt.client.Topic topic) {
        super(title, topic);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setStep(LightDimmingResponse response) {
        //this.value = response.;
    }

    public int getStep() {
        if (value == 0) return 0;
        if (value <= 27000) return 1;
        if (value <= 28000) return 2;
        if (value <= 29000) return 3;
        if (value <= 30000) return 4;
        return 5;
    }

    public void setStep(SensorResponse response) {
        value = response.getStep();
    }

    @Override
    public String toString() {
        return "LightDimming{" +
                "value=" + value +
                '}';
    }
}
