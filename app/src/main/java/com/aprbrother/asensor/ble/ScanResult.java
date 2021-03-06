package com.aprbrother.asensor.ble;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class ScanResult implements Parcelable {

    private BluetoothDevice device;
    private int rssi;
    private byte[] scanRecord;
    private String hexScanRecord;

    public ScanResult() {

    }

    protected ScanResult(Parcel in) {
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        rssi = in.readInt();
        scanRecord = in.createByteArray();
        hexScanRecord = in.readString();
    }

    public static final Creator<ScanResult> CREATOR = new Creator<ScanResult>() {
        @Override
        public ScanResult createFromParcel(Parcel in) {
            return new ScanResult(in);
        }

        @Override
        public ScanResult[] newArray(int size) {
            return new ScanResult[size];
        }
    };

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }

    public void setScanRecord(byte[] scanRecord) {
        this.scanRecord = scanRecord;
    }

    public String getHexScanRecord() {
        return hexScanRecord;
    }

    public void setHexScanRecord(String hexScanRecord) {
        this.hexScanRecord = hexScanRecord;
    }

    @Override
    public String toString() {
        return "ScanResult [device=" + device + ", rssi=" + rssi
                + ", scanRecord=" + Arrays.toString(scanRecord)
                + ", hexScanRecord=" + hexScanRecord + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((device == null) ? 0 : device.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ScanResult other = (ScanResult) obj;
        if (device == null) {
            if (other.device != null)
                return false;
        } else if (!device.equals(other.device))
            return false;
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(device, flags);
        dest.writeInt(rssi);
        dest.writeByteArray(scanRecord);
        dest.writeString(hexScanRecord);
    }

}
