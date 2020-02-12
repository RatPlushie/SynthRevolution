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
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bluetooth_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Binding each list item to the recyclerView
        holder.bluetoothDeviceName.setText(bluetoothNames.get(position));
        holder.bluetoothDeviceMAC.setText(bluetoothMACs.get(position));

        // Creating onClick listener to each bluetooth device
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO - select bluetooth device and pass device info to other methods
                Toast.makeText(mContext, bluetoothNames.get(position) + " pressed", Toast.LENGTH_SHORT).show();



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
