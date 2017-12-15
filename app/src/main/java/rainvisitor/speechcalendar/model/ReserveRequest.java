package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

import rainvisitor.speechcalendar.libs.Utils;

/**
 * Created by Ray on 2017/12/12.
 * {"ID": "id",
 "CMD": "cmd",
 "Date": "Date" }
 Date格式："YYYY-MM-DD HH:mm:ss"
 */

public class ReserveRequest {

    @SerializedName("ID")
    private String ID ;

    @SerializedName("CMD")
    private String CMD ;

    @SerializedName("Date")
    private String Date ;

    public ReserveRequest(Event event) {
        ID = event.getID()+"";
        Date = Utils.ConvertTime(event.getTime());

    }

    public String getID() {
        return ID;
    }

    public int getIntegerID() {
        return Integer.parseInt(ID);
    }

    public void setID(String ID) {
        this.ID = ID;
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
}
