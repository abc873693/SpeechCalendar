package rainvisitor.speechcalendar.libs.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import rainvisitor.speechcalendar.model.Message;

/**
 * Created by Ray on 2017/11/30.
 */

@Dao
public interface MessageDao {

    @Query("SELECT * FROM Message")
    List<Message> getAll();

    @Query("SELECT * FROM Message WHERE ID IN (:userIds)")
    List<Message> getAllByIds(int[] userIds);

    @Query("SELECT * FROM Message Where ID IN (:id)")
    Message getByID(int id);

   /* @Query("SELECT * FROM Message Where Date IN (:date)")
    List<Message> getByDate(String date);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Message... messages);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Message> messages);

    @Delete
    void delete(Message Message);

    @Delete
    void delete(List<Message> MessageList);
}

