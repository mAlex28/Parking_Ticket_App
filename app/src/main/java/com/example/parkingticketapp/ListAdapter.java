package com.example.parkingticketapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ListAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Vehicle> vehicleList;

    public ListAdapter(Activity mContext, List<Vehicle> vehicleList) {
        super(mContext, R.layout.vehicle_list, vehicleList);
        this.mContext = mContext;
        this.vehicleList = vehicleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View listItemView = inflater.inflate(R.layout.vehicle_list, null, true);

        TextView vdate = listItemView.findViewById(R.id.dateId);
        TextView vehicleNum = listItemView.findViewById(R.id.vehicleNoId);
        TextView vehicleType = listItemView.findViewById(R.id.typeId);
        TextView hours = listItemView.findViewById(R.id.hoursId);
        TextView price = listItemView.findViewById(R.id.priceId);

        Vehicle vehicle = vehicleList.get(position);

        vdate.setText(vehicle.getDate());
        vehicleNum.setText(vehicle.getvVehicleNo());
        vehicleType.setText(vehicle.getType());
        hours.setText(vehicle.getvHours());
        price.setText(vehicle.getvTotal());

        return listItemView;
    }
}
