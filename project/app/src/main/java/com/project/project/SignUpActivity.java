package com.project.project;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    TextView signup;
    EditText fname, lname,email,password,cpassword;
    Button buttonC, buttonB;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedPrefManager=SharedPrefManager.getInstance(this);
        signup= findViewById(R.id.textview);
        fname=findViewById(R.id.firstName);
        lname=findViewById(R.id.lastName);
        email=findViewById(R.id.EmailAddress);
        password=findViewById(R.id.Password);
        cpassword=findViewById(R.id.cPassword);

        buttonB=findViewById(R.id.back);
        buttonC=findViewById(R.id.createButton);

        Spinner spinner = findViewById(R.id.continentSpinner);

        String[] listContinent = {"Select your Favorite Continent","Asia", "Europe", "Africa", "North America"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listContinent);

        spinner.setAdapter(adapter);

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                SignUpActivity.this.startActivity(intent);
                finish();
            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().isEmpty()){
                    fname.setBackgroundColor(getResources().getColor(R.color.red2));
                    Toast toast =Toast.makeText(SignUpActivity.this, "You Must Fill Your First Name",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    fname.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if (fname.getText().toString().length()<3|| fname.getText().toString().length()>20 ){
                    Toast toast =Toast.makeText(SignUpActivity.this, " First Name can't be less than 3 characters and not more 20 characters",Toast.LENGTH_LONG);
                    toast.show();
                }


                if (lname.getText().toString().isEmpty()){
                    lname.setBackgroundColor(getResources().getColor(R.color.red2));
                    Toast toast =Toast.makeText(SignUpActivity.this, "You Must Fill Your Last Name",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    lname.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if (lname.getText().toString().length()<3|| lname.getText().toString().length()>20 ){
                    Toast toast =Toast.makeText(SignUpActivity.this, " Last Name can't be less than 3 characters and not more 20 characters",Toast.LENGTH_LONG);
                    toast.show();
                }


                if(email.getText().toString().isEmpty()){
                    email.setBackgroundColor(getResources().getColor(R.color.red2));
                    Toast toast =Toast.makeText(SignUpActivity.this, "You Must Fill Your Email",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    email.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(!email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
                    Toast toast =Toast.makeText(SignUpActivity.this, "Email is not Valid",Toast.LENGTH_LONG);
                    toast.show();
                }

                if(password.getText().toString().isEmpty()){
                    password.setBackgroundColor(getResources().getColor(R.color.red2));
                    Toast toast =Toast.makeText(SignUpActivity.this, "You Must Fill Your Password",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    password.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(password.getText().toString().length() < 8 || password.getText().toString().length() >15  ){
                    Toast toast =Toast.makeText(SignUpActivity.this, "password can't be less than 8 characters and not more 15 characters",Toast.LENGTH_LONG);
                    toast.show();
                }

                if(cpassword.getText().toString().isEmpty()){
                    cpassword.setBackgroundColor(getResources().getColor(R.color.red2));
                    Toast toast =Toast.makeText(SignUpActivity.this, "You Must Fill Confirm Password Field",Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    email.setBackgroundColor(getResources().getColor(R.color.white));
                }
                if(!password.getText().toString().equals(cpassword.getText().toString())){
                    Toast toast =Toast.makeText(SignUpActivity.this, "Passwords Isn't Matches",Toast.LENGTH_LONG);
                    toast.show();
                }
                else if(!Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,15}$").matcher(password.getText().toString()).matches()){
                    Toast toast =Toast.makeText(SignUpActivity.this, "Password Should include capital,small,number,special char",Toast.LENGTH_LONG);
                    toast.show();
                }

                else {
                    DataBaseHelper dataBaseHelper =new DataBaseHelper(SignUpActivity.this,"TravelDestination",null,1);
                    dataBaseHelper.insert(email.getText().toString(),fname.getText().toString(),lname.getText().toString(),
                            password.getText().toString(), spinner.getSelectedItem().toString());
                    Toast toast =Toast.makeText(SignUpActivity.this, "Successfully Registered",Toast.LENGTH_LONG);
                    toast.show();

                    sharedPrefManager.writeString("userName",email.getText().toString());
                    sharedPrefManager.writeString("password",password.getText().toString());
                    Toast.makeText(SignUpActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}
