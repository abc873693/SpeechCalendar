package rainvisitor.speechcalendar.libs;

import java.util.ArrayList;
import java.util.List;

import rainvisitor.speechcalendar.api.models.Dictionary;
import rainvisitor.speechcalendar.callback.ThingCallback;

/**
 * Created by Ray on 2017/9/22.
 */

public class ThingHelper {

    public static void send(List<String> Words, List<Dictionary> dictionaryList, ThingCallback thingCallback) {
        List<Dictionary> peopleList = new ArrayList<>();
        List<Dictionary> thingList = new ArrayList<>();
        List<Dictionary> timeList = new ArrayList<>();
        List<Dictionary> placeList = new ArrayList<>();
        List<Dictionary> itemList = new ArrayList<>();

        for (Dictionary dictionary : dictionaryList) {
            if (Dictionary.PEOPLE == dictionary.getID())
                peopleList.add(dictionary);
            else if (Dictionary.THINGS == dictionary.getID())
                thingList.add(dictionary);
            else if (Dictionary.TIME == dictionary.getID())
                timeList.add(dictionary);
            else if (Dictionary.PLACE == dictionary.getID())
                placeList.add(dictionary);
            else if (Dictionary.ITEM == dictionary.getID())
                itemList.add(dictionary);
        }
        while (true) {

        }
    }
}
