package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Water_portal extends AppCompatActivity {

    int previous_reading, current_reading,amount_paid, rate, phone_number;
    Integer usage_calc, balance_calc;

    public EditText editTextTextPersonName7; //house number
    public EditText editTextTextPersonName8; //previous reading
    public EditText editTextTextPersonName9; //current reading
    public EditText editTextTextPersonName10; //rate
    public EditText editTextTextPersonName11; //amount paid
    public EditText editTextTextPersonName12; //balance
    public EditText editTextTextPersonName19; //month
    public EditText editTextTextPersonName24; //phone_number

    public Button button8; //balance
    public Button button15; //usage
    public Button button10; //DB
    public Button button9; //SMS
    public Button button16; //update DB record
    public Button button20; //previous reading obtain

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_portal);

        //Binding
        editTextTextPersonName7=findViewById(R.id.editTextTextPersonName7);
        editTextTextPersonName8=findViewById(R.id.editTextTextPersonName8);
        editTextTextPersonName9=findViewById(R.id.editTextTextPersonName9);
        editTextTextPersonName10=findViewById(R.id.editTextTextPersonName10);
        editTextTextPersonName11=findViewById(R.id.editTextTextPersonName11);
        editTextTextPersonName12=findViewById(R.id.editTextTextPersonName12);
        editTextTextPersonName19=findViewById(R.id.editTextTextPersonName19);
        editTextTextPersonName24=findViewById(R.id.editTextTextPersonName24);

        button8=findViewById(R.id.button8);
        button15=findViewById(R.id.button15);
        button10=findViewById(R.id.button10);
        button9=findViewById(R.id.button9);
        button16=findViewById(R.id.button16);
        button20=findViewById(R.id.button20);

        DB = new DBHelper(this);

        //if there is username who has no previous water bill:
        //textView.setVisibility(View.GONE);
        //else
        //pull the record
        //then editTextTextPersonName12.setVisibility(View.GONE);

        //Usage calculation 1 unit=1000L
        //rate obtain
        Cursor res = DB.getdata("water");
        while(res.moveToNext()){
            rate = res.getInt(1);
        }


        //previous reading obtain
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res1 = DB.obtainwater(editTextTextPersonName7.getText().toString());
                while(res1.moveToNext()){
                    previous_reading = res1.getInt(1);
                }
                editTextTextPersonName8.setText(String.valueOf(previous_reading));
            }
        });

        //usage
        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=editTextTextPersonName9.getText().toString(); //current reading
                String b=editTextTextPersonName8.getText().toString(); //previous reading

                previous_reading=Integer.parseInt(a);
                current_reading=Integer.parseInt(b);

                usage_calc=(previous_reading-current_reading)*rate;
                editTextTextPersonName10.setText(usage_calc.toString());
            }
        });

        //balance
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String c=editTextTextPersonName10.getText().toString(); //Usage
                String d=editTextTextPersonName11.getText().toString(); //Amount paid
                String e=editTextTextPersonName12.getText().toString(); //Balance

                usage_calc=Integer.parseInt(c);
                amount_paid=Integer.parseInt(d);

                balance_calc=(amount_paid-usage_calc);
                editTextTextPersonName12.setText(balance_calc.toString());

                if (balance_calc==0)
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Payment successful with no balance";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else
                {
                    Context context = getApplicationContext();
                    CharSequence text = "Payment successful with debt. Kindly check balance";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

            }
        });

        //send to DB
        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check=DB.insertwaterdata(editTextTextPersonName7.getText().toString(),
                        inta(editTextTextPersonName8),
                        inta(editTextTextPersonName9),
                        inta(editTextTextPersonName10),
                        inta(editTextTextPersonName11),
                        inta(editTextTextPersonName12),
                        inta(editTextTextPersonName19),
                        inta(editTextTextPersonName24));
                if (check)
                {
                    Toast.makeText(Water_portal.this, "Added successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Water_portal.this, "Record not added", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //update data
        button16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String water_hse_numbertxt=editTextTextPersonName7.getText().toString();
                Integer water_previous_readingtxt=Integer.valueOf(editTextTextPersonName8.getText().toString());
                Integer water_current_readingtxt=Integer.valueOf(editTextTextPersonName9.getText().toString());
                Integer usage_watertxt=Integer.valueOf(editTextTextPersonName10.getText().toString());
                Integer water_amount_paidtxt=Integer.valueOf(editTextTextPersonName11.getText().toString());
                Integer water_balancetxt=Integer.valueOf(editTextTextPersonName12.getText().toString());
                Integer water_monthtxt=Integer.valueOf(editTextTextPersonName19.getText().toString());
                Integer phone_numbertxt=Integer.valueOf(editTextTextPersonName24.getText().toString());

                Boolean checkupdatedata=DB.updatewater(water_hse_numbertxt, water_previous_readingtxt,
                        water_current_readingtxt, usage_watertxt, water_amount_paidtxt, water_balancetxt, water_monthtxt,
                        phone_numbertxt);
                if(checkupdatedata==true)
                    Toast.makeText(Water_portal.this," Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Water_portal.this," Entry Not Updated",Toast.LENGTH_SHORT).show();

            }
        });

        //SMS
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a=editTextTextPersonName8.getText().toString(); //house number
                Integer b=Integer.valueOf(editTextTextPersonName8.getText().toString()); //previous
                Integer c=Integer.valueOf(editTextTextPersonName9.getText().toString()); //current
                Integer d=Integer.valueOf(editTextTextPersonName10.getText().toString()); //usage
                Integer g=Integer.valueOf(editTextTextPersonName19.getText().toString()); //month
                String message="You water bills is as follows: House number"+a+"has a previous reading of"+b+
                                "current reading of"+c+"usage of"+d+"for the month of"+g;
                Intent intentsend=new Intent(getApplicationContext(), Activity_SMS.class);
                intentsend.putExtra("key1", message);
                startActivity(intentsend);
            }
        });

    }
    //insert water data function
    public int inta(EditText editText){
        return Integer.valueOf(editText.getText().toString());
    }



}
