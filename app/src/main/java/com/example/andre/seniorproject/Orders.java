package com.example.andre.seniorproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Orders extends AppCompatActivity implements Serializable {


    EditText etDate, etQuantity;
    Button btnSubmit, btnExit;
    FirebaseDatabase database;
    DatabaseReference reff;
    String date, itemName, selectedItem, spinnerItem, date2;
    Spinner spinner;
    Integer quantity;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Integer count = 0, count2 = 0;
    ListView viewOrder;
    ArrayList<Order> list;
    OrderAdapter adapter;
    Boolean itemExists;
    DataSnapshot snapshot;
    String key, savedID;
    ArrayList<Order> dates2;
    Boolean sumbitClick;
    Inventory inventory;
    DatabaseReference key2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);


        Intent i = getIntent();
        savedID = (String)i.getSerializableExtra("saveID");

        dates2 = new ArrayList<Order>();





        list = new ArrayList<Order>();
        viewOrder = findViewById(R.id.lvListView);
        adapter = new OrderAdapter(this, list);

        database = FirebaseDatabase.getInstance();
        reff = database.getReference("Customers").child(savedID)
                .child("Orders");

        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Order post = dataSnapshot.getValue(Order.class);
                // System.out.println(post.getItemName() + post.getItemQuantity());

                list.add(post);


                viewOrder.setAdapter(adapter);
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


        viewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {

                Order orders = adapter.getItem(i);
                Intent intent = new Intent(Orders.this, ViewOrderInfo.class);
                intent.putExtra("object", orders);
                startActivity(intent);


            }
        });


        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


////////////////////////////////////////////////////////////////////////////////////////////////////

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        Orders.this);

                // set title


                final EditText input = new EditText(Orders.this);
                input.setHeight(100);
                input.setWidth(340);
                input.setGravity(Gravity.LEFT);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                alertDialogBuilder.setView(input);



                LayoutInflater inflater = getLayoutInflater();
                final View dialoglayout = inflater.inflate(R.layout.dialog_neworder, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Orders.this);
                if(input.getParent()!=null)
                    ((ViewGroup)input.getParent()).removeView(input); // <- fix
                builder.setView(dialoglayout);
                // builder.setView(dialoglayout);
                final AlertDialog dialog = builder.create();



                dialog.show();

                etDate = dialoglayout.findViewById(R.id.etDate);
                etQuantity = dialoglayout.findViewById(R.id.etQuantity);
                btnSubmit = dialoglayout.findViewById(R.id.btnSubmit);
                btnExit = dialoglayout.findViewById(R.id.btnExit);

                etDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dialog = new DatePickerDialog(Orders.this,
                                android.R.style.Theme_Holo, dateSetListener, year, month, day
                        );
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();
                    }
                });

                dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        date2 = month + "/" + dayOfMonth + "/" + year;
                        etDate.setText(date2);


                    }
                };






                FirebaseDatabase.getInstance().getReference("Customers").child(savedID).child("Inventory").addValueEventListener
                        (new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                // Is better to use a List, because you don't know the size
                                // of the iterator returned by dataSnapshot.getChildren() to
                                // initialize the array
                                final List<String> invList = new ArrayList<String>();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Inventory inv = snapshot.getValue(Inventory.class);
                                    if (inv.getItemName()!= null) {
                                        invList.add(inv.getItemName());

                                    }
                                }


                                Spinner spinnerProperty = (Spinner) dialoglayout.findViewById(R.id.spinner1);
                                ArrayAdapter<String> addressAdapter = new ArrayAdapter<String>(Orders.this, android.R.layout.simple_spinner_item,invList);
                                addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerProperty.setAdapter(addressAdapter);

                                spinnerProperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        selectedItem = (String) parent.getItemAtPosition(position);

                                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                                        ((TextView) parent.getChildAt(0)).setTextSize(20);
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            inventory = snapshot.getValue(Inventory.class);


                                            if (inventory.getItemName().equals(selectedItem)) {
                                                key2 = snapshot.getRef();


                                            }
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });
                /////////////////////////////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////



                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        date = etDate.getText().toString();
                        quantity = Integer.parseInt(etQuantity.getText().toString());


                        adapter.notifyDataSetChanged();


                        reff.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Order order = snapshot.getValue(Order.class);


                                    if (order.getDate().equals(date2)) {
                                        count = 1;
                                        Inventory item = new Inventory();
                                        item.setItemName(selectedItem);
                                        item.setItemQuantity(quantity);
                                        for (int i = 0; i < order.inventory.size(); i++) {
                                            if (order.inventory.get(i).itemName.equals(selectedItem)){
                                                order.inventory.get(i).setItemQuantity(quantity);
                                                count2 = 1;
                                            }
                                        }
                                        if(count2 == 0){
                                            order.getInventory().add(item);}


                                        reff.child(snapshot.getKey()).setValue(order);
                                        Toast.makeText(Orders.this, "Order Updated", Toast.LENGTH_LONG).show();



                                        // viewOrder.setAdapter(adapter);


                                    }


                                }

                                if (count == 0) {
                                    Inventory item = new Inventory();
                                    item.setItemName(selectedItem);
                                    item.setItemQuantity(quantity);
                                    Order order = new Order(date, item);
                                    reff.push().setValue(order);


                                    Toast.makeText(Orders.this, "Order Updated", Toast.LENGTH_LONG).show();


                                    // viewOrder.setAdapter(adapter);

                                }


                                count = 0;
                                count2 = 0;

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                });


                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();




                        reff.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Order post = dataSnapshot.getValue(Order.class);


                                list.add(post);
                                viewOrder.setAdapter(adapter);

                            }

                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                        adapter.refreshEvents(list);




                    }
                });


            }
        });


    }

}





