package com.example.pollandvote.Admin.Profiles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pollandvote.Admin.AdminProfile;
import com.example.pollandvote.R;


public class PasswordChangeFragment extends Fragment {
    ImageView imgClose;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_password_change, container, false);
        imgClose = view.findViewById(R.id.imgClose);




        /**
         * for close fragment
         */
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().getSupportFragmentManager().getBackStackEntryCount() > 1){
                    getActivity().getSupportFragmentManager().popBackStack();
                }
                else{
                    AdminProfile.adminLayout.setVisibility(View.VISIBLE);
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            }
        });


        return view;
    }
}