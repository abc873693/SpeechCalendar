package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/9/23.
 */

public class DBItem {

    private int ID;
    private String Text;
    private long AddTime;

    private int Status;
    private long Time;

    public DBItem() {
    }

    public DBItem(int ID, String text, long addTime) {
        this.ID = ID;
        Text = text;
        AddTime = addTime;
    }

    public DBItem(int ID, String text, long addTime, long time, int status) {
        this.ID = ID;
        Text = text;
        AddTime = addTime;
        Status = status;
        Time = time;
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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    @Override
    public String toString() {
        return "DBItem{" +
                "ID=" + ID +
                ", Text='" + Text + '\'' +
                ", AddTime=" + AddTime +
                ", Status=" + Status +
                ", Time=" + Time +
                '}';
    }
}
