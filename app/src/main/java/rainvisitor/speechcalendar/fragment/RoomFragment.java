package rainvisitor.speechcalendar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fusesource.mqtt.client.Callback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.adapter.RoomItemAdapter;
import rainvisitor.speechcalendar.base.BaseFragment;
import rainvisitor.speechcalendar.callback.GeneralCallback;
import rainvisitor.speechcalendar.callback.RoomCallback;
import rainvisitor.speechcalendar.libs.MQTTHelper;
import rainvisitor.speechcalendar.model.AirConditioner;
import rainvisitor.speechcalendar.model.LightDimming;
import rainvisitor.speechcalendar.model.LightSwitch;
import rainvisitor.speechcalendar.model.RoomInfo;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.SensorResponse;
import rainvisitor.speechcalendar.model.TV;

public class RoomFragment extends BaseFragment {


    @BindView(R.id.recycleView_room_item)
    RecyclerView recycleViewRoomItem;
    Unbinder unbinder;

    private RoomItemAdapter roomItemAdapter;

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
        roomItemAdapter = new RoomItemAdapter(getActivity(), getRoomItemList(), new RoomItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RoomItem item, int position) {

            }
        });
        recycleViewRoomItem.setAdapter(roomItemAdapter);
        recycleViewRoomItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        MQTTHelper.addCallback(new GeneralCallback() {
            @Override
            public void onSuccess() {
                super.onSuccess();
                roomItemAdapter.notifyDataSetChanged();
            }
        });
        MQTTHelper.subscribe(getActivity(), getRoomItemList(), new RoomCallback() {
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
            public void onResponse(SensorResponse response) {
                super.onResponse(response);
                ((RoomInfo) getRoomItemList().get(0)).setRoomInfo(response);
                ((LightDimming) getRoomItemList().get(1)).setStep(response);
                ((LightSwitch) getRoomItemList().get(2)).setPower(response);
                ((TV) getRoomItemList().get(3)).setPower(response);
                ((AirConditioner) getRoomItemList().get(4)).setPower(response);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        MQTTHelper.connection.disconnect(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {

            }

            @Override
            public void onFailure(Throwable value) {

            }
        });
    }
}
