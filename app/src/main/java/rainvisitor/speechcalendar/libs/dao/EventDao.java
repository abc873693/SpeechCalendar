package rainvisitor.speechcalendar.libs.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import rainvisitor.speechcalendar.model.Event;

/**
 * Created by Ray on 2017/11/30.
 */

@Dao
public interface EventDao {

    @Query("SELECT * FROM Event")
    List<Event> getAll();

    @Query("SELECT * FROM Event WHERE ID IN (:userIds)")
    List<Event> getAllByIds(int[] userIds);

    @Query("SELECT * FROM Event Where ID IN (:id)")
    Event getByID(int id);

   /* @Query("SELECT * FROM Event Where Date IN (:date)")
    List<Event> getByDate(String date);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Event... Events);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Event> Events);

    @Delete
    void delete(Event Event);

    @Delete
    void delete(List<Event> EventList);
}

