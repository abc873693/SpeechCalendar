package rainvisitor.speechcalendar.callback;

import rainvisitor.speechcalendar.model.CallbackResponse;

/**
 * Created by Ray on 2017/12/5.
 */

public abstract class BackgroundCallback {

    public abstract void onError();

    public abstract void onFailure(Throwable throwable);

    public abstract void onFailure(Exception e);

    public abstract void onResponse(CallbackResponse response);
}