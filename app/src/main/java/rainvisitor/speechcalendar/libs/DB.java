package rainvisitor.speechcalendar.libs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import rainvisitor.speechcalendar.model.DBItem;

/**
 * Created by Ray on 2017/9/22.
 */

public class DB {

    public static final String CHAT_TABLE_NAME = "Chat";
    public static final String EVENT_TABLE_NAME = "Event";

    public static final String ACCESS_TOKEN = "AccessToken";

    public static final String ID  = "ID";
    public static final String TEXT  = "Text";
    public static final String ADD_TIME  = "AddTime";
    public static final String TIME  = "Time";
    public static final String STATUS  = "Status";

    public static final String CREATE_CHAT_TABLE =
            "CREATE TABLE " + CHAT_TABLE_NAME + " (" +
                    ID + " INTEGER NOT NULL, " +
                    TEXT + " TEXT NOT NULL, " +
                    ADD_TIME + " INTEGER NOT NULL)";

    public static final String CREATE_EVENT_TABLE =
            "CREATE TABLE " + EVENT_TABLE_NAME + " (" +
                    ID + " INTEGER NOT NULL, " +
                    TEXT + " TEXT NOT NULL, " +
                    ADD_TIME + " INTEGER NOT NULL, " +
                    TIME + " INTEGER NOT NULL, " +
                    STATUS + " INTEGER NOT NULL)";

    // 資料庫物件
    private SQLiteDatabase db;

    // 建構子，一般的應用都不需要修改
    public DB(Context context) {
        db = DBHelper.getDatabase(context);
    }

    // 關閉資料庫，一般的應用都不需要修改
    public void close() {
        db.close();
    }

    public DBItem insert(String TABLE_NAME, DBItem item) {
        // 建立準備新增資料的ContentValues物件
        ContentValues cv = new ContentValues();
        // 加入ContentValues物件包裝的新增資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        switch (TABLE_NAME) {
            case CHAT_TABLE_NAME:
                cv.put(ID, item.getID());
                cv.put(TEXT, item.getText());
                cv.put(ADD_TIME, item.getAddTime());
                break;
            case EVENT_TABLE_NAME:
                cv.put(ID, item.getID());
                cv.put(TEXT, item.getText());
                cv.put(ADD_TIME, item.getAddTime());
                cv.put(TIME, item.getTime());
                cv.put(STATUS, item.getStatus());
                break;
        }
        // 新增一筆資料並取得編號
        // 第一個參數是表格名稱
        // 第二個參數是沒有指定欄位值的預設值
        // 第三個參數是包裝新增資料的ContentValues物件
        db.insert(TABLE_NAME, null, cv);

        // 回傳結果
        return item;
    }

    // 修改參數指定的物件
    public boolean update(String TABLE_NAME, DBItem item) {
        // 建立準備修改資料的ContentValues物件
        ContentValues cv = new ContentValues();

        // 加入ContentValues物件包裝的修改資料
        // 第一個參數是欄位名稱， 第二個參數是欄位的資料
        switch (TABLE_NAME) {
            case CHAT_TABLE_NAME:
                break;
            case EVENT_TABLE_NAME:
                break;
        }

        // 設定修改資料的條件為編號
        // 格式為「欄位名稱＝資料」
        String where = null;
        switch (TABLE_NAME) {
            case CHAT_TABLE_NAME:
                break;
            case EVENT_TABLE_NAME:
                break;
        }
        // 執行修改資料並回傳修改的資料數量是否成功
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // 刪除參數指定編號的資料
    public boolean deleteAll(String TABLE_NAME) {
        // 設定條件為編號，格式為「欄位名稱=資料」
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, null, null) > 0;
    }

    // 讀取所有記事資料
    public  List<DBItem> getAll(String TABLE_NAME) {
        List<DBItem> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(TABLE_NAME, cursor));
        }
        cursor.close();
        return result;
    }

    // 把Cursor目前的資料包裝為物件
    public  DBItem getRecord(String TABLE_NAME, Cursor cursor) {
        // 準備回傳結果用的物件
        DBItem result = new DBItem();
        switch (TABLE_NAME) {
            case CHAT_TABLE_NAME:
                result.setID(cursor.getInt(0));
                result.setText(cursor.getString(1));
                result.setAddTime(cursor.getLong(2));
                break;
            case EVENT_TABLE_NAME:
                result.setID(cursor.getInt(0));
                result.setText(cursor.getString(1));
                result.setAddTime(cursor.getLong(2));
                result.setTime(cursor.getLong(3));
                result.setStatus(cursor.getInt(4));
                break;
        }

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount(String TABLE_NAME) {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }

    // 取得某個時間之後的資料數量
    public int getCount(String TABLE_NAME, long Time) {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME + " Where " + ADD_TIME + ">" + Time, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
