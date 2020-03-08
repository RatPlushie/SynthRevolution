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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ratbox.synthrevolution.ui.main.PatternRecyclerViewAdapter;

import static com.ratbox.synthrevolution.MainActivity.synthPattern;

public class synthTab2 extends Fragment {

    private RecyclerView patternRecyclerView;

    private FloatingActionButton addActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.synth_tab2, container, false);

        // Attaching views
        patternRecyclerView = view.findViewById(R.id.synthPatternRecyclerView);
        addActionButton = view.findViewById(R.id.addActionButton);

        // Initialising the gridLayout for the pattern recyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        patternRecyclerView.setLayoutManager(gridLayoutManager);
        final PatternRecyclerViewAdapter patternRecyclerViewAdapter = new PatternRecyclerViewAdapter(getContext(), synthPattern.patternNameList, synthPattern.patternConfList);
        patternRecyclerView.setAdapter(patternRecyclerViewAdapter);

        addActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Array of a blank, all off, 8x8 LEDs
                char[] blankPattern = {'0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0',
                                       '0', '0', '0', '0', '0', '0', '0', '0'};

                // Adding the new values to the list
                synthPattern.patternNameList.add("New Pattern");
                synthPattern.patternConfList.add(blankPattern);

                // Notifying the adapter that a new entry has been inserted
                patternRecyclerViewAdapter.notifyItemInserted(synthPattern.patternNameList.size() - 1);
            }
        });

        // TODO - add hold to delete functionality

        return view;
    }
}
