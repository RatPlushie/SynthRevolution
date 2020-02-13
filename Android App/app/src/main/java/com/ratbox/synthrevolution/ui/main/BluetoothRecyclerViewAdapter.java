package com.ratbox.synthrevolution.ui.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratbox.synthrevolution.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BluetoothRecyclerViewAdapter extends RecyclerView.Adapter<BluetoothRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String>   bluetoothNames;
    private ArrayList<String>   bluetoothMACs;
    private Context             mContext;

    public static final String SHARED_PREFS = "sharedPrefs";


    // Default constructor
    public BluetoothRecyclerViewAdapter(Context mContext, ArrayList<String> bluetoothNames, ArrayList<String> bluetoothMACs) {
        this.bluetoothNames = bluetoothNames;
        this.bluetoothMACs = bluetoothMACs;
        this.mContext = mContext;
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

        // Creating onClick listener to each bluetooth device
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Getting the relevant UUID
                // Initialising Bluetooth Adapter
                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

                // Initialising Bluetooth Device
                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(bluetoothMACs.get(position));

                // Finding the array of all UUIDs the connected device has
                ParcelUuid[] parcelUuid = bluetoothDevice.getUuids();
                UUID mUUID = parcelUuid[0].getUuid();
                Log.d("UUID Discovered", mUUID.toString());

                // Moving the selected bluetooth device to shared preferences for persistent discovery
                // Initialisation of the shared preferences object
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);

                // Initialisation of the shared preferences editor
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Adding Name, MAC, and UUID to sharedPrefs
                editor.putString("bluetoothName", bluetoothNames.get(position));
                editor.putString("bluetoothMAC", bluetoothMACs.get(position));
                editor.putString("bluetoothUUID", mUUID.toString());

                // Applying changes to the sharedPrefs file
                editor.apply();

                Toast.makeText(mContext,"Bluetooth Device Selected",Toast.LENGTH_SHORT).show();
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
