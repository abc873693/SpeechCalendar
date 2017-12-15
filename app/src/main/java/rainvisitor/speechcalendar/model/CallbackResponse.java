package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/12/12.
 * EX:{"ID":"1","Device":"TV","CMD":"ON","Date":"2017-12-12 00:41:00"}
 */

public class CallbackResponse {

    @SerializedName("ID")
    private String ID;

    @SerializedName("Device")
    private String Device;

    @SerializedName("CMD")
    private String CMD;

    @SerializedName("Date")
    private String Date;

    public CallbackResponse() {
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public String getCMD() {
        return CMD;
    }

    public void setCMD(String CMD) {
        this.CMD = CMD;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "CallbackResponse{" +
                "ID='" + ID + '\'' +
                ", Device='" + Device + '\'' +
                ", CMD='" + CMD + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
