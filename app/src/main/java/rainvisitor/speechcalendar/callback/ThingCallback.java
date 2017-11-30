package rainvisitor.speechcalendar.callback;

import java.util.Date;
import java.util.List;

/**
 * Created by Ray on 2017/9/22.
 */

public class ThingCallback {

    public void unKnownCommand(List<String> commands) {

    }

    public void needVoice(Boolean flagThing, Boolean flagTime) {

    }

    public void createCalender(String title, Date startDate, Date endDate, String location) {

    }

    public void hardwareControl(String name, int value) {

    }

    public void hardwareStroke(String name, int value, Date time) {

    }
}
