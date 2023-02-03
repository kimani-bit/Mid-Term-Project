package com.example.testing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin_portal extends AppCompatActivity {

    public EditText editTextTextPersonName21; //type
    public EditText editTextTextPersonName22; //value
    public Button button19; //update
    public Button button22; //report
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);

        //binding
        editTextTextPersonName21=findViewById(R.id.editTextTextPersonName21);
        editTextTextPersonName22=findViewById(R.id.editTextTextPersonName22);
        button19=findViewById(R.id.button19);
        button22=findViewById(R.id.button22);
        DB=new DBHelper(this);

        //update
        button19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean check=DB.insertrates(editTextTextPersonName21.getText().toString(),
                        Double.valueOf(editTextTextPersonName22.getText().toString()));
                if (check)
                {
                    Toast.makeText(Admin_portal.this, "Added successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Admin_portal.this, "Record not added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //report
        button22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res=DB.gethousedata();
                if (res.getCount()==0){
                    Toast.makeText(Admin_portal.this,"There are no Users' Details",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){ //loop through the records
                    buffer.append("House_number : "+res.getString(0)+"\n");
                    buffer.append("Previous_reading : "+res.getString(1)+"\n");
                    buffer.append("Current_reading : "+res.getString(2)+"\n");
                    buffer.append("Usage : "+res.getString(3)+"\n");
                    buffer.append("Amount_paid : "+res.getString(4)+"\n");
                    buffer.append("Balance : "+res.getString(5)+"\n");
                    buffer.append("Month : "+res.getString(6)+"\n");
                    buffer.append("SMS : "+res.getString(7)+"\n\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(Admin_portal.this);
                builder.setCancelable(true);
                builder.setTitle("Current Users:");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

    }
}