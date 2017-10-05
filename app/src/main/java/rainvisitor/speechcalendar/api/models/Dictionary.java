package rainvisitor.speechcalendar.api.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/9/22.
 */

public class Dictionary {

    public static int UNKNOWN = 1;
    public static int PEOPLE = 1;
    public static int THINGS = 2;
    public static int TIME = 3;
    public static int PLACE = 4;
    public static int ITEM = 5;

    @SerializedName("ID")
    private int ID;

    @SerializedName("Word")
    private String Word;

    @SerializedName("Part")
    private int Part;

    public Dictionary(int ID, String word, int part) {
        this.ID = ID;
        Word = word;
        Part = part;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public int getPart() {
        return Part;
    }

    public void setPart(int part) {
        Part = part;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "ID=" + ID +
                ", Word='" + Word + '\'' +
                ", Part=" + Part +
                '}';
    }
}
