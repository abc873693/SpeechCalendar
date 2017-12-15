package rainvisitor.speechcalendar.libs;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import rainvisitor.speechcalendar.libs.dao.EventDao;
import rainvisitor.speechcalendar.libs.dao.MessageDao;
import rainvisitor.speechcalendar.model.Event;
import rainvisitor.speechcalendar.model.Message;

/**
 * Created by Ray on 2017/10/31.
 */

@Database(entities = {
        Event.class,
        Message.class,
},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract MessageDao messageDao();
}