package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/10/5.
 */

public class LightDimmingResponse {

    @SerializedName("DimmingLight")
    private int Step;

    public LightDimmingResponse() {
    }

    public int getStep() {
        return Step;
    }

    public void setStep(int step) {
        Step = step;
    }

}
