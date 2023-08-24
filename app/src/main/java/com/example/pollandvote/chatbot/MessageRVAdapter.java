package com.example.pollandvote.chatbot;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pollandvote.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MessageRVAdapter extends RecyclerView.Adapter<MessageRVAdapter.MyViewHolder> {

    // variable for our array list and context.
    private final ArrayList<Message> messageModalArrayList;

    // constructor class.
    public MessageRVAdapter(ArrayList<Message> messageModalArrayList) {
        this.messageModalArrayList = messageModalArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // this method is use to set data to our layout file.
        Message modal = messageModalArrayList.get(position);
        if (modal.getSender().equals("user")){
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
