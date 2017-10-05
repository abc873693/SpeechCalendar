package rainvisitor.speechcalendar.base;

import android.app.Application;

import rainvisitor.speechcalendar.libs.DB;
import rainvisitor.speechcalendar.libs.MQTTHelper;

/**
 * Created by Ray on 2017/9/23.
 */

public class BaseApplication extends Application {

    private DB db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DB(getApplicationContext());
        MQTTHelper.init();
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
