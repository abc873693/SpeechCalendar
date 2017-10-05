package rainvisitor.speechcalendar.model;

import java.util.Date;

/**
 * Created by Ray on 2017/10/5.
 */

public class Event {

    public enum  Status {
        NORMAL ,FINISHED , ERROR
    }

    private int ID;
    private String Text;
    private Date AddTime;
    private Date Time;
    private int Status;

    public Event(int ID, String text, Date addTime, Date time, int status) {
        this.ID = ID;
        Text = text;
        AddTime = addTime;
        this.Time = time;
        Status = status;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public Date getAddTime() {
        return AddTime;
    }

    public void setAddTime(Date addTime) {
        AddTime = addTime;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    @Override
    public String toString() {
        return "Event{" +
                "ID='" + ID + '\'' +
                ", Text='" + Text + '\'' +
                ", AddTime=" + AddTime +
                ", Time=" + Time +
                ", Status=" + Status +
                '}';
    }
}
