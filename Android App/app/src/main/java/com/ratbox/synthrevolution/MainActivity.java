package com.ratbox.synthrevolution;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.ratbox.synthrevolution.ui.main.BluetoothManager;
import com.ratbox.synthrevolution.ui.main.BluetoothRecyclerViewAdapter;
import com.ratbox.synthrevolution.ui.main.SectionsPagerAdapter;
import com.ratbox.synthrevolution.ui.main.SynthVisor;

public class MainActivity extends AppCompatActivity {

    public static BluetoothManager  bluetoothManager = new BluetoothManager();
    public static SynthVisor        synthVisor = new SynthVisor();

    private RecyclerView        bluetoothRecyclerView;

    private Dialog              bluetoothDialog;
    private Dialog              uploadDialog;

    private ImageButton         btnBluetoothDialog;

    private Button              btnSendtoVisor;

    private TextView            uploadStatus;

    private boolean             connectionEstablished;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        // Initialising dialogues
        bluetoothDialog = new Dialog(this);
        uploadDialog    = new Dialog(this);
    }


    // Bluetooth device selector dialog box
    public void ShowBluetoothPopup(View v){

        // Attaching to views
        btnBluetoothDialog = v.findViewById(R.id.bluetoothButton);
        bluetoothDialog.setContentView(R.layout.bluetooth_popout);
        bluetoothRecyclerView = bluetoothDialog.findViewById(R.id.bluetoothRecyclerView);

        // Creating the bluetoothDevice array
        if (bluetoothManager.pairedDevices.size() > 0){

            // Initialising the recyclerView
            BluetoothRecyclerViewAdapter adapter = new BluetoothRecyclerViewAdapter(v.getContext(), bluetoothManager.listBluetoothNames, bluetoothManager.listBluetoothMACs);
            bluetoothRecyclerView.setAdapter(adapter);
            bluetoothRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Displaying the popout dialog box
            bluetoothDialog.show();
            Log.d("Bluetooth pop-up box", "Displayed");

            // onDismissListener for the bluetoothPopUp to change the bluetooth button icon if bluetooth is connected
            bluetoothDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                    try {
                        connectionEstablished = bluetoothManager.bluetoothSocket.isConnected();
                    } catch (Exception BluetoothSocketNotInitialised){
                        connectionEstablished = false;
                    }

                    if (connectionEstablished){
                        btnBluetoothDialog.setImageResource(R.drawable.ic_bluetooth_connected);
                    } else {
                        btnBluetoothDialog.setImageResource(R.drawable.ic_bluetooth_disconnected);
                    }
                }
            });

        } else {
            Toast.makeText(this, "Please pair your SynthVisor in your device's bluetooth settings", Toast.LENGTH_LONG).show();
        }
    }


    // Upload dialog box
    public void ShowUploadPopup(View view){

        // Checking to see if the bluetooth synth visor has been connected - if not, toast user to first connect
        try {
            connectionEstablished = bluetoothManager.bluetoothSocket.isConnected();
        } catch (Exception BluetoothSocketNotInitialised){
            connectionEstablished = false;
        }

        if (connectionEstablished){ // Already connected - Open dialog

            // Attaching to views
            uploadDialog.setContentView(R.layout.upload_popout);

            // Displaying the popout dialog box for uploading
            uploadDialog.show();

        } else { // Not connected - prevent further action and toast user
            Toast.makeText(this, "Please connect to SynthVisor First", Toast.LENGTH_LONG).show();
        }
    }


    // Send to synthVisor button behaviour
    public void SendToSynthVisor(View view){

        // TODO - add upload status text view for user feedback

        // Attaching to views
        btnSendtoVisor  = view.findViewById(R.id.uploadToVisorButton);
        //uploadStatus    = view.findViewById(R.id.uploadStatusTextView);

        // Hiding textView till required
        //uploadStatus.setVisibility(View.INVISIBLE);

        btnSendtoVisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    // Text View upload status
                    //uploadStatus.setVisibility(View.VISIBLE);
                    //uploadStatus.setText(getText(R.string.sending));

                    // Sending the updated values to the synthVisor
                    bluetoothManager.sendSynthVisor(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue, synthVisor.LED_Brightness, synthVisor.blinkRate);

                    //uploadStatus.setText(getText(R.string.sent));

                } catch (Exception noVisorPresent){
                    Log.e("Bluetooth Connected", "False");
                }
            }
        });
    }
}