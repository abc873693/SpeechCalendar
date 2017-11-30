package rainvisitor.speechcalendar.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

/**
 * Created by Ray on 2017/3/1.
 */

@Entity
public class Message implements IMessage {

    /*...*/
    //because Message implements so set SerialID to PrimaryKey
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "SerialID")
    private int SerialID;

    //ID == userID
    @ColumnInfo(name = "ID")
    private String id;

    @ColumnInfo(name = "text")
    private String text;

    @Ignore
    private Author author;

    @ColumnInfo(name = "CreateTime")
    private long CreateTime;

    public Message(String id, String text, Author author, long CreateTime) {
        super();
        this.id = id;
        this.text = text;
        this.author = author;
        this.CreateTime = CreateTime;
    }

    public Message(String id, String text, long CreateTime) {
        this.id = id;
        this.text = text;
        this.CreateTime = CreateTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return new Date(CreateTime);
    }

    public int getSerialID() {
        return SerialID;
    }

    public void setSerialID(int serialID) {
        SerialID = serialID;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "SerialID=" + SerialID +
                ", id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", author=" + author +
                ", CreateTime=" + CreateTime +
                '}';
    }
}