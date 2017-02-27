package com.aprbrother.asensor;

public class ASensor {
    private String name;
    private String mac;
    private long time;
    private int temperature;
    private int motionState;
    private int accelerationX;
    private int accelerationY;
    private int accelerationZ;
    private int battery;
    private int measurepower;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMotionState() {
        return motionState;
    }

    public void setMotionState(int motionState) {
        this.motionState = motionState;
    }

    public int getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(int accelerationX) {
        this.accelerationX = accelerationX;
    }

    public int getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(int accelerationY) {
        this.accelerationY = accelerationY;
    }

    public int getAccelerationZ() {
        return accelerationZ;
    }

    public void setAccelerationZ(int accelerationZ) {
        this.accelerationZ = accelerationZ;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getMeasurepower() {
        return measurepower;
    }

    public void setMeasurepower(int measurepower) {
        this.measurepower = measurepower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ASensor)) return false;
        ASensor aSensor = (ASensor) o;
        return mac != null ? mac.equals(aSensor.mac) : aSensor.mac == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (mac != null ? mac.hashCode() : 0);
        result = 31 * result + temperature;
        result = 31 * result + motionState;
        result = 31 * result + accelerationX;
        result = 31 * result + accelerationY;
        result = 31 * result + accelerationZ;
        result = 31 * result + battery;
        result = 31 * result + measurepower;
        return result;
    }

    @Override
    public String toString() {
        return "ASensor{" +
                "name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                ", time=" + time +
                ", temperature=" + temperature +
                ", motionState=" + motionState +
                ", accelerationX=" + accelerationX +
                ", accelerationY=" + accelerationY +
                ", accelerationZ=" + accelerationZ +
                ", battery=" + battery +
                ", measurepower=" + measurepower +
                '}';
    }
}
