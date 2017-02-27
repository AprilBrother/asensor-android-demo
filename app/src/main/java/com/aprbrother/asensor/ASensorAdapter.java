package com.aprbrother.asensor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ASensorAdapter extends RecyclerView.Adapter<ASensorAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ASensor> datas;

    public ASensorAdapter(Context context) {
        datas = new ArrayList<>();
        this.context = context;
    }

    public void refreshData(ArrayList<ASensor> newDatas) {
        datas.clear();
        datas.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public ASensorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asensor, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ASensorAdapter.ViewHolder holder, int position) {
        holder.name.setText(datas.get(position).getName());
        holder.mac.setText(datas.get(position).getMac());
        holder.time.setText("last update time:" + getYMDHMS(datas.get(position).getTime()));
        holder.temperature.setText("temperature : " + datas.get(position).getTemperature() + "℃");
        holder.state.setText("motion state : " + (datas.get(position).getMotionState() == 1 ? "move" : "static"));
        holder.accelerationx.setText("acceleration X : "+datas.get(position).getAccelerationX());
        holder.accelerationy.setText("acceleration Y : "+datas.get(position).getAccelerationY());
        holder.accelerationz.setText("acceleration Z : "+datas.get(position).getAccelerationZ());
        holder.measurepower.setText("measurepower : "+datas.get(position).getMeasurepower());
        holder.battery.setText("battery : "+datas.get(position).getBattery()+"%");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView mac;
        public TextView time;
        public TextView state;
        public TextView temperature;
        public TextView accelerationx;
        public TextView accelerationy;
        public TextView accelerationz;
        public TextView measurepower;
        public TextView battery;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            mac = (TextView) view.findViewById(R.id.mac);
            time = (TextView) view.findViewById(R.id.time);
            temperature = (TextView) view.findViewById(R.id.temperature);
            state = (TextView) view.findViewById(R.id.state);
            accelerationx = (TextView) view.findViewById(R.id.accelerationx);
            accelerationy = (TextView) view.findViewById(R.id.accelerationy);
            accelerationz = (TextView) view.findViewById(R.id.accelerationz);
            measurepower = (TextView) view.findViewById(R.id.measurepower);
            battery = (TextView) view.findViewById(R.id.battery);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String getYMDHMS(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }
}
