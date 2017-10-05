package rainvisitor.speechcalendar.model;

/**
 * Created by Ray on 2017/10/5.
 */

public class RoomInfo extends RoomItem {

    private int CO2 = 0;
    private double Humidity = 0;
    private double Temperature = 0;
    private double Current = 0;
    private double Voltage = 0;
    private double RealTimePower = 0;
    private double AccumulateElectricity = 0;
    private double PowerFactor = 0;

    public RoomInfo(String title, org.fusesource.mqtt.client.Topic topic) {
        super(title, topic);
    }

    public void setRoomInfo(RoomInfoResponse roomInfoResponse) {
        this.CO2 = roomInfoResponse.getCO2();
        Humidity = roomInfoResponse.getHumidity();
        Temperature = roomInfoResponse.getTemperature();
        Current = roomInfoResponse.getCurrent();
        Voltage = roomInfoResponse.getVoltage();
        RealTimePower = roomInfoResponse.getRealTimePower();
        AccumulateElectricity = roomInfoResponse.getAccumulateElectricity();
        PowerFactor = roomInfoResponse.getPowerFactor();
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

    @Override
    public String toString() {
        return "RoomInfo{" +
                "CO2=" + CO2 +
                ", Humidity=" + Humidity +
                ", Temperature=" + Temperature +
                ", Current=" + Current +
                ", Voltage=" + Voltage +
                ", RealTimePower=" + RealTimePower +
                ", AccumulateElectricity=" + AccumulateElectricity +
                ", PowerFactor=" + PowerFactor +
                '}';
    }
}
