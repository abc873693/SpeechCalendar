package rainvisitor.speechcalendar.libs;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rainvisitor.speechcalendar.model.DictionariesResponse;
import rainvisitor.speechcalendar.callback.DictionariesCallback;

/**
 * Created by Ray on 2017/9/21.
 */

public class Helper {

    //內部
    private static final String HOST = "192.168.200.50";
    private static final String PORT = "81";
    /*//外部
    private static final String HOST = "pj.icp-si.com";
    private static final String PORT = "64503";*/

    private static final String API_DICTIONARIES = "/api/Dictionaries";

    public static void get(final android.content.Context context, final String word, final DictionariesCallback dictionariesCallback) {
        String URL = "http://" + HOST + ":" + PORT + API_DICTIONARIES + "?Type=0&Word=" + word;
        OkHttpClient mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(URL)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dictionariesCallback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final DictionariesResponse appContext_response = new Gson().fromJson(response.body().string(), DictionariesResponse.class);
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dictionariesCallback.onSuccess(word, appContext_response.getData());
                    }
                });
            }
        });
    }
}
