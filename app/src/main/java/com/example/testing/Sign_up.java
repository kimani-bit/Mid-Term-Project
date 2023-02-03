package com.example.testing;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_up extends AppCompatActivity {

    public EditText editTextTextPersonName3; //username
    public EditText editTextTextPersonName4; //password
    public EditText editTextTextPersonName5; //confirm password
    public Button button2; //sign up
    public Button button5;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //binding
        editTextTextPersonName3=findViewById(R.id.editTextTextPersonName3);
        editTextTextPersonName4=findViewById(R.id.editTextTextPersonName4);
        editTextTextPersonName5=findViewById(R.id.editTextTextPersonName5);
        button2=findViewById(R.id.button2);
        button5=findViewById(R.id.button5);
        DB=new DBHelper(this);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username=editTextTextPersonName3.getText().toString();
                String password=editTextTextPersonName4.getText().toString();
                String con_password=editTextTextPersonName5.getText().toString();

                if(username.isEmpty() || password.isEmpty() || con_password.isEmpty()){
                    Toast.makeText(Sign_up.this, "Please fill in the forms", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (password.equals(con_password)) {
                        Boolean checkinsertdata = DB.insertuserdata(username, password);
                        if (checkinsertdata == true)
                            Toast.makeText(Sign_up.this, "User successfully created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(Sign_up.this, "Error in user creation", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Sign_up.this, "Passwords are not identical", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(getApplicationContext(), Log_in.class);
                startActivity(myintent);
            }
        });
    }

}