package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_SMS extends AppCompatActivity {
    private TextView title;
    private EditText hpn, message;
    private Button send;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        title = findViewById(R.id.title);
        hpn=findViewById(R.id.hpn);
        message=findViewById(R.id.message);
        send=findViewById(R.id.send);

        hpn.setText("0757924738"); //Number
        Intent intentsend=getIntent();
        String message1=intentsend.getStringExtra("key1");

        DB=new DBHelper(this);

        message.setText(message1);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                        SendSMS();;
                    }else{
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }

                }

            }
        });
    }

    private void SendSMS(){
        String number=hpn.getText().toString().trim();
        String message1=message.getText().toString().trim();

        try{
            //call manager
            SmsManager smsManager=SmsManager.getDefault();

            //send the message
            smsManager.sendTextMessage(number,null,message1,null,null);
            Toast.makeText(this,"Message is sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this,"Message is Not Sent", Toast.LENGTH_SHORT).show();
        }
    }
}