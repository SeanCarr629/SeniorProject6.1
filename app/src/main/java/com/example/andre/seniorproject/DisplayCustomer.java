package com.example.andre.seniorproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class DisplayCustomer extends AppCompatActivity {

    DatabaseReference reff1;
    Customer customer1;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_customer);

        Intent intent= getIntent();

        final String custName= intent.getExtras().getString("custName");


        final TextView firstDisplay= findViewById(R.id.displayFirstName ),
                lastDisplay=findViewById(R.id.displayLastName),
                companyDisplay=findViewById(R.id.displayCompanyName),
                phoneDisplay=findViewById(R.id.displayPhone),
                emailDisplay=findViewById(R.id.displayEmail),
                addressDisplay=findViewById(R.id.displayAddress),
                stateDisplay=findViewById(R.id.displayState),
                zipCodeDisplay=findViewById(R.id.displayZipCode);


        reff1 = FirebaseDatabase.getInstance().getReference("Customers");

        reff1.orderByChild("fullName").equalTo(custName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                customer1 = dataSnapshot.getValue(Customer.class);

                firstDisplay.setText(customer1.getFirstName());
                lastDisplay.setText(customer1.getLastName());
                companyDisplay.setText(customer1.getCompanyName());
                phoneDisplay.setText(customer1.getPhoneNumber());
                emailDisplay.setText(customer1.getEmailAddress());
                addressDisplay.setText(customer1.getCompanyAddress());
                stateDisplay.setText(customer1.getState());
                zipCodeDisplay.setText(customer1.getZipCode());

                id= dataSnapshot.getKey();




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //delete customer
        final Button deleteCustomerButton = findViewById(R.id.buttonDeleteCustomer);

        deleteCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                deleteCustomer(id);


                Toast.makeText(getApplicationContext(),"Customer Deleted", Toast.LENGTH_LONG).show();


                finish();


            }
        });

    }


    public void deleteCustomer( String customerID)
    {
        DatabaseReference customerChild = FirebaseDatabase.getInstance().getReference("Customers").child(customerID);

        customerChild.removeValue();

    }

}








