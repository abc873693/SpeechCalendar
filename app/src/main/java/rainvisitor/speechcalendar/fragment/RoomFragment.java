package rainvisitor.speechcalendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.adapter.RoomItemAdapter;
import rainvisitor.speechcalendar.base.BaseFragment;
import rainvisitor.speechcalendar.callback.RoomCallback;
import rainvisitor.speechcalendar.libs.MQTTHelper;
import rainvisitor.speechcalendar.model.AirConditioner;
import rainvisitor.speechcalendar.model.AirConditionerResponse;
import rainvisitor.speechcalendar.model.LightDimming;
import rainvisitor.speechcalendar.model.LightDimmingResponse;
import rainvisitor.speechcalendar.model.LightSwitch;
import rainvisitor.speechcalendar.model.LightSwitchResponse;
import rainvisitor.speechcalendar.model.RoomInfo;
import rainvisitor.speechcalendar.model.RoomInfoResponse;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.TV;
import rainvisitor.speechcalendar.model.TVResponse;

public class RoomFragment extends BaseFragment {


    @BindView(R.id.recycleView_room_item)
    RecyclerView recycleViewRoomItem;
    Unbinder unbinder;

    private RoomItemAdapter roomItemAdapter;

    private List<RoomItem> roomItemList;

    private View view;

    public RoomFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_room, container, false);
        restoreArgs(savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        setView();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private void restoreArgs(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setView() {
        roomItemList = new ArrayList<>();
        roomItemList.add(new RoomInfo("即時資訊", new Topic(MQTTHelper.TOPIC_SENSOR, QoS.EXACTLY_ONCE)));
        roomItemList.add(new LightDimming("調光燈", new Topic(MQTTHelper.TOPIC_LIGHT_DIMMING, QoS.EXACTLY_ONCE)));
        roomItemList.add(new LightSwitch("層板燈", new Topic(MQTTHelper.TOPIC_LIGHT_SWITCH, QoS.EXACTLY_ONCE)));
        roomItemList.add(new TV("電視", new Topic(MQTTHelper.TOPIC_TV, QoS.EXACTLY_ONCE)));
        roomItemList.add(new AirConditioner("冷氣", new Topic(MQTTHelper.TOPIC_AIR_CONDITIONER, QoS.EXACTLY_ONCE)));
        roomItemAdapter = new RoomItemAdapter(getActivity(), roomItemList, new RoomItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RoomItem item, int position) {

            }
        });
        recycleViewRoomItem.setAdapter(roomItemAdapter);
        recycleViewRoomItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        MQTTHelper.subscribe(getActivity(), roomItemList, new RoomCallback() {
            @Override
            public void onError() {
                super.onError();
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
            }

            @Override
            public void onFailure(Exception e) {
                super.onFailure(e);
            }

            @Override
            public void onRoomInfo(RoomInfoResponse response, int position) {
                super.onRoomInfo(response, position);
                ((RoomInfo) roomItemList.get(position)).setRoomInfo(response);
                roomItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLightDimming(LightDimmingResponse response, int position) {
                super.onLightDimming(response, position);
                ((LightDimming) roomItemList.get(position)).setStep(response);
                roomItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLightSwitch(LightSwitchResponse response, int position) {
                super.onLightSwitch(response, position);
                ((LightSwitch) roomItemList.get(position)).setPower(response);
                roomItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTv(TVResponse response, int position) {
                super.onTv(response, position);
                ((TV) roomItemList.get(position)).setPower(response);
                roomItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onAirConditioner(AirConditionerResponse response, int position) {
                super.onAirConditioner(response, position);
                ((AirConditioner) roomItemList.get(position)).setPower(response);
                roomItemAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
