package com.example.andre.seniorproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddCustomer extends AppCompatActivity {

    EditText firstName1,lastName1,companyName1,phoneNumber1,emailAddress1,address1,state1,zipCode1;
    Button addCust;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reff, reff2;
    ArrayList<Inventory> inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);

        firstName1 = (EditText) findViewById(R.id.FirstName);
        firstName1.addTextChangedListener(mWatcher);
        lastName1 = (EditText) findViewById(R.id.LastName);
        lastName1.addTextChangedListener(mWatcher);
        companyName1 = (EditText) findViewById(R.id.companyName);
        companyName1.addTextChangedListener(mWatcher);
        phoneNumber1 = (EditText) findViewById(R.id.Phone);
        phoneNumber1.addTextChangedListener(mWatcher);
        emailAddress1 = (EditText) findViewById(R.id.Email);
        emailAddress1.addTextChangedListener(mWatcher);
        address1 = (EditText) findViewById(R.id.Address);
        address1.addTextChangedListener(mWatcher);
        state1 = (EditText) findViewById(R.id.State);
        state1.addTextChangedListener(mWatcher);
        zipCode1= (EditText) findViewById(R.id.ZipCode);
        zipCode1.addTextChangedListener(mWatcher);
        addCust = (Button) findViewById(R.id.buttonAddCustomer);
        inventory = new ArrayList<>();


        reff = database.getReference("Customers");



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

        CheckValidation();

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

        String key = reff.push().getKey();
        reff.child(key).setValue(customer);





    }





    TextWatcher mWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            CheckValidation();

        }
    };


    public void CheckValidation() {

        if(firstName1.length()==0 ||
                lastName1.length()==0 ||
                companyName1.length()==0 ||
                phoneNumber1.length()==0 ||
                emailAddress1.length()==0 ||
                address1.length()==0 ||
                state1.length()==0 ||
                zipCode1.length()==0)

        {
            if(firstName1.length()==0){firstName1.setError("Enter First Name!");}
            if(lastName1.length()==0) {lastName1.setError("Enter Last Name!");}
            if(companyName1.length()==0) {companyName1.setError("Enter Company Name!");}
            if(phoneNumber1.length()==0) {phoneNumber1.setError("Enter Phone Number!");}
            if(emailAddress1.length()==0) {emailAddress1.setError("Enter Email Address!");}
            if(address1.length()==0) {address1.setError("Enter Address!");}
            if(state1.length()==0) {state1.setError("Enter State!");}
            if(zipCode1.length()==0) {zipCode1.setError("Enter Zip Code!");}


            addCust.setEnabled(false);


        }

        else{
            addCust.setEnabled(true);

        }



    }

}


