package com.example.pollandvote.Admin.Profiles;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pollandvote.R;


public class HelpFragment extends Fragment {

    TextView tvVersion, tvEmail;
    ImageView imgClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_help, container, false);

        tvEmail = view.findViewById(R.id.tvEmail);
        tvVersion = view.findViewById(R.id.tvVersion);
        imgClose = view.findViewById(R.id.imgClose);

        String versionName = null;
        try {
            versionName = getActivity().getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvVersion.setText(versionName+"");

        tvEmail.setText("votingpoll.akrs@gmail.com");

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