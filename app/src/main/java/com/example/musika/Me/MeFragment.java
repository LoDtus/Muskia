package com.example.musika.Me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.musika.Me.createAccount.activity_me_login;
import com.example.musika.Me.createAccount.activity_me_register;
import com.example.musika.R;
import com.example.musika.Welcome.HomePage;

public class MeFragment extends Fragment {
    Button register;
    Button login;
    Button later;
    public MeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_me, container, false);

        register = rootView.findViewById(R.id.register_xml);
        login = rootView.findViewById(R.id.login_xml);
        later = rootView.findViewById(R.id.later_xml);

        register.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), activity_me_register.class);
            startActivity(intent);
        });
        login.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), activity_me_login.class);
            startActivity(intent);
        });
        later.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), HomePage.class);
            startActivity(intent);
        });
        
        return rootView;
    }
}