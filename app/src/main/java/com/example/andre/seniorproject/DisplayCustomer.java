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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisplayCustomer extends AppCompatActivity {

    DatabaseReference reff1,reff2;
    Customer customer1;
    String id, saveID;
    Button inventory, orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_customer);




        inventory = findViewById(R.id.buttonInventory);
        orders = findViewById(R.id.buttonOrders);


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

                saveID = id;




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

        //update customer

        Button updateCustomerButton = findViewById(R.id.buttonUpdateCustomer);

        updateCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        reff2 = FirebaseDatabase.getInstance().getReference("Customers").child(id);
                        reff2.child("firstName").setValue(firstDisplay.getText().toString());
                        reff2.child("lastName").setValue(lastDisplay.getText().toString());
                        reff2.child("companyName").setValue(companyDisplay.getText().toString());
                        reff2.child("companyAddress").setValue(addressDisplay.getText().toString());
                        reff2.child("emailAddress").setValue(emailDisplay.getText().toString());
                        reff2.child("phoneNumber").setValue(phoneDisplay.getText().toString());
                        reff2.child("state").setValue(stateDisplay.getText().toString());
                        reff2.child("zipCode").setValue(zipCodeDisplay.getText().toString());
                        reff2.child("fullName").setValue(firstDisplay.getText().toString()+ ' ' + lastDisplay.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(getApplicationContext(),"Customer Information Updated", Toast.LENGTH_LONG).show();
            }
        });

    }







}








