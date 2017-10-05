package rainvisitor.speechcalendar.callback;

import rainvisitor.speechcalendar.model.AirConditionerResponse;
import rainvisitor.speechcalendar.model.LightDimmingResponse;
import rainvisitor.speechcalendar.model.LightSwitchResponse;
import rainvisitor.speechcalendar.model.RoomInfoResponse;
import rainvisitor.speechcalendar.model.TVResponse;

/**
 * Created by Ray on 2017/10/5.
 */

public class RoomCallback {

    public void onError() {

    }

    public void onFailure(Throwable throwable) {

    }

    public void onFailure(Exception e) {
    }

    public void onRoomInfo(RoomInfoResponse response, int position) {

    }

    public void onLightDimming(LightDimmingResponse response, int position) {

    }

    public void onLightSwitch(LightSwitchResponse response, int position) {

    }

    public void onTv(TVResponse response, int position) {

    }

    public void onAirConditioner(AirConditionerResponse response, int position) {

    }
}
