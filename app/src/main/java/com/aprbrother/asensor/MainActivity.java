package com.aprbrother.asensor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aprbrother.asensor.ble.BleManager;
import com.aprbrother.asensor.ble.ScanResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<ASensor> datas = new ArrayList<>();
    private RecyclerView rv;
    private ASensorAdapter mAdapter;
    private TextView tvSearching;
    private BleManager bleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(mReceiver, makeFilter());
        initView();
        initScan();
    }

    private void initScan() {
        if (!getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            tvSearching.setText("did not support ble");
            return;
        }
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (!adapter.isEnabled()) {
            adapter.enable();
            return;
        }
        startScan();
    }

    private void startScan() {
        bleManager = new BleManager(this);
        bleManager.startBleScan(new BleManager.MyScanCallBack() {
            @Override
            public void onScanCallBack(ArrayList<ScanResult> results) {
                if (results.size() == 0) return;
                for (ScanResult scanResult : results) {
                    if (((int) scanResult.getScanRecord()[5] & 0xff) == 0x4c
                            && ((int) scanResult.getScanRecord()[6] & 0xff) == 0x00
                            && ((int) scanResult.getScanRecord()[7] & 0xff) == 0x02
                            && ((int) scanResult.getScanRecord()[8] & 0xff) == 0x15) {
                        Log.i(TAG, "onScanCallBack: this is a beacon broadcast");
                    } else {
                        ASensor asensor = parseData(scanResult.getDevice(), scanResult.getScanRecord());
                        if (datas.contains(asensor)) {
                            datas.remove(asensor);
                        }
                        datas.add(asensor);
                    }
                }
                if (datas.size() > 0) {
                    tvSearching.setVisibility(View.GONE);
                    mAdapter.refreshData(datas);
                }
            }
        });
    }

    private ASensor parseData(BluetoothDevice device, byte[] scanRecord) {
        ASensor asensor = new ASensor();
        asensor.setName(device.getName()==null?"Unknown":device.getName());
        asensor.setMac(device.getAddress());
        asensor.setTime(System.currentTimeMillis());
        asensor.setTemperature(scanRecord[18] & 0xff);
        asensor.setMotionState(scanRecord[19] & 0xff);
        asensor.setAccelerationX(scanRecord[20] & 0xff);
        asensor.setAccelerationY(scanRecord[21] & 0xff);
        asensor.setAccelerationZ(scanRecord[22] & 0xff);
        asensor.setBattery(scanRecord[25] & 0xff);
        asensor.setMeasurepower((scanRecord[26] & 0xff) - 255);
        return asensor;
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ASensorAdapter(this);
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new SpacesItemDecoration(30));
        tvSearching = (TextView) findViewById(R.id.tvSearching);
    }

    private IntentFilter makeFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        return filter;
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_TURNING_ON:
                            break;
                        case BluetoothAdapter.STATE_ON:
                            if (!bleManager.isStartScan())
                                startScan();
                            break;
                        case BluetoothAdapter.STATE_TURNING_OFF:
                            break;
                        case BluetoothAdapter.STATE_OFF:
                            break;
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        bleManager.stopBleScan();
    }
}
