package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Log_in extends AppCompatActivity {
//Welcome page
    public EditText editTextTextPersonName;//username
    public EditText editTextTextPersonName2;//password
    public Button button; //log in
    public Button button18; //admin log in

    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Binding
        editTextTextPersonName=findViewById(R.id.editTextTextPersonName);
        editTextTextPersonName2=findViewById(R.id.editTextTextPersonName2);
        button=findViewById(R.id.button);
        button18=findViewById(R.id.button18);
        DB = new DBHelper(this);

        //default electricity rate before admin change
        //DB.insertrates("electricity", 25.20);
        //DB.insertrates("water", 120.00);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=editTextTextPersonName.getText().toString();
                String password=editTextTextPersonName2.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(Log_in.this, "Please fill in the forms", Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean checkCred = DB.checkpassword(username,password);{
                        if(checkCred == true){
                            Toast.makeText(Log_in.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), Selection_pane.class);
                            startActivity(intent);
                        }
                    }
                }

            }
        });

        //admin log in
        button18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextTextPersonName.getText().toString().equals("admin") &&
                        editTextTextPersonName2.getText().toString().equals("admin")) {
                    Intent intent=new Intent(getApplicationContext(), Admin_portal.class);
                    startActivity(intent);
                }
            }
        });
    }
}