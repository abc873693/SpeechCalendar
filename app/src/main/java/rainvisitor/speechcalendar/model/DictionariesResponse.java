package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ray on 2017/9/22.
 */

public class DictionariesResponse {

    @SerializedName("StatusCode")
    private int StatusCode;

    @SerializedName("ResponseMessage")
    private String Message;

    @SerializedName("JiebaData")
    private List<Dictionary> Data;

    public DictionariesResponse() {
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public List<Dictionary> getData() {
        return Data;
    }

    public void setData(List<Dictionary> data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "DictionariesResponse{" +
                "StatusCode=" + StatusCode +
                ", Message='" + Message + '\'' +
                ", Data=" + Data +
                '}';
    }
}
