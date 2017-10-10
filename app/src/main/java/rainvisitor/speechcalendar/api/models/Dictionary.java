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

    @SerializedName("Word")
    private String Word;

    @SerializedName("Flag")
    private String Flag;

    public Dictionary(String word, String flag) {
        Word = word;
        Flag = flag;
    }

    public String getWord() {
        return Word;
    }

    public void setWord(String word) {
        Word = word;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "Word='" + Word + '\'' +
                ", Flag='" + Flag + '\'' +
                '}';
    }
}
