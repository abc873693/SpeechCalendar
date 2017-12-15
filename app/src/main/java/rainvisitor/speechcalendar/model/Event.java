package rainvisitor.speechcalendar.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ray on 2017/10/5.
 */

@Entity
public class Event {

    public enum  Status {
        NORMAL ,FINISHED , ERROR
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Text")
    private String Text;

    @ColumnInfo(name = "AddTime")
    private long AddTime;

    @ColumnInfo(name = "Time")
    private long Time;

    @ColumnInfo(name = "Status")
    private int Status;

    public Event() {
    }

    public Event(String text, long addTime, long time, int status) {
        Text = text;
        AddTime = addTime;
        Time = time;
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

    public long getAddTime() {
        return AddTime;
    }

    public void setAddTime(long addTime) {
        AddTime = addTime;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
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
