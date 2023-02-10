package com.project.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileFragment extends Fragment {

    public ProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getContext());
        String email = dataBaseHelper.getEmail();
        TextView firstname = (TextView) view.findViewById(R.id.first_name);
        TextView lastname = (TextView) view.findViewById(R.id.last_name);
        TextView password = (TextView) view.findViewById(R.id.password);
        String continent = dataBaseHelper.getContinent(email);
        Spinner spinner = (Spinner) view.findViewById(R.id.continentSpinner);
        String[] continentList = {continent,"Asia", "Europe", "Africa", "North America"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, continentList);
        spinner.setAdapter(adapter);

        DataBaseHelper dataBaseHelper1 = new DataBaseHelper(getContext());
        Cursor cursorName = dataBaseHelper1.getFullName();
        if (cursorName.getCount() != 0) {
            while (cursorName.moveToNext()) {
                firstname.setText(cursorName.getString(0));
                lastname.setText(cursorName.getString(1));
            }
        }
        Cursor cursorPassword = dataBaseHelper1.getPassword();
        if (cursorPassword.getCount() != 0) {
            while (cursorPassword.moveToNext()) {
                password.setText(cursorPassword.getString(0));
            }
        }

        Button button = (Button) view.findViewById(R.id.editButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper1 = new DataBaseHelper(getContext());
                dataBaseHelper1.update(email,firstname.getText().toString(),lastname.getText().toString(),password.getText().toString(),(String) spinner.getSelectedItem());
                Toast.makeText(getActivity(), "Successful Editing", Toast.LENGTH_LONG).show();

            }
        });


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}