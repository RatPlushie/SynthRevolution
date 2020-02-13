package com.ratbox.synthrevolution;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.ratbox.synthrevolution.ui.main.BluetoothRecyclerViewAdapter;
import com.ratbox.synthrevolution.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    RecyclerView bluetoothRecyclerView;

    Dialog mDialog;

    ArrayList<String> bluetoothNameList;
    ArrayList<String> bluetoothMACList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Creating the bluetooth pop up dialog box
        mDialog = new Dialog(this);
    }


    // Bluetooth device selector dialog box
    public void ShowPopup(View v){

        // Trying to call this method will fail if there is no bluetooth hardware on the android device itself
        try{
            mDialog.setContentView(R.layout.bluetooth_popout);

            bluetoothRecyclerView = mDialog.findViewById(R.id.bluetoothRecyclerView);

            // Initialising Bluetooth Adapter
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // Creating a set list of all the paired devices the phone has connected to
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

            // Creating the bluetoothDevice array
            if (pairedDevices.size() > 0){

                bluetoothNameList = new ArrayList<>();
                bluetoothMACList = new ArrayList<>();

                // Adding all the bonded devices to their respective list
                for (BluetoothDevice bluetoothDevice : pairedDevices){
                    bluetoothNameList.add(bluetoothDevice.getName());
                    bluetoothMACList.add(bluetoothDevice.getAddress());
                }

                // Initialising the recyclerView
                BluetoothRecyclerViewAdapter adapter = new BluetoothRecyclerViewAdapter(v.getContext(), bluetoothNameList, bluetoothMACList);
                bluetoothRecyclerView.setAdapter(adapter);
                bluetoothRecyclerView.setLayoutManager(new LinearLayoutManager(this));

                // Displaying the popup dialog box
                mDialog.show();
                Log.d("Dialog box", "Displayed");



            } else {
                Toast.makeText(this, "Please pair your SynthVisor in your device's bluetooth settings", Toast.LENGTH_LONG).show();
            }

        } catch (Exception noBluetoothDevice){
            Log.e("Bluetooth Hardware", "No Bluetooth hardware detected on this device");
            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_SHORT).show();
        }
    }
}