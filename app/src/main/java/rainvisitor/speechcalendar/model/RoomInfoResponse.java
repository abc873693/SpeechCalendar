package rainvisitor.speechcalendar.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ray on 2017/10/5.
 */

public class RoomInfoResponse {

    @SerializedName("CO2")
    private int CO2 ;

    @SerializedName("HR")
    private double Humidity ;

    @SerializedName("Temp")
    private double Temperature ;

    @SerializedName("I")
    private double Current ;

    @SerializedName("V")
    private double Voltage ;

    @SerializedName("kW")
    private double RealTimePower ;

    @SerializedName("kWh")
    private double AccumulateElectricity ;

    @SerializedName("PF")
    private double PowerFactor ;

    public RoomInfoResponse() {
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public double getHumidity() {
        return Humidity;
    }

    public void setHumidity(double humidity) {
        Humidity = humidity;
    }

    public double getTemperature() {
        return Temperature;
    }

    public void setTemperature(double temperature) {
        Temperature = temperature;
    }

    public double getCurrent() {
        return Current;
    }

    public void setCurrent(double current) {
        Current = current;
    }

    public double getVoltage() {
        return Voltage;
    }

    public void setVoltage(double voltage) {
        Voltage = voltage;
    }

    public double getRealTimePower() {
        return RealTimePower;
    }

    public void setRealTimePower(double realTimePower) {
        RealTimePower = realTimePower;
    }

    public double getAccumulateElectricity() {
        return AccumulateElectricity;
    }

    public void setAccumulateElectricity(double accumulateElectricity) {
        AccumulateElectricity = accumulateElectricity;
    }

    public double getPowerFactor() {
        return PowerFactor;
    }

    public void setPowerFactor(double powerFactor) {
        PowerFactor = powerFactor;
    }
}
