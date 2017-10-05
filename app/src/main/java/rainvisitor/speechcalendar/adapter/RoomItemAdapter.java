package rainvisitor.speechcalendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.model.AirConditioner;
import rainvisitor.speechcalendar.model.LightDimming;
import rainvisitor.speechcalendar.model.LightSwitch;
import rainvisitor.speechcalendar.model.RoomInfo;
import rainvisitor.speechcalendar.model.RoomItem;
import rainvisitor.speechcalendar.model.TV;

public class RoomItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private enum ITEM_TYPE {
        ITEM_TYPE_ROOM_INFO,
        ITEM_TYPE_LIGHT_SWITCH,
        ITEM_TYPE_LIGHT_DIMMING,
        ITEM_TYPE_TV,
        ITEM_TYPE_AIR_CONDITIONER
    }

    public interface OnItemClickListener {
        void onItemClick(RoomItem item, int position);
    }

    private final List<RoomItem> contactList;
    private final OnItemClickListener listener;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public RoomItemAdapter(Context context, List<RoomItem> contactList, OnItemClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (contactList.get(position) instanceof RoomInfo) {
            return ITEM_TYPE.ITEM_TYPE_ROOM_INFO.ordinal();
        } else if (contactList.get(position) instanceof LightDimming) {
            return ITEM_TYPE.ITEM_TYPE_LIGHT_DIMMING.ordinal();
        } else if (contactList.get(position) instanceof LightSwitch) {
            return ITEM_TYPE.ITEM_TYPE_LIGHT_SWITCH.ordinal();
        } else if (contactList.get(position) instanceof TV) {
            return ITEM_TYPE.ITEM_TYPE_TV.ordinal();
        } else if (contactList.get(position) instanceof AirConditioner) {
            return ITEM_TYPE.ITEM_TYPE_AIR_CONDITIONER.ordinal();
        }
        return ITEM_TYPE.ITEM_TYPE_AIR_CONDITIONER.ordinal();
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_ROOM_INFO.ordinal()) {
            return new RoomInfoViewHolder(layoutInflater.inflate(R.layout.list_room_info, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_LIGHT_DIMMING.ordinal()) {
            return new LightDimmingViewHolder(layoutInflater.inflate(R.layout.list_light_dimming, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_LIGHT_SWITCH.ordinal()) {
            return new LightSwitchViewHolder(layoutInflater.inflate(R.layout.list_light_switch, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_TV.ordinal()) {
            return new TvViewHolder(layoutInflater.inflate(R.layout.list_tv, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM_TYPE_AIR_CONDITIONER.ordinal()) {
            return new AirConditionerViewHolder(layoutInflater.inflate(R.layout.list_air_conditionter, parent, false));
        } else {
            return new RoomInfoViewHolder(layoutInflater.inflate(R.layout.list_room_info, parent, false));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof RoomInfoViewHolder) {
            ((RoomInfoViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((RoomInfoViewHolder) holder).textViewCO2.setText(((RoomInfo) contactList.get(position)).getCO2() + " ppm");
            ((RoomInfoViewHolder) holder).textViewHumidity.setText(((RoomInfo) contactList.get(position)).getCO2() + " %");
            ((RoomInfoViewHolder) holder).textViewTemperature.setText(((RoomInfo) contactList.get(position)).getCO2() + " °C");
            ((RoomInfoViewHolder) holder).textViewCurrent.setText(((RoomInfo) contactList.get(position)).getCO2() + " A");
            ((RoomInfoViewHolder) holder).textViewVoltage.setText(((RoomInfo) contactList.get(position)).getCO2() + " V");
            ((RoomInfoViewHolder) holder).textViewRealTimePower.setText(((RoomInfo) contactList.get(position)).getCO2() + " 瓩");
            ((RoomInfoViewHolder) holder).textViewAccumulateElectricity.setText(((RoomInfo) contactList.get(position)).getCO2() + " 度");
            ((RoomInfoViewHolder) holder).textViewPowerFactor.setText(((RoomInfo) contactList.get(position)).getCO2() + " 瓦");
        } else if (holder instanceof LightDimmingViewHolder) {
            ((LightDimmingViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((LightDimmingViewHolder) holder).seekBar.setProgress(((LightDimming) contactList.get(position)).getStep());
        } else if (holder instanceof LightSwitchViewHolder) {
            ((LightSwitchViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((LightSwitchViewHolder) holder).switchControl.setChecked(((LightSwitch) contactList.get(position)).isPower());
        } else if (holder instanceof TvViewHolder) {
            ((TvViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((TvViewHolder) holder).switchControl.setChecked(((TV) contactList.get(position)).isPower());
        } else if (holder instanceof AirConditionerViewHolder) {
            ((AirConditionerViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((AirConditionerViewHolder) holder).switchControl.setChecked(((AirConditioner) contactList.get(position)).isPower());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(contactList.get(position), position);
            }
        });
    }

    private class RoomInfoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewCO2;
        TextView textViewHumidity;
        TextView textViewTemperature;
        TextView textViewCurrent;
        TextView textViewVoltage;
        TextView textViewRealTimePower;
        TextView textViewAccumulateElectricity;
        TextView textViewPowerFactor;

        View mView;

        RoomInfoViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewTitle = itemLayoutView.findViewById(R.id.textView_title);
            textViewCO2 = itemLayoutView.findViewById(R.id.textView_CO2);
            textViewHumidity = itemLayoutView.findViewById(R.id.textView_humidity);
            textViewTemperature = itemLayoutView.findViewById(R.id.textView_temperature);
            textViewCurrent = itemLayoutView.findViewById(R.id.textView_current);
            textViewVoltage = itemLayoutView.findViewById(R.id.textView_voltage);
            textViewRealTimePower = itemLayoutView.findViewById(R.id.textView_real_time_power);
            textViewAccumulateElectricity = itemLayoutView.findViewById(R.id.textView_accumulate_electricity);
            textViewPowerFactor = itemLayoutView.findViewById(R.id.textView_power_factor);
            mView = itemLayoutView;
        }
    }

    private class LightDimmingViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        SeekBar seekBar;
        View mView;

        LightDimmingViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewTitle = itemLayoutView.findViewById(R.id.textView_title);
            seekBar = itemLayoutView.findViewById(R.id.seekBar_dimming);
            mView = itemLayoutView;
        }
    }

    private class LightSwitchViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        SwitchCompat switchControl;
        View mView;

        LightSwitchViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewTitle = itemLayoutView.findViewById(R.id.textView_title);
            switchControl = itemLayoutView.findViewById(R.id.switch_control);
            mView = itemLayoutView;
        }
    }

    private class TvViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        SwitchCompat switchControl;
        View mView;

        TvViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewTitle = itemLayoutView.findViewById(R.id.textView_title);
            switchControl = itemLayoutView.findViewById(R.id.switch_control);
            mView = itemLayoutView;
        }
    }

    private class AirConditionerViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        SwitchCompat switchControl;
        View mView;

        AirConditionerViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            textViewTitle = itemLayoutView.findViewById(R.id.textView_title);
            switchControl = itemLayoutView.findViewById(R.id.switch_control);
            mView = itemLayoutView;
        }
    }
}
