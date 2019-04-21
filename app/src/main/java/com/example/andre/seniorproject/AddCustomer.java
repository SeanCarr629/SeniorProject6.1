package com.example.andre.seniorproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCustomer extends AppCompatActivity {

    EditText firstName1,lastName1,companyName1,phoneNumber1,emailAddress1,address1,state1,zipCode1;
    Button addCust;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);

        firstName1 = (EditText) findViewById(R.id.FirstName);
        lastName1 = (EditText) findViewById(R.id.LastName);
        companyName1 = (EditText) findViewById(R.id.companyName);
        phoneNumber1 = (EditText) findViewById(R.id.Phone);
        emailAddress1 = (EditText) findViewById(R.id.Email);
        address1 = (EditText) findViewById(R.id.Address);
        state1 = (EditText) findViewById(R.id.State);
        zipCode1= (EditText) findViewById(R.id.ZipCode);
        addCust = (Button) findViewById(R.id.buttonAddCustomer);


        reff = FirebaseDatabase.getInstance().getReference("Customers");


        addCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addCustomer();

                Toast.makeText(getApplicationContext(),"Customer Added", Toast.LENGTH_LONG).show();

                finish();

            }
        });
    }

            public void addCustomer()
            {
                String firstName=(firstName1.getText().toString().trim());
                String lastName=(lastName1.getText().toString().trim());
                String companyName=(companyName1.getText().toString().trim());
                String phoneNumber= (phoneNumber1.getText().toString().trim());
                String email= (emailAddress1.getText().toString().trim());
                String address= (address1.getText().toString().trim());
                String state= (state1.getText().toString().trim());
                String fullName= firstName+ " " + lastName;
                String zipCode= (zipCode1.getText().toString().trim());

                Customer customer = new Customer(firstName,lastName,companyName,phoneNumber,email,address,state,zipCode,fullName);
                reff.push().setValue(customer);

            }


    }


