package com.ratbox.synthrevolution.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ratbox.synthrevolution.R;

import java.util.ArrayList;
import java.util.List;

public class PatternRecyclerViewAdapter extends RecyclerView.Adapter<PatternRecyclerViewAdapter.PatternView> {

    private List<String>            nameList;
    private ArrayList<char[]>       confList;
    private Context                 mContext;

    public PatternRecyclerViewAdapter(Context context, List<String> nameList, ArrayList<char[]> confList) {
        this.nameList = nameList;
        this.confList = confList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PatternView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.synthpatterncard, parent, false);

        return new PatternView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatternView holder, int position) {

        holder.patternNameTextView.setText(nameList.get(position));

        // Creating the array to iterate though filled with all the views
        View[] viewGridArray = {holder.v1, holder.v2, holder.v3, holder.v4, holder.v5, holder.v6, holder.v7, holder.v8, holder.v9, holder.v10,
                holder.v11, holder.v12, holder.v13, holder.v14, holder.v15, holder.v16, holder.v17, holder.v18, holder.v19, holder.v20,
                holder.v21, holder.v22, holder.v23, holder.v24, holder.v25, holder.v26, holder.v27, holder.v28, holder.v29, holder.v30,
                holder.v31, holder.v32, holder.v33, holder.v34, holder.v35, holder.v36, holder.v37, holder.v38, holder.v39, holder.v40,
                holder.v41, holder.v42, holder.v43, holder.v44, holder.v45, holder.v46, holder.v47, holder.v48, holder.v49, holder.v50,
                holder.v51, holder.v52, holder.v53, holder.v54, holder.v55, holder.v56, holder.v57, holder.v58, holder.v59, holder.v60,
                holder.v61, holder.v62, holder.v63, holder.v64};

        // filling each of the holder's views with the correct 1/0
        for (int i = 0; i <= confList.get(position).length - 1; i++){
            if (confList.get(position)[i] == '1'){
                viewGridArray[i].setBackgroundColor(mContext.getResources().getColor(R.color.patternGridTrue));

            } else {
                viewGridArray[i].setBackgroundColor(mContext.getResources().getColor(R.color.patternGridFalse));

            }
        }
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class PatternView extends RecyclerView.ViewHolder{

        TextView    patternNameTextView;
        View        v1,   v2,  v3,  v4,  v5,  v6,  v7,  v8,  v9, v10,
                    v11, v12, v13, v14, v15, v16, v17, v18, v19, v20,
                    v21, v22, v23, v24, v25, v26, v27, v28, v29, v30,
                    v31, v32, v33, v34, v35, v36, v37, v38, v39, v40,
                    v41, v42, v43, v44, v45, v46, v47, v48, v49, v50,
                    v51, v52, v53, v54, v55, v56, v57, v58, v59, v60,
                    v61, v62, v63, v64;

        public PatternView(@NonNull View itemView) {
            super(itemView);

            patternNameTextView = itemView.findViewById(R.id.synthPatternNameTextView);

            v1      = itemView.findViewById(R.id.v1);
            v2      = itemView.findViewById(R.id.v2);
            v3      = itemView.findViewById(R.id.v3);
            v4      = itemView.findViewById(R.id.v4);
            v5      = itemView.findViewById(R.id.v5);
            v6      = itemView.findViewById(R.id.v6);
            v7      = itemView.findViewById(R.id.v7);
            v8      = itemView.findViewById(R.id.v8);
            v9      = itemView.findViewById(R.id.v9);
            v10     = itemView.findViewById(R.id.v10);

            v11     = itemView.findViewById(R.id.v11);
            v12     = itemView.findViewById(R.id.v12);
            v13     = itemView.findViewById(R.id.v13);
            v14     = itemView.findViewById(R.id.v14);
            v15     = itemView.findViewById(R.id.v15);
            v16     = itemView.findViewById(R.id.v16);
            v17     = itemView.findViewById(R.id.v17);
            v18     = itemView.findViewById(R.id.v18);
            v19     = itemView.findViewById(R.id.v19);
            v20     = itemView.findViewById(R.id.v20);

            v21      = itemView.findViewById(R.id.v21);
            v22      = itemView.findViewById(R.id.v22);
            v23      = itemView.findViewById(R.id.v23);
            v24      = itemView.findViewById(R.id.v24);
            v25      = itemView.findViewById(R.id.v25);
            v26      = itemView.findViewById(R.id.v26);
            v27      = itemView.findViewById(R.id.v27);
            v28      = itemView.findViewById(R.id.v28);
            v29      = itemView.findViewById(R.id.v29);
            v30      = itemView.findViewById(R.id.v30);

            v31      = itemView.findViewById(R.id.v31);
            v32      = itemView.findViewById(R.id.v32);
            v33      = itemView.findViewById(R.id.v33);
            v34      = itemView.findViewById(R.id.v34);
            v35      = itemView.findViewById(R.id.v35);
            v36      = itemView.findViewById(R.id.v36);
            v37      = itemView.findViewById(R.id.v37);
            v38      = itemView.findViewById(R.id.v38);
            v39      = itemView.findViewById(R.id.v39);
            v40      = itemView.findViewById(R.id.v40);

            v41      = itemView.findViewById(R.id.v41);
            v42      = itemView.findViewById(R.id.v42);
            v43      = itemView.findViewById(R.id.v43);
            v44      = itemView.findViewById(R.id.v44);
            v45      = itemView.findViewById(R.id.v45);
            v46      = itemView.findViewById(R.id.v46);
            v47      = itemView.findViewById(R.id.v47);
            v48      = itemView.findViewById(R.id.v48);
            v49      = itemView.findViewById(R.id.v49);
            v50      = itemView.findViewById(R.id.v50);

            v51      = itemView.findViewById(R.id.v51);
            v52      = itemView.findViewById(R.id.v52);
            v53      = itemView.findViewById(R.id.v53);
            v54      = itemView.findViewById(R.id.v54);
            v55      = itemView.findViewById(R.id.v55);
            v56      = itemView.findViewById(R.id.v56);
            v57      = itemView.findViewById(R.id.v57);
            v58      = itemView.findViewById(R.id.v58);
            v59      = itemView.findViewById(R.id.v59);
            v60      = itemView.findViewById(R.id.v60);

            v61      = itemView.findViewById(R.id.v61);
            v62      = itemView.findViewById(R.id.v62);
            v63      = itemView.findViewById(R.id.v63);
            v64      = itemView.findViewById(R.id.v64);
        }
    }
}
