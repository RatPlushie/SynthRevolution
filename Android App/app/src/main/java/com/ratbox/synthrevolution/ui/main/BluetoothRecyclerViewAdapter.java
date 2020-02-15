package com.ratbox.synthrevolution.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratbox.synthrevolution.R;

import java.util.ArrayList;

public class BluetoothRecyclerViewAdapter extends RecyclerView.Adapter<BluetoothRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String>   bluetoothNames;
    private ArrayList<String>   bluetoothMACs;
    private Context             mContext;

    public static final String SHARED_PREFS = "sharedPrefs";

    BluetoothManager bluetoothManager = new BluetoothManager();


    // Default constructor
    public BluetoothRecyclerViewAdapter(Context mContext, ArrayList<String> bluetoothNames, ArrayList<String> bluetoothMACs) {
        this.bluetoothNames = bluetoothNames;
        this.bluetoothMACs = bluetoothMACs;
        this.mContext = mContext;

        // Passing the ArrayLists into the bluetoothManager class
        bluetoothManager.listBluetoothNames = bluetoothNames;
        bluetoothManager.listBluetoothMACs = bluetoothMACs;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        // Binding each list item to the recyclerView
        holder.bluetoothDeviceName.setText(bluetoothNames.get(position));
        holder.bluetoothDeviceMAC.setText(bluetoothMACs.get(position));

        // Populating bluetoothManager with the selected Name & MAC address
        bluetoothManager.deviceName = bluetoothNames.get(position);
        bluetoothManager.deviceMAC = bluetoothMACs.get(position);

        // Creating onClick listener to each bluetooth device
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Initialising Bluetooth Device
                bluetoothManager.getUUID();

                // Saving the selected device to android local memory
                bluetoothManager.saveConfig(mContext);

                // Initial connection of bluetooth
                bluetoothManager.connect();

                // TODO - pass bluetooth connection from this point



                Toast.makeText(mContext,bluetoothManager.deviceName + " device Selected",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bluetoothNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView        bluetoothDeviceName;
        TextView        bluetoothDeviceMAC;
        LinearLayout    parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bluetoothDeviceName = itemView.findViewById(R.id.bluetoothDeviceName);
            bluetoothDeviceMAC  = itemView.findViewById(R.id.bluetoothDeviceMAC);
            parentLayout        = itemView.findViewById(R.id.bluetoothParentLayout);
        }
    }
}
