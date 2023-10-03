package com.example.pollandvote.Voter.user_poll;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.Admin.Utils.CalculationFunctions;
import com.example.pollandvote.R;
import com.example.pollandvote.models.PollData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Map;

public class OptionAdaptorUser extends RecyclerView.Adapter<OptionAdaptorUser.OptionViewHolder> {

    private static final String TAG = "OptionAdaptorUser";
    private List<PollData> pollDataList;
    private boolean checkSelected = false;
    private DatabaseReference reference;
    private OptionViewHolder optionViewHolder;

    private Context context;

    public OptionAdaptorUser(List<PollData> pollDataList, Context context) {
        this.pollDataList = pollDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_poll_example, parent, false);
        Log.d(TAG, "onCreateViewHolder: ");
        return new OptionViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return pollDataList.size();
    }


    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        PollData pollData = pollDataList.get(position);

        Log.d(TAG, "onBindViewHolder: option adapter...");
        holder.questionTextView.setText(pollData.getQuestion());
        optionViewHolder = holder;

        reference = FirebaseDatabase.getInstance().getReference(context.getString(R.string.dbname_adminpolldata));

        holder.optionsRadioGroup.removeAllViews();

        int totalCount = pollData.getTotalCount();
        Log.d(TAG, "onBindViewHolder: total Count "+totalCount);

        Map<String, Map<String, Object>> options = pollData.getOptions();

        for (Map.Entry<String, Map<String, Object>> option: options.entrySet()){
            Map<String, Object> innerMap = option.getValue();
            String optionName = (String) innerMap.get(context.getString(R.string.dbname_polloption));
            String s = option.getKey().charAt(option.getKey().length()-1)+"";

            int index  = Integer.parseInt(s);
            Log.d(TAG, "onBindViewHolder: optionName: "+index+ " "+optionName +" option "+option.getKey()+" string "+s);


            Log.d(TAG, "onBindViewHolder: "+innerMap);
            Long countString = (Long) innerMap.get(context.getString(R.string.dbname_count));
            long longValue = countString.longValue(); // Extract long value
            Log.d(TAG, "onBindViewHolder: "+countString);
            int count = 0;

            if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
                count = (int) longValue; // Cast to int if within the valid range
                Log.d(TAG, "onBindViewHolder: con"+count);
            } else {
                System.out.println("Long value is outside the valid int range");
            }
//            View optionView = LayoutInflater.from(holder.itemView.getContext())
//                    .inflate(R.layout.item_option_user, holder.optionsLayout, false);

            RadioButton radioButton = new RadioButton(holder.itemView.getContext());
            radioButton.setText(optionName);
            radioButton.setId(index);

            Log.d(TAG, "onBindViewHolder: index and option name : "+index+" "+optionName);

            holder.optionsRadioGroup.addView(radioButton);

            if (pollData.isCheckSelected()){
                showIndicator(pollData);
            }

            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.optionsRadioGroup.removeView(radioButton);

                   onOptionClicked(pollData, radioButton.getId());


                    // Re-add the RadioButton to the RadioGroup
                    holder.optionsRadioGroup.addView(radioButton);

                    // Set the RadioButton as selected
                    radioButton.setChecked(true);

                }
            });

        }
        }

    private void onOptionClicked(PollData pollData, int selectedOptionIndex) {
        incrementOptionCount(pollData, selectedOptionIndex);

        Log.d(TAG, "onOptionClicked: selected option "+ selectedOptionIndex);
        // You can update UI to show the selected option or perform any other actions here
    }
    private void incrementOptionCount(PollData pollData, int selectedOptionIndex) {

        // Get the selected option key from the pollData
//        String[] optionKeys = pollData.getOptions().keySet().toArray(new String[0]);
//        String selectedOptionKey = optionKeys[selectedOptionIndex];
        String option_num = "option_"+selectedOptionIndex;


        Map<String, Map<String, Object>> allData = pollData.getOptions();

        Map<String, Object> innerMap = allData.get(option_num);

       Long count = (Long) innerMap.get(context.getString(R.string.dbname_count));

       count = count+1L;
       innerMap.put(context.getString(R.string.dbname_count), count);
       allData.put(option_num, innerMap);

        Log.d(TAG, "incrementOptionCount: inner Map "+innerMap);

//        Log.d(TAG, "incrementOptionCount: selectedOptionKey : "+Arrays.toString(optionKeys));
        Log.d(TAG, "incrementOptionCount: "+pollData.getTotalCount());
        pollData.setTotalCount(pollData.getTotalCount()+1);

        pollData.setCheckSelected(true);
        Log.d(TAG, "incrementOptionCount: Poll ID: "+pollData.getPollId());

//        try {
//            Thread.sleep(5000);
//        }catch (Exception e){
//            Log.d(TAG, "incrementOptionCount: "+ e.getMessage());
//        }

        reference.child(pollData.getPollId()).setValue(pollData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "Data added to Realtime Database", Toast.LENGTH_SHORT).show();
//                        showIndicator(pollData);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error adding data to Realtime Database: " + e.getMessage());
                        Toast.makeText(context, "Error adding data to Realtime Database", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void showIndicator(PollData pollData){

        optionViewHolder.optionsLayout.removeAllViews();
        Log.d(TAG, "showIndicator: optionViewHolder: "+optionViewHolder);

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

            View optionView = LayoutInflater.from(optionViewHolder.itemView.getContext())
                    .inflate(R.layout.item_option, optionViewHolder.optionsLayout, false);

            TextView optionTextView = optionView.findViewById(R.id.optionTextView);

            LinearProgressIndicator progressIndicator = optionView.findViewById(R.id.progressIndicator);
            TextView votePercentageTextView = optionView.findViewById(R.id.votePercentageTextView);

            optionTextView.setText(optionName);
            String pollPercentage = CalculationFunctions.calculateVotePercent(totalCount, count);

            Double pollPercentageDouble = Double.parseDouble(pollPercentage);

            int pollPercentageInt = pollPercentageDouble.intValue();
            progressIndicator.setProgress(pollPercentageInt, true);
            votePercentageTextView.setText(pollPercentage+"%");

            optionViewHolder.optionsLayout.addView(optionView);
        }
    }
    static class OptionViewHolder extends RecyclerView.ViewHolder{

        TextView questionTextView;
        RadioGroup optionsRadioGroup;
        LinearLayout optionsLayout;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);

            Log.d(TAG, "OptionViewHolder: data init");
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
            optionsLayout = itemView.findViewById(R.id.optionsLayout);

        }
    }
}
