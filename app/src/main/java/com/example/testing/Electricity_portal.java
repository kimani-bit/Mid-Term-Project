package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Electricity_portal extends AppCompatActivity {

    double previous_reading, current_reading, amount_paid, rate;
    double usage_calc;

    public EditText editTextTextPersonName13; //house number
    public EditText editTextTextPersonName14; //previous reading
    public EditText editTextTextPersonName15; //current reading
    public EditText editTextTextPersonName16; //usage
    public EditText editTextTextPersonName17; //amount paid
    public EditText editTextTextPersonName18; //balance
    public EditText editTextTextPersonName20; //month
    public EditText editTextTextPersonName23; //phone number

    public Button button11; //balance
    public Button button12; //sms
    public Button button13; //usage
    public Button button14; //database
    public Button button17; //update
    public Button button21; //previous reading

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_portal);

        //binding
        editTextTextPersonName13=findViewById(R.id.editTextTextPersonName13);
        editTextTextPersonName14=findViewById(R.id.editTextTextPersonName14);
        editTextTextPersonName15=findViewById(R.id.editTextTextPersonName15);
        editTextTextPersonName16=findViewById(R.id.editTextTextPersonName16);
        editTextTextPersonName17=findViewById(R.id.editTextTextPersonName17);
        editTextTextPersonName18=findViewById(R.id.editTextTextPersonName18);
        editTextTextPersonName20=findViewById(R.id.editTextTextPersonName20);
        editTextTextPersonName23=findViewById(R.id.editTextTextPersonName23);

        button11=findViewById(R.id.button11);
        button12=findViewById(R.id.button12);
        button13=findViewById(R.id.button13);
        button14=findViewById(R.id.button14);
        button17=findViewById(R.id.button17);
        button21=findViewById(R.id.button21);

        DB = new DBHelper(this);

        //electricity rate derivation
        Cursor res = DB.getdata("electricity");
        while(res.moveToNext()) {
            rate=res.getDouble(1);
        }

        //previous reading
        button21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res1 = DB.obtainelectricity(editTextTextPersonName13.getText().toString());
                while(res1.moveToNext()){
                    previous_reading = res1.getInt(1);
                }
                editTextTextPersonName14.setText(String.valueOf(previous_reading));
            }
        });

        //usage calculation
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a=editTextTextPersonName15.getText().toString(); //current reading
                String b=editTextTextPersonName14.getText().toString(); //previous reading

                previous_reading=Double.parseDouble(a);
                current_reading=Double.parseDouble(b);

                double usage_calc=(previous_reading-current_reading)*rate;
                String final_usage_calc= new Double(usage_calc).toString();
                editTextTextPersonName16.setText(final_usage_calc);

            }
        });

        //balance
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String c=editTextTextPersonName16.getText().toString(); //Usage
                String d=editTextTextPersonName17.getText().toString(); //Amount paid

                usage_calc=Double.parseDouble(c);
                amount_paid=Double.parseDouble(d);

                double balance_calc=(amount_paid-usage_calc);
                String final_balance_calc=new Double(balance_calc).toString();
                editTextTextPersonName18.setText(final_balance_calc);

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

        //Send to the DB
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check=DB.insertelectricitydata(editTextTextPersonName13.getText().toString(),
                        doublea(editTextTextPersonName14),
                        doublea(editTextTextPersonName15),
                        doublea(editTextTextPersonName16),
                        doublea(editTextTextPersonName17),
                        doublea(editTextTextPersonName18),
                        Integer.valueOf(editTextTextPersonName20.getText().toString()),
                        Integer.valueOf(editTextTextPersonName23.getText().toString()));
                if (check)
                {
                    Toast.makeText(Electricity_portal.this, "Added successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Electricity_portal.this, "Record not added", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //update electricity record
        button17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String electricity_hse_numbertxt=editTextTextPersonName13.getText().toString();
                Double electricity_previous_readingtxt=Double.valueOf(editTextTextPersonName14.getText().toString());
                Double electricity_current_readingtxt=Double.valueOf(editTextTextPersonName15.getText().toString());
                Double usage_electricitytxt=Double.valueOf(editTextTextPersonName16.getText().toString());
                Double electricity_amount_paidtxt=Double.valueOf(editTextTextPersonName17.getText().toString());
                Double electricity_balancetxt=Double.valueOf(editTextTextPersonName18.getText().toString());
                Integer electricity_monthtxt=Integer.valueOf(editTextTextPersonName20.getText().toString());
                Integer phone_numbertxt=Integer.valueOf(editTextTextPersonName23.getText().toString());

                Boolean checkupdatedata=DB.updateelectricity(electricity_hse_numbertxt, electricity_previous_readingtxt,
                        electricity_current_readingtxt, usage_electricitytxt, electricity_amount_paidtxt, electricity_balancetxt,
                        electricity_monthtxt, phone_numbertxt);
                if(checkupdatedata==true)
                    Toast.makeText(Electricity_portal.this," Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Electricity_portal.this," Entry Not Updated",Toast.LENGTH_SHORT).show();
            }
        });

        //SMS
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i=editTextTextPersonName13.getText().toString(); //number
                Double j=Double.valueOf(editTextTextPersonName14.getText().toString()); //previous
                Double k=Double.valueOf(editTextTextPersonName15.getText().toString()); //current
                Double l=Double.valueOf(editTextTextPersonName16.getText().toString()); //usage
                Integer o=Integer.valueOf(editTextTextPersonName20.getText().toString()); //month

                String message="You electrcity bills is as follows: House number"+i+"has a previous reading of"+j+
                        "current reading of"+k+"usage of"+l+"for the month of"+o;
                Intent intentsend=new Intent(getApplicationContext(), Activity_SMS.class);
                intentsend.putExtra("key1", message);
                startActivity(intentsend);
            }
        });
    }

    //function for data insertion
    public double doublea(EditText editText){
        return Double.valueOf(editText.getText().toString());
    }

}