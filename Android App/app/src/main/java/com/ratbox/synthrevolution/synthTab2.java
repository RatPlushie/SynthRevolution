package com.ratbox.synthrevolution;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ratbox.synthrevolution.ui.main.PatternRecyclerViewAdapter;
import com.ratbox.synthrevolution.ui.main.SynthPattern;

public class synthTab2 extends Fragment {

    private RecyclerView patternRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab2, container, false);


        // TODO - Add recycler view to show a grid of all available/saved eye patterns/animations

        // TODO - Push ~5 of these patterns to connected synth visor (Depending on amount of storage space on arduino

        // TODO - Store the default synth eye pattern

        // Attaching views
        patternRecyclerView = view.findViewById(R.id.synthPatternRecyclerView);


        // Creating the synthPattern object
        SynthPattern synthPattern = new SynthPattern(getContext());


        // Initialising the gridLayout for the pattern recyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        patternRecyclerView.setLayoutManager(gridLayoutManager);

        patternRecyclerView.setAdapter(new PatternRecyclerViewAdapter(getContext(), synthPattern.patternNameList, synthPattern.patternConfList));













        return view;
    }
}
