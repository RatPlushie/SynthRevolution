package com.ratbox.synthrevolution.ui.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothManager {

    private static final String FILENAME = "bluetoothConfig.txt";

    public ArrayList<String> listBluetoothNames;
    public ArrayList<String> listBluetoothMACs;
    public Set<BluetoothDevice> pairedDevices;

    public String deviceName;
    public String deviceMAC;
    public String deviceUUID;

    public BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    public BluetoothSocket  bluetoothSocket;

    public BluetoothManager(){

        pairedDevices       = bluetoothAdapter.getBondedDevices();

        listBluetoothNames  = new ArrayList<>();
        listBluetoothMACs   = new ArrayList<>();

        for (BluetoothDevice bluetoothDevice : pairedDevices){
            listBluetoothNames.add(bluetoothDevice.getName());
            listBluetoothMACs.add(bluetoothDevice.getAddress());
        }
    }

    public void getUUID(){

        // Getting the specifically selected device
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceMAC);

        // Finding the correct UUID for the bluetooth device
        ParcelUuid[] parcelUuid = bluetoothDevice.getUuids();
        UUID mUUID = parcelUuid[0].getUuid();
        deviceUUID = mUUID.toString();
    }

    public void saveConfig(Context context){

        // Saving bluetooth device specific variables to a locally stored .txt file
        BufferedWriter writer = null;

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            writer.write("deviceName =" + deviceName);
            writer.newLine();
            writer.write("deviceMAC = " + deviceMAC);
            writer.newLine();
            writer.write("deviceUUID = " + deviceUUID);
            writer.newLine();


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfig(Context context){

        BufferedReader reader = null;

        try {
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            reader = new BufferedReader(new InputStreamReader(fileInputStream));

            int i = 0; // line counter
            String tempString;
            while ((tempString = reader.readLine()) != null){

                switch (i){

                    case 0: // Device name

                        String[] splitName = tempString.split("=");
                        tempString = splitName[splitName.length - 1];
                        deviceName = tempString;
                        i++;
                        break;

                    case 1:

                        String[] splitMAC = tempString.split("=");
                        tempString = splitMAC[splitMAC.length - 1];
                        deviceMAC = tempString;
                        i++;
                        break;

                    case 2:

                        String[] splitUUID = tempString.split("=");
                        tempString = splitUUID[splitUUID.length - 1];
                        deviceUUID = tempString;
                        i++;
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


    }

    public void connect(){

        try { // Potentially throwable if invalid bluetooth config is given
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceMAC);
            bluetoothSocket = null;

            // Trying to connect to the device upto 3 times
            int counter = 0;
            do {
                try {

                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString(deviceUUID));
                    bluetoothSocket.connect();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } while (!bluetoothSocket.isConnected() && counter < 3);

        } catch (Exception nonValidBluetoothAddress){
            Log.e("Valid Bluetooth Address", "false");
        }
    }

    public void sendSynthVisor(int red, int green, int blue, int brightness, int blink){

        try {
            OutputStream outputStream;
            outputStream = bluetoothSocket.getOutputStream();

            String[][] tempArray = new String[][]{{Integer.toString(red), ""},
                                                  {Integer.toString(green), ""},
                                                  {Integer.toString(blue), ""},
                                                  {Integer.toString(brightness), ""},
                                                  {Integer.toString(blink), ""}};


            for (String[] idvArray : tempArray){

                if (idvArray[0].toCharArray().length == 3){

                    idvArray[1] = idvArray[0];

                } else if (idvArray[0].toCharArray().length == 2){

                    idvArray[1] = "0" + idvArray[0];

                } else if (idvArray[0].toCharArray().length == 1){

                    idvArray[1] = "00" + idvArray[0];

                }
            }

            String outputString = tempArray[0][1] + "," + tempArray[1][1] + "," + tempArray[2][1] + "," + tempArray[3][1] + "," + tempArray[4][1];
            
            outputStream.write(outputString.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
