package com.ratbox.synthrevolution.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratbox.synthrevolution.MainActivity;
import com.ratbox.synthrevolution.R;
import java.util.ArrayList;

public class BluetoothRecyclerViewAdapter extends RecyclerView.Adapter<BluetoothRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String>   bluetoothNames;
    private ArrayList<String>   bluetoothMACs;
    private Context             mContext;


    // Default constructor
    public BluetoothRecyclerViewAdapter(Context mContext, ArrayList<String> bluetoothNames, ArrayList<String> bluetoothMACs) {
        this.bluetoothNames = bluetoothNames;
        this.bluetoothMACs = bluetoothMACs;
        this.mContext = mContext;

        // Passing the ArrayLists into the bluetoothManager class
        MainActivity.bluetoothManager.listBluetoothNames = bluetoothNames;
        MainActivity.bluetoothManager.listBluetoothMACs = bluetoothMACs;
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
        MainActivity.bluetoothManager.deviceName = bluetoothNames.get(position);
        MainActivity.bluetoothManager.deviceMAC = bluetoothMACs.get(position);

        // Creating onClick listener to each bluetooth device
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // User feedback that device is connecting
                Toast.makeText(mContext, "Connecting...", Toast.LENGTH_SHORT);

                // Discovering UUID of the bluetooth device
                MainActivity.bluetoothManager.getUUID();

                // Saving the selected device to android local memory
                //bluetoothManager.saveConfig(mContext);

                // Initial connection of bluetooth
                MainActivity.bluetoothManager.connect();

                // Testing to see if the connection is established
                if (MainActivity.bluetoothManager.bluetoothSocket.isConnected()){
                    Toast.makeText(mContext,"Connected to " + MainActivity.bluetoothManager.deviceName,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mContext,"Device unreachable, please try again...",Toast.LENGTH_LONG).show();
                }
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
