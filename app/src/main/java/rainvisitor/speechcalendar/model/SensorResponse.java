package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/10/6.
 */

public class SensorResponse {

    @SerializedName("CO2")
    private int CO2 ;

    @SerializedName("HR")
    private int Humidity ;

    @SerializedName("Temp")
    private int Temperature ;

    @SerializedName("I")
    private String Current ;

    @SerializedName("V")
    private String Voltage ;

    @SerializedName("kW")
    private String RealTimePower ;

    @SerializedName("kWh")
    private String AccumulateElectricity ;

    @SerializedName("PF")
    private String PowerFactor ;

    @SerializedName("DimmingLight")
    private int Step;

    @SerializedName("BattenLighting")
    private int SwitchPower;

    @SerializedName("TV_Status")
    private int TvPower;

    @SerializedName("ac_Status")
    private int AirConditionerPower;

    public SensorResponse() {
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getHumidity() {
        return Humidity;
    }

    public void setHumidity(int humidity) {
        Humidity = humidity;
    }

    public int getTemperature() {
        return Temperature;
    }

    public void setTemperature(int temperature) {
        Temperature = temperature;
    }

    public String getCurrent() {
        return Current;
    }

    public void setCurrent(String current) {
        Current = current;
    }

    public String getVoltage() {
        return Voltage;
    }

    public void setVoltage(String voltage) {
        Voltage = voltage;
    }

    public String getRealTimePower() {
        return RealTimePower;
    }

    public void setRealTimePower(String realTimePower) {
        RealTimePower = realTimePower;
    }

    public String getAccumulateElectricity() {
        return AccumulateElectricity;
    }

    public void setAccumulateElectricity(String accumulateElectricity) {
        AccumulateElectricity = accumulateElectricity;
    }

    public String getPowerFactor() {
        return PowerFactor;
    }

    public void setPowerFactor(String powerFactor) {
        PowerFactor = powerFactor;
    }

    public int getStep() {
        return Step;
    }

    public void setStep(int step) {
        Step = step;
    }

    public int getSwitchPower() {
        return SwitchPower;
    }

    public void setSwitchPower(int switchPower) {
        SwitchPower = switchPower;
    }

    public int getTvPower() {
        return TvPower;
    }

    public void setTvPower(int tvPower) {
        TvPower = tvPower;
    }

    public int getAirConditionerPower() {
        return AirConditionerPower;
    }

    public void setAirConditionerPower(int airConditionerPower) {
        AirConditionerPower = airConditionerPower;
    }

    @Override
    public String toString() {
        return "SensorResponse{" +
                "CO2=" + CO2 +
                ", Humidity=" + Humidity +
                ", Temperature=" + Temperature +
                ", Current='" + Current + '\'' +
                ", Voltage='" + Voltage + '\'' +
                ", RealTimePower='" + RealTimePower + '\'' +
                ", AccumulateElectricity='" + AccumulateElectricity + '\'' +
                ", PowerFactor='" + PowerFactor + '\'' +
                ", Step=" + Step +
                ", SwitchPower=" + SwitchPower +
                ", TvPower=" + TvPower +
                ", AirConditionerPower=" + AirConditionerPower +
                '}';
    }
}
