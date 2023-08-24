package com.example.pollandvote.chatbot;

import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.pollandvote.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MessageRVAdapter extends RecyclerView.Adapter {

    // variable for our array list and context.
    private ArrayList<Message> messageModalArrayList;
    private Context context;

    // constructor class.
    public MessageRVAdapter(ArrayList<Message> messageModalArrayList, Context context) {
        this.messageModalArrayList = messageModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // below code is to switch our
        // layout type along with view holder.
//        switch (viewType) {
//            case 0:
//                // below line we are inflating user message layout.
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
//                return new UserViewHolder(view);
//            case 1:
//                // below line we are inflating bot message layout.
//                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_item, parent, false);
//                return new BotViewHolder(view);
//        }
//        return null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // this method is use to set data to our layout file.
        Message modal = messageModalArrayList.get(position);
//        switch (modal.getSender()) {
//            case "user":
//                // below line is to set the text to our text view of user layout
//                ((UserViewHolder) holder).userTV.setText(modal.getMessage());
//                break;
//            case "bot":
//            {
//                try{
//                    // below line is to set the text to our text view of bot layout
//                    ((BotViewHolder) holder).botTV.setText(Html.fromHtml(modal.getMessage()));
//                    ((BotViewHolder) holder).botTV.setMovementMethod(LinkMovementMethod.getInstance());
//                    break;
//                }catch (Exception e){
//                    Log.d("Akash","Null "+e);
//                }
//
//            }
//
//        }
       // Message message = messageList.get(position);
        if (modal.getSender().equals("me")){
            ((MyViewHolder)holder).left_chat_view.setVisibility(View.GONE);
            ((MyViewHolder)holder).right_chat_view.setVisibility(View.VISIBLE);
            ((MyViewHolder)holder).right_chat_text_view.setText(modal.getMessage());
        } else {
            ((MyViewHolder)holder).right_chat_view.setVisibility(View.GONE);
            ((MyViewHolder)holder).left_chat_view.setVisibility(View.VISIBLE);
            ((MyViewHolder)holder).left_chat_text_view.setText(modal.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        // return the size of array list
        return messageModalArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // below line of code is to set position.
        switch (messageModalArrayList.get(position).getSender()) {
            case "user":
                return 0;
            case "bot":
                return 1;
            default:
                return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        // creating a variable
        // for our text view.
        TextView userTV;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            userTV = itemView.findViewById(R.id.idTVUser);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder {

        // creating a variable
        // for our text view.
        TextView botTV;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing with id.
            botTV = itemView.findViewById(R.id.idTVBot);
        }
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView left_chat_view, right_chat_view;
        TextView left_chat_text_view, right_chat_text_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            left_chat_view = itemView.findViewById(R.id.left_chat_view);
            right_chat_view = itemView.findViewById(R.id.right_chat_view);
            left_chat_text_view = itemView.findViewById(R.id.left_chat_text_view);
            right_chat_text_view = itemView.findViewById(R.id.right_chat_text_view);



        }
    }
}
