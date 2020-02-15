package com.ratbox.synthrevolution;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.ratbox.synthrevolution.ui.main.BluetoothManager;
import com.ratbox.synthrevolution.ui.main.BluetoothRecyclerViewAdapter;
import com.ratbox.synthrevolution.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    public static BluetoothManager bluetoothManager = new BluetoothManager();

    private RecyclerView        bluetoothRecyclerView;
    private Dialog              mDialog;

    private ImageButton         btnBluetooth;


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

        // Attaching to views
        btnBluetooth = v.findViewById(R.id.bluetoothButton);
        mDialog.setContentView(R.layout.bluetooth_popout);
        bluetoothRecyclerView = mDialog.findViewById(R.id.bluetoothRecyclerView);

        // Creating the bluetoothDevice array
        if (bluetoothManager.pairedDevices.size() > 0){

            // Initialising the recyclerView
            BluetoothRecyclerViewAdapter adapter = new BluetoothRecyclerViewAdapter(v.getContext(), bluetoothManager.listBluetoothNames, bluetoothManager.listBluetoothMACs);
            bluetoothRecyclerView.setAdapter(adapter);
            bluetoothRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Displaying the popup dialog box
            mDialog.show();
            Log.d("Bluetooth pop-up box", "Displayed");

            // onDismissListener for the bluetoothPopUp to change the bluetooth button icon if bluetooth is connected
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    if (bluetoothManager.bluetoothSocket.isConnected()){
                        btnBluetooth.setImageResource(R.drawable.ic_bluetooth_connected);
                    } else if (!bluetoothManager.bluetoothSocket.isConnected()){
                        btnBluetooth.setImageResource(R.drawable.ic_bluetooth_disconnected);
                    }
                }
            });

        } else {
            Toast.makeText(this, "Please pair your SynthVisor in your device's bluetooth settings", Toast.LENGTH_LONG).show();
        }
    }
}