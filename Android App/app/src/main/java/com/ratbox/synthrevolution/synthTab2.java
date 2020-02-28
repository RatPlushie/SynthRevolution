package com.ratbox.synthrevolution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class synthTab2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab2, container, false);


        // TODO - Add recycler view to show a grid of all available/saved eye patterns/animations

        // TODO - Push ~5 of these patterns to connected synth visor (Depending on amount of storage space on arduino

        // TODO - Store the default synth eye pattern

        return view;
    }
}
