package com.ratbox.synthrevolution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SynthTab3 extends Fragment {

    private Spinner                 configSpinner;
    private FloatingActionButton    btnSave;

    private CheckBox                chkbtn1,  chkbtn2,  chkbtn3,  chkbtn4,  chkbtn5,  chkbtn6,  chkbtn7,  chkbtn8,
                                    chkbtn9,  chkbtn10, chkbtn11, chkbtn12, chkbtn13, chkbtn14, chkbtn15, chkbtn16,
                                    chkbtn17, chkbtn18, chkbtn19, chkbtn20, chkbtn21, chkbtn22, chkbtn23, chkbtn24,
                                    chkbtn25, chkbtn26, chkbtn27, chkbtn28, chkbtn29, chkbtn30, chkbtn31, chkbtn32,
                                    chkbtn33, chkbtn34, chkbtn35, chkbtn36, chkbtn37, chkbtn38, chkbtn39, chkbtn40,
                                    chkbtn41, chkbtn42, chkbtn43, chkbtn44, chkbtn45, chkbtn46, chkbtn47, chkbtn48,
                                    chkbtn49, chkbtn50, chkbtn51, chkbtn52, chkbtn53, chkbtn54, chkbtn55, chkbtn56,
                                    chkbtn57, chkbtn58, chkbtn59, chkbtn60, chkbtn61, chkbtn62, chkbtn63, chkbtn64;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab3, container, false);

        // attaching to views
        configSpinner       = view.findViewById(R.id.ledConfigSelectSpinner);
        btnSave             = view.findViewById(R.id.saveConfigButton);

        chkbtn1     = view.findViewById(R.id.r1b1);
        chkbtn2     = view.findViewById(R.id.r1b2);
        chkbtn3     = view.findViewById(R.id.r1b3);
        chkbtn4     = view.findViewById(R.id.r1b4);
        chkbtn5     = view.findViewById(R.id.r1b5);
        chkbtn6     = view.findViewById(R.id.r1b6);
        chkbtn7     = view.findViewById(R.id.r1b7);
        chkbtn8     = view.findViewById(R.id.r1b8);

        chkbtn9     = view.findViewById(R.id.r2b9);
        chkbtn10    = view.findViewById(R.id.r2b10);
        chkbtn11    = view.findViewById(R.id.r2b11);
        chkbtn12    = view.findViewById(R.id.r2b12);
        chkbtn13    = view.findViewById(R.id.r2b13);
        chkbtn14    = view.findViewById(R.id.r2b14);
        chkbtn15    = view.findViewById(R.id.r2b15);
        chkbtn16    = view.findViewById(R.id.r2b16);

        chkbtn17    = view.findViewById(R.id.r3b17);
        chkbtn18    = view.findViewById(R.id.r3b18);
        chkbtn19    = view.findViewById(R.id.r3b19);
        chkbtn20    = view.findViewById(R.id.r3b20);
        chkbtn21    = view.findViewById(R.id.r3b21);
        chkbtn22    = view.findViewById(R.id.r3b22);
        chkbtn23    = view.findViewById(R.id.r3b23);
        chkbtn24    = view.findViewById(R.id.r3b24);

        chkbtn25    = view.findViewById(R.id.r4b25);
        chkbtn26    = view.findViewById(R.id.r4b26);
        chkbtn27    = view.findViewById(R.id.r4b27);
        chkbtn28    = view.findViewById(R.id.r4b28);
        chkbtn29    = view.findViewById(R.id.r4b29);
        chkbtn30    = view.findViewById(R.id.r4b30);
        chkbtn31    = view.findViewById(R.id.r4b31);
        chkbtn32    = view.findViewById(R.id.r4b32);

        chkbtn33    = view.findViewById(R.id.r5b33);
        chkbtn34    = view.findViewById(R.id.r5b34);
        chkbtn35    = view.findViewById(R.id.r5b35);
        chkbtn36    = view.findViewById(R.id.r5b36);
        chkbtn37    = view.findViewById(R.id.r5b37);
        chkbtn38    = view.findViewById(R.id.r5b38);
        chkbtn39    = view.findViewById(R.id.r5b39);
        chkbtn40    = view.findViewById(R.id.r5b40);

        chkbtn41    = view.findViewById(R.id.r6b41);
        chkbtn42    = view.findViewById(R.id.r6b42);
        chkbtn43    = view.findViewById(R.id.r6b43);
        chkbtn44    = view.findViewById(R.id.r6b44);
        chkbtn45    = view.findViewById(R.id.r6b45);
        chkbtn46    = view.findViewById(R.id.r6b46);
        chkbtn47    = view.findViewById(R.id.r6b47);
        chkbtn48    = view.findViewById(R.id.r6b48);

        chkbtn49    = view.findViewById(R.id.r7b49);
        chkbtn50    = view.findViewById(R.id.r7b50);
        chkbtn51    = view.findViewById(R.id.r7b51);
        chkbtn52    = view.findViewById(R.id.r7b52);
        chkbtn53    = view.findViewById(R.id.r7b53);
        chkbtn54    = view.findViewById(R.id.r7b54);
        chkbtn55    = view.findViewById(R.id.r7b55);
        chkbtn56    = view.findViewById(R.id.r7b56);

        chkbtn57    = view.findViewById(R.id.r8b57);
        chkbtn58    = view.findViewById(R.id.r8b58);
        chkbtn59    = view.findViewById(R.id.r8b59);
        chkbtn60    = view.findViewById(R.id.r8b60);
        chkbtn61    = view.findViewById(R.id.r8b61);
        chkbtn62    = view.findViewById(R.id.r8b62);
        chkbtn63    = view.findViewById(R.id.r8b63);
        chkbtn64    = view.findViewById(R.id.r8b64);

        // Creating an array of all the checkboxes
        final CheckBox[] checkBoxArr = {chkbtn1,  chkbtn2,  chkbtn3,  chkbtn4,  chkbtn5,  chkbtn6,  chkbtn7,  chkbtn8,
                                        chkbtn9,  chkbtn10, chkbtn11, chkbtn12, chkbtn13, chkbtn14, chkbtn15, chkbtn16,
                                        chkbtn17, chkbtn18, chkbtn19, chkbtn20, chkbtn21, chkbtn22, chkbtn23, chkbtn24,
                                        chkbtn25, chkbtn26, chkbtn27, chkbtn28, chkbtn29, chkbtn30, chkbtn31, chkbtn32,
                                        chkbtn33, chkbtn34, chkbtn35, chkbtn36, chkbtn37, chkbtn38, chkbtn39, chkbtn40,
                                        chkbtn41, chkbtn42, chkbtn43, chkbtn44, chkbtn45, chkbtn46, chkbtn47, chkbtn48,
                                        chkbtn49, chkbtn50, chkbtn51, chkbtn52, chkbtn53, chkbtn54, chkbtn55, chkbtn56,
                                        chkbtn57, chkbtn58, chkbtn59, chkbtn60, chkbtn61, chkbtn62, chkbtn63, chkbtn64};


        // Creating the array adapter, attaching it and setting the onClickListener
        ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, MainActivity.synthPattern.patternNameList);
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        configSpinner.setAdapter(patternAdapter);
        configSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Retrieving the currently selected array as its own array
                char[] selectedArray = MainActivity.synthPattern.patternConfList.get(position);

                if (position == 0){ // Disabling the save button for the default pattern

                    // Disable FAB
                    btnSave.setVisibility(View.GONE);

                    // Disable checkboxes
                    for (CheckBox c : checkBoxArr){
                        c.setEnabled(false);
                    }

                    // Setting the checkbox 8x8 pattern to the default pattern selected
                    for (int i = 0; i <= checkBoxArr.length - 1; i++){
                        if (selectedArray[i] == '1'){
                            checkBoxArr[i].setChecked(true);
                        } else {
                            checkBoxArr[i].setChecked(false);
                        }
                    }

                } else { // Enabling the buttons and normal behaviour for the FAB

                    // Enable FAB
                    btnSave.setVisibility(View.VISIBLE);

                    // Enable checkboxes
                    for (CheckBox c : checkBoxArr){
                        c.setEnabled(true);
                    }

                    // Setting the checkbox 8x8 pattern to the default pattern selected
                    for (int i = 0; i <= checkBoxArr.length - 1; i++){
                        if (selectedArray[i] == '1'){
                            checkBoxArr[i].setChecked(true);
                        } else {
                            checkBoxArr[i].setChecked(false);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Loading the default eye pattern onCreate()
        MainActivity.synthPattern.displayPattern(0, checkBoxArr);

        // Setting the onclick listener for the save pattern floating action button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO - behaviour for save pattern button

            }
        });


        // Returning the inflater view
        return view;
    }
}
