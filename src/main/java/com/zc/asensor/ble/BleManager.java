package com.zc.asensor.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

public class BleManager {

    private Context context;
    private BluetoothAdapter mBluetoothAdapter;
    private MyScanCallBack scanCallBack;
    private ArrayList<ScanResult> results;
    private long scanPeriod = 1000;
    private long beforeTime;
    private boolean isStartScan;

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            if (TextUtils.isEmpty(device.getName()))
                return;
            if (!device.getName().contains("asensor"))
                return;
            ScanResult result = new ScanResult();
            result.setDevice(device);
            result.setRssi(rssi);
            result.setScanRecord(scanRecord);
            result.setHexScanRecord(UUID2bytesUtils.hex(scanRecord));
            long nowTime = System.currentTimeMillis();
            if (nowTime - beforeTime > scanPeriod) {
                beforeTime = nowTime;
                ArrayList<ScanResult> myResults = new ArrayList<>();
                myResults.addAll(results);
                scanCallBack.onScanCallBack(myResults);
                results.clear();

            } else {
                if (!results.contains(result)) {
                    results.add(result);
                } else {
                    results.remove(result);
                    results.add(result);
                }
            }
            scanCallBack.onScanCallBack(result);
            scanCallBack.onScanCallBack(device, rssi, scanRecord);
        }
    };

    public BleManager(Context context) {
        this.context = context;
        final BluetoothManager bluetoothManager = (BluetoothManager) context
                .getApplicationContext().getSystemService(
                        Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }


    /**
     * 检测是否支持蓝牙4.0BLE
     *
     * @return true为设备支持BLE
     */
    public boolean hasBluetooth() {
        return this.context.getPackageManager().hasSystemFeature(
                "android.hardware.bluetooth_le");
    }

    /**
     * 开启蓝牙Ble扫描
     *
     * @return true为成功开启扫描
     */
    public boolean startBleScan(MyScanCallBack scanCallBack) {
        results = new ArrayList<>();
        if (mBluetoothAdapter == null) {
            return false;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            return false;
        }
        isStartScan = true;
        this.scanCallBack = scanCallBack;
        return mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    /**
     * 停止蓝牙Ble扫描
     */
    public void stopBleScan() {
        isStartScan = false;
        if (results != null) {
            results.clear();
        }
        if (mBluetoothAdapter == null) {
            return;
        }
        if (mLeScanCallback == null) {
            return;
        }
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    /**
     * 设置扫描间隔时间 返回扫描结果list的结果
     *
     * @param scanPeriod 扫描间隔 单位ms
     */
    public void setScanPeriod(long scanPeriod) {
        this.scanPeriod = scanPeriod;
    }

    /**
     * 扫描开启的回调
     *
     * @author think_admin
     */
    public static abstract interface ScanCallBack {
        /**
         * 扫描到数据时的回调
         *
         * @param scanRecord 扫描到的设备的广播数据
         * @param rssi       扫描到的设备的rssi
         * @param device     扫描到的设备
         */
        public abstract void onScanCallBack(BluetoothDevice device, int rssi,
                                            byte[] scanRecord);

        /**
         * 扫描到数据时的回调
         *
         * @param result 扫描数据结果
         */
        public abstract void onScanCallBack(ScanResult result);

        /**
         * scanPeriod内扫描到数据时的回调
         *
         * @param results scanPeriod内扫描到的设备的集合
         */
        public abstract void onScanCallBack(ArrayList<ScanResult> results);


    }

    public static class MyScanCallBack implements ScanCallBack {

        public void onScanCallBack(BluetoothDevice device, int rssi,
                                   byte[] scanRecord) {

        }

        public void onScanCallBack(ArrayList<ScanResult> results) {

        }

        public void onScanCallBack(ScanResult result) {

        }
    }

    public boolean isStartScan() {
        return isStartScan;
    }
}
