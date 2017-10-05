package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/10/5.
 */

public class TVResponse {

    @SerializedName("TV_Status")
    private int Power;

    public TVResponse() {
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
