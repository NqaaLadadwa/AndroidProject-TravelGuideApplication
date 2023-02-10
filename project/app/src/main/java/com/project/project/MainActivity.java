package com.project.project;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
       TextView login;
       EditText email,password;
       Button buttonC,buttonL;
       CheckBox checkBox;
       ImageView img;
      SharedPrefManager sharedPrefManager;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = SharedPrefManager.getInstance(this);
        login = findViewById(R.id.textview);
        img = findViewById(R.id.img);
        email = findViewById(R.id.EmailAddress);
        password = findViewById(R.id.Password);
        checkBox=findViewById(R.id.checkBox);


        sharedPrefManager = SharedPrefManager.getInstance(this);
        if (!sharedPrefManager.readString("userName", "NOVALUE").equals("NOVALUE")) {
            email.setText(sharedPrefManager.readString("userName", "NOVALUE"));
            password.setText(sharedPrefManager.readString("password","NOVALUE"));
            checkBox.setChecked(true);
        }

        buttonL=findViewById(R.id.loginButton);
        buttonC=findViewById(R.id.signupbutton);
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }

        });
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if the email and password are filled
                if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast toast = Toast.makeText(MainActivity.this, "Blank Must Be Filled", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    //if the email and password are exist in database
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this, "TravelDestination", null, 1);
                    boolean isExisted = dataBaseHelper.remember(email.getText().toString(), password.getText().toString());
                    if (isExisted) {
                        // if the remember me box selected save the email and password in a sharedPref
                        if (checkBox.isChecked()) {
                            sharedPrefManager.writeString("userName", email.getText().toString());
                            sharedPrefManager.writeString("password", password.getText().toString());
                            Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                        }

                        Toast toast = Toast.makeText(MainActivity.this, "Successful SignIn", Toast.LENGTH_LONG);
                        toast.show();
                        Intent intent = new Intent(MainActivity.this, NavigationActivity.class);
                        MainActivity.this.startActivity(intent);
                        OpenMainPage(email.getText().toString());
                        finish();


                    } else {


                        Toast toast = Toast.makeText(MainActivity.this, "Something is Wrong", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

    }
    public void OpenMainPage(String email) {
        startActivity(new Intent(MainActivity.this,NavigationActivity.class));
    }
}
