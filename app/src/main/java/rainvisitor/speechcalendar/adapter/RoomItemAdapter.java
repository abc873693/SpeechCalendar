package rainvisitor.speechcalendar.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import rainvisitor.speechcalendar.R;
import rainvisitor.speechcalendar.libs.MQTTHelper;
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

    private static Boolean isTouched = false;

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
            ((RoomInfoViewHolder) holder).textViewHumidity.setText(((RoomInfo) contactList.get(position)).getHumidity() / 100 + " %");
            ((RoomInfoViewHolder) holder).textViewTemperature.setText(((RoomInfo) contactList.get(position)).getTemperature() / 100 + " °C");
            ((RoomInfoViewHolder) holder).textViewCurrent.setText(((RoomInfo) contactList.get(position)).getCurrent() + " A");
            ((RoomInfoViewHolder) holder).textViewVoltage.setText(((RoomInfo) contactList.get(position)).getVoltage() + " V");
            ((RoomInfoViewHolder) holder).textViewRealTimePower.setText(((RoomInfo) contactList.get(position)).getRealTimePower() + " 瓩");
            ((RoomInfoViewHolder) holder).textViewAccumulateElectricity.setText(((RoomInfo) contactList.get(position)).getAccumulateElectricity() + " 度");
            ((RoomInfoViewHolder) holder).textViewPowerFactor.setText(((RoomInfo) contactList.get(position)).getPowerFactor() + " 瓦");
        } else if (holder instanceof LightDimmingViewHolder) {
            ((LightDimmingViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((LightDimmingViewHolder) holder).seekBar.setProgress(((LightDimming) contactList.get(position)).getStep());
            ((LightDimmingViewHolder) holder).seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    MQTTHelper.publish(context, MQTTHelper.TOPIC_LIGHT_SWITCH, seekBar.getProgress() + "");
                }
            });
        } else if (holder instanceof LightSwitchViewHolder) {
            ((LightSwitchViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((LightSwitchViewHolder) holder).switchControl.setChecked(((LightSwitch) contactList.get(position)).isPower());
            ((LightSwitchViewHolder) holder).switchControl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    isTouched = true;
                    return false;
                }
            });
            ((LightSwitchViewHolder) holder).switchControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (isTouched) {
                        isTouched = false;
                        String content = b ? "ON" : "OFF";
                        MQTTHelper.publish(context, MQTTHelper.TOPIC_LIGHT_SWITCH, content);
                    }
                }
            });
        } else if (holder instanceof TvViewHolder) {
            ((TvViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((TvViewHolder) holder).switchControl.setChecked(((TV) contactList.get(position)).isPower());
            ((TvViewHolder) holder).switchControl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    isTouched = true;
                    return false;
                }
            });
            ((TvViewHolder) holder).switchControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (isTouched) {
                        isTouched = false;
                        String content = b ? "ON" : "OFF";
                        MQTTHelper.publish(context, MQTTHelper.TOPIC_TV, content);
                    }
                }
            });
        } else if (holder instanceof AirConditionerViewHolder) {
            ((AirConditionerViewHolder) holder).textViewTitle.setText(contactList.get(position).getTitle());
            ((AirConditionerViewHolder) holder).switchControl.setChecked(((AirConditioner) contactList.get(position)).isPower());
            ((AirConditionerViewHolder) holder).switchControl.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    isTouched = true;
                    return false;
                }
            });
            ((AirConditionerViewHolder) holder).switchControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (isTouched) {
                        isTouched = false;
                        String content = b ? "ON" : "OFF";
                        MQTTHelper.publish(context, MQTTHelper.TOPIC_AIR_CONDITIONER, content);
                    }
                }
            });
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
