package com.example.pollandvote.Admin.Polls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Utils.CalculationFunctions;
import com.example.pollandvote.R;
import com.example.pollandvote.models.PollData;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;
import java.util.Map;

public class PollDataAdapter extends RecyclerView.Adapter<PollDataAdapter.PollViewHolder> {
    private static final String TAG = "PollDataAdapter";
    private List<PollData> pollDataList;
    private Context context;

    public PollDataAdapter(List<PollData> pollDataList, Context context) {
        this.pollDataList = pollDataList;
        this.context = context;
    }

    public PollDataAdapter(List<PollData> pollDataList) {
        this.pollDataList = pollDataList;
    }

    @NonNull
    @Override
    public PollViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item_poll_2, parent, false);
        return new PollViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PollViewHolder holder, int position) {
        PollData pollData = pollDataList.get(position);

        holder.questionTextView.setText(pollData.getQuestion());
        holder.optionsLayout.removeAllViews();
        int totalCount = pollData.getTotalCount();
        Log.d(TAG, "onBindViewHolder: total Count "+totalCount);

        Map<String, Map<String, Object>> options = pollData.getOptions();
        // Display poll options
        /////////////////////////////////////////
        for (Map.Entry<String, Map<String, Object>> option: options.entrySet()){
            Map<String, Object> innerMap = option.getValue();
            String optionName = (String) innerMap.get(context.getString(R.string.dbname_polloption));

            Log.d(TAG, "onBindViewHolder: "+innerMap);
            Long countString = (Long) innerMap.get(context.getString(R.string.dbname_count));
            long longValue = countString.longValue(); // Extract long value

//            Integer count = Integer.parseInt((String) countString);
            Log.d(TAG, "onBindViewHolder: "+countString);
            int count = 0;

            if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                count = (int) longValue; // Cast to int if within the valid range
                Log.d(TAG, "onBindViewHolder: con"+count);
            } else {
                System.out.println("Long value is outside the valid int range");
            }

            View optionView = LayoutInflater.from(holder.itemView.getContext())
                    .inflate(R.layout.item_option, holder.optionsLayout, false);

            TextView optionTextView = optionView.findViewById(R.id.optionTextView);

            LinearProgressIndicator progressIndicator = optionView.findViewById(R.id.progressIndicator);
            TextView votePercentageTextView = optionView.findViewById(R.id.votePercentageTextView);

            optionTextView.setText(optionName);
            String pollPercentage = CalculationFunctions.calculateVotePercent(totalCount, count);

            Double pollPercentageDouble = Double.parseDouble(pollPercentage);

            int pollPercentageInt = pollPercentageDouble.intValue();
            progressIndicator.setProgress(pollPercentageInt, true);
            votePercentageTextView.setText(pollPercentage+"%");

            holder.optionsLayout.addView(optionView);
        }


    }

    @Override
    public int getItemCount() {
        return pollDataList.size();
    }

    static class PollViewHolder extends RecyclerView.ViewHolder {
        TextView questionTextView;
        TextView optionsTextView;
        LinearLayout optionsLayout;

        PollViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsTextView = itemView.findViewById(R.id.optionsTextView);
           // pollIdTextView = itemView.findViewById(R.id.pollIdTextView);
            optionsLayout = itemView.findViewById(R.id.optionsLayout);


        }
    }


}
