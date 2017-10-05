package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/9/23.
 */

public class DBItem {

    private int ID;
    private String Text;
    private long AddTime;

    public DBItem() {
    }

    public DBItem(int ID, String text, long addTime) {
        this.ID = ID;
        Text = text;
        AddTime = addTime;
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

    @Override
    public String toString() {
        return "DBItem{" +
                "ID=" + ID +
                ", Text='" + Text + '\'' +
                ", AddTime=" + AddTime +
                '}';
    }
}
