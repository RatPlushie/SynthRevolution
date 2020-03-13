package com.ratbox.synthrevolution;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ratbox.synthrevolution.ui.main.BluetoothManager;
import com.ratbox.synthrevolution.ui.main.BluetoothRecyclerViewAdapter;
import com.ratbox.synthrevolution.ui.main.SectionsPagerAdapter;
import com.ratbox.synthrevolution.ui.main.SynthPattern;
import com.ratbox.synthrevolution.ui.main.SynthVisor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String SYNTHVISORFILENAME      = "SynthVisorConfig.txt";
    private static final String SYNTHPATTERNFILENAME    = "VisorPatternConfig.txt";

    public static BluetoothManager  bluetoothManager;
    public static SynthPattern      synthPattern;
    public static SynthVisor        synthVisor;

    private RecyclerView            bluetoothRecyclerView;

    private Dialog                  bluetoothDialog;
    private Dialog                  uploadDialog;

    private ImageButton             btnBluetoothDialog;

    private Button                  btnSendToVisor;

    private boolean                 connectionEstablished;

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
        bluetoothDialog     = new Dialog(this);
        uploadDialog        = new Dialog(this);

        // Initialising the synth instance objects
        synthVisor          = new SynthVisor();
        synthPattern        = new SynthPattern();

        // Initialising the bluetooth manager instance
        bluetoothManager    = new BluetoothManager();
    }

    // Loading the synthVisor and synthPattern configs onResume from respective .txt files
    @Override
    protected void onResume() {
        super.onResume();

        BufferedReader reader = null;

        // Attempting to load the synthVisor config file to its respective object
        try {
            FileInputStream fileInputStream = MainActivity.this.openFileInput(SYNTHVISORFILENAME);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));

            int counter = 0;        // Line read counter
            String lineReadString;  // Temporary read line
            while ((lineReadString = reader.readLine()) != null){   // EoF check

                switch (counter){
                    case 0: // Red
                        String[] splitRed = lineReadString.split("=");
                        lineReadString = splitRed[splitRed.length - 1];
                        MainActivity.synthVisor.setRGB_Red(Integer.parseInt(lineReadString));
                        counter++;
                        break;

                    case 1: // Green
                        String[] splitGreen = lineReadString.split("=");
                        lineReadString = splitGreen[splitGreen.length - 1];
                        MainActivity.synthVisor.setRGB_Green(Integer.parseInt(lineReadString));
                        counter++;
                        break;

                    case 2: // Blue
                        String[] splitBlue = lineReadString.split("=");
                        lineReadString = splitBlue[splitBlue.length - 1];
                        MainActivity.synthVisor.setRGB_Blue(Integer.parseInt(lineReadString));
                        counter++;
                        break;

                    case 3: // LED Brightness
                        String[] splitBrightness = lineReadString.split("=");
                        lineReadString = splitBrightness[splitBrightness.length - 1];
                        MainActivity.synthVisor.setLED_Brightness(Integer.parseInt(lineReadString));
                        counter++;
                        break;

                    case 4: // Blink Rate
                        String[] splitBlinkRate = lineReadString.split("=");
                        lineReadString = splitBlinkRate[splitBlinkRate.length - 1];
                        MainActivity.synthVisor.setBlinkRate(Integer.parseInt(lineReadString));
                        counter++;
                        break;

                    case 5: // HEX Value
                        String[] splitHEX = lineReadString.split("=");
                        lineReadString = splitHEX[splitHEX.length - 1];
                        MainActivity.synthVisor.setHex(lineReadString);
                        counter++;
                        break;

                    case 6: // Swatch 1
                        String [] splitSwatch1 = lineReadString.split("=");
                        lineReadString = splitSwatch1[splitSwatch1.length - 1];
                        String swatch1RGB[] = lineReadString.split(",");
                        MainActivity.synthVisor.swatch1 = new int[]{Integer.parseInt(swatch1RGB[0]), Integer.parseInt(swatch1RGB[1]), Integer.parseInt(swatch1RGB[2])};
                        counter++;
                        break;

                    case 7: // Swatch 2
                        String [] splitSwatch2 = lineReadString.split("=");
                        lineReadString = splitSwatch2[splitSwatch2.length - 1];
                        String swatch2RGB[] = lineReadString.split(",");
                        MainActivity.synthVisor.swatch2 = new int[]{Integer.parseInt(swatch2RGB[0]), Integer.parseInt(swatch2RGB[1]), Integer.parseInt(swatch2RGB[2])};
                        counter++;
                        break;

                    case 8: // Swatch 3
                        String [] splitSwatch3 = lineReadString.split("=");
                        lineReadString = splitSwatch3[splitSwatch3.length - 1];
                        String swatch3RGB[] = lineReadString.split(",");
                        MainActivity.synthVisor.swatch3 = new int[]{Integer.parseInt(swatch3RGB[0]), Integer.parseInt(swatch3RGB[1]), Integer.parseInt(swatch3RGB[2])};
                        counter++;
                        break;

                    case 9: // Swatch 4
                        String [] splitSwatch4 = lineReadString.split("=");
                        lineReadString = splitSwatch4[splitSwatch4.length - 1];
                        String swatch4RGB[] = lineReadString.split(",");
                        MainActivity.synthVisor.swatch4 = new int[]{Integer.parseInt(swatch4RGB[0]), Integer.parseInt(swatch4RGB[1]), Integer.parseInt(swatch4RGB[2])};
                        counter++;
                        break;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (reader != null){
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // Attempting to load the synthPattern config to its respective object
        try {
            FileInputStream fileInputStream = MainActivity.this.openFileInput(SYNTHPATTERNFILENAME);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));

            // Clearing the current lists so the list.add() method later doesn't append to an existing list
            MainActivity.synthPattern.patternNameList.clear();
            MainActivity.synthPattern.patternConfList.clear();

            String readString;
            while ((readString = reader.readLine()) != null){ // EoF Check
                // Splitting the line into name and pattern
                String[] splitString = readString.split("=");

                char[] patternArray = splitString[1].toCharArray();

                // Storing the name and pattern in the lists
                MainActivity.synthPattern.patternNameList.add(splitString[0]);
                MainActivity.synthPattern.patternConfList.add(patternArray);
            }

            Log.d("File Read", "Successful");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("PatternFile", "No file found, Creating default");

            // No pattern save file was found on the device, creating a new save file and filling it with the default synth eye pattern
            BufferedWriter writer = null;
            try {
                FileOutputStream fileOutputStream = MainActivity.this.openFileOutput(SYNTHPATTERNFILENAME, Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

                // Creating the default pattern for the eye
                char[] defaultPattern = {'0','0','0','0','0','0','0','0',
                                         '0','0','1','1','1','1','1','0',
                                         '0','1','1','1','1','1','1','1',
                                         '1','1','0','1','1','1','1','1',
                                         '1','1','0','1','1','1','1','0',
                                         '1','1','0','1','1','1','0','0',
                                         '0','1','0','1','1','0','0','0',
                                         '0','0','0','0','0','0','0','0'};

                // adding the default pattern array to the list of arrays
                MainActivity.synthPattern.patternNameList.add("Default");
                MainActivity.synthPattern.patternConfList.add(defaultPattern);

                // StringBuilder init
                StringBuilder stringBuilder = new StringBuilder();

                // Building string to write
                stringBuilder.append("Default=");
                for (char c : defaultPattern){
                    stringBuilder.append(c);
                }

                // Writing string to file
                writer.write(stringBuilder.toString());

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();

            } catch (IOException ex) {
                ex.printStackTrace();

            } finally { // Closing the writer
                if (writer != null){
                    try {
                        writer.close();

                    } catch (IOException eIO) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally { // closing the reader
            if (reader != null){
                try {
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        BufferedWriter writer = null;

        // Writing the synthVisor config to the respective text file
        try {
            FileOutputStream fileOutputStream = MainActivity.this.openFileOutput(SYNTHVISORFILENAME, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            writer.write("RGB_Red =" + MainActivity.synthVisor.RGB_Red);
            writer.newLine();

            writer.write("RGB_Green =" + MainActivity.synthVisor.RGB_Green);
            writer.newLine();

            writer.write("RGB_Blue =" + MainActivity.synthVisor.RGB_Blue);
            writer.newLine();

            writer.write("LED_Brightness =" + MainActivity.synthVisor.LED_Brightness);
            writer.newLine();

            writer.write("Blink Rate =" + MainActivity.synthVisor.blinkRate);
            writer.newLine();

            writer.write("HEX =" + MainActivity.synthVisor.hex);
            writer.newLine();

            writer.write("Swatch1 =" + MainActivity.synthVisor.swatch1[0] + "," + MainActivity.synthVisor.swatch1[1] + "," + MainActivity.synthVisor.swatch1[2]);
            writer.newLine();

            writer.write("Swatch2 =" + MainActivity.synthVisor.swatch2[0] + "," + MainActivity.synthVisor.swatch2[1] + "," + MainActivity.synthVisor.swatch2[2]);
            writer.newLine();

            writer.write("Swatch3 =" + MainActivity.synthVisor.swatch3[0] + "," + MainActivity.synthVisor.swatch3[1] + "," + MainActivity.synthVisor.swatch3[2]);
            writer.newLine();

            writer.write("Swatch4 =" + MainActivity.synthVisor.swatch4[0] + "," + MainActivity.synthVisor.swatch4[1] + "," + MainActivity.synthVisor.swatch4[2]);
            writer.newLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (writer != null){
                try {
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Console printout of when the device saves the config
        Log.d("SynthVisor", "Saved");

        // Writing the synthPattern config to its respective text file
        // TODO - writer seems to be duplicating all entries onPause
        try {
            FileOutputStream fileOutputStream = MainActivity.this.openFileOutput(SYNTHPATTERNFILENAME, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            // For every entry of the name list, add a line in the format name=pattern
            for (int i = 0; i <= MainActivity.synthPattern.patternNameList.size() - 1; i++){
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(MainActivity.synthPattern.patternNameList.get(i));
                stringBuilder.append("=");

                for (char c : MainActivity.synthPattern.patternConfList.get(i)){
                    stringBuilder.append(c);
                }

                writer.write(stringBuilder.toString());
                writer.newLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (writer != null){
                try {
                    writer.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Console printout of when the device saves the patterns
        Log.d("SynthPatterns", "Saved");
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

        // TODO - on first showing of the dialog box, sometimes the button is unresponsive "InputEventReceiver: Attempted to finish an input event but the input event receiver has already been disposed."

        // Attaching to views
        btnSendToVisor = view.findViewById(R.id.uploadToVisorButton);

        btnSendToVisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    // Sending the updated values to the synthVisor
                    bluetoothManager.sendSynthVisor(synthVisor.RGB_Red, synthVisor.RGB_Green, synthVisor.RGB_Blue, synthVisor.LED_Brightness, synthVisor.blinkRate);

                } catch (Exception noVisorPresent){
                    Log.e("Bluetooth Connected", "False");
                }
            }
        });
    }
}