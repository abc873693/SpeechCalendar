package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/10/5.
 */

public class LightDimming extends RoomItem {

    private int step = 0;

    public LightDimming(String title, org.fusesource.mqtt.client.Topic topic) {
        super(title, topic);
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void setStep(LightDimmingResponse response) {
        //this.step = response.;
    }

    @Override
    public String toString() {
        return "LightDimming{" +
                "step=" + step +
                '}';
    }
}
