package com.example.andre.seniorproject;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class SearchCustomer extends AppCompatActivity {

    Customer value;
    public ListView viewCustomer;
    DatabaseReference reff;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_customer);


        //Populate search list from firebase
        value= new Customer();

        viewCustomer = (ListView) findViewById(R.id.listCustomers);

        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);

        viewCustomer.setAdapter(arrayAdapter);

        reff = FirebaseDatabase.getInstance().getReference("Customers");

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                value= dataSnapshot.getValue(Customer.class);

                String custID = String.valueOf(dataSnapshot.getKey());

                value.setCustomerID(custID);

                arrayList.add(value.getFirstName() + " " + value.getLastName());

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                arrayAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //update search list based on search input:

        EditText thisFilter= (EditText) findViewById(R.id.searchFilter);

        thisFilter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                (SearchCustomer.this).arrayAdapter.getFilter().filter(s);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });



        //start display activity from search list
        viewCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String s = viewCustomer.getItemAtPosition(position).toString();

                Intent intent = new Intent(SearchCustomer.this, DisplayCustomer.class);
                intent.putExtra("custName",s);
                startActivity(intent);


            }

        });
    }



}





