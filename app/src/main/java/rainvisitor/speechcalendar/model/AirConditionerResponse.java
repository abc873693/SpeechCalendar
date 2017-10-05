package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/10/5.
 */

public class AirConditionerResponse {

    @SerializedName("ac_Status")
    private int Power;

    public AirConditionerResponse() {
    }

    public int getPower() {
        return Power;
    }

    public void setPower(int power) {
        Power = power;
    }

    public boolean getPowerBool() {
        return Power == 1;
    }
}
