package com.example.andre.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ViewInventory extends AppCompatActivity {

    ListView viewInventory;
    DatabaseReference reff;
    FirebaseDatabase database;
    ArrayList<Inventory> list = new ArrayList<Inventory>();
    EditText etItemNumber;
    ProductAdapter adapter2;
    Button addItem;
    String savedID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);


        addItem = findViewById(R.id.btnAddItem);

        Intent i = getIntent();
        savedID = (String)i.getSerializableExtra("saveID");


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ViewInventory.this, SpinnerTest.class);
                i.putExtra("savedID", savedID);
                startActivity(i);


            }
        });





        viewInventory = (ListView) findViewById(R.id.ListView);

        adapter2 = new ProductAdapter(this, list);

        database = FirebaseDatabase.getInstance();
        reff = database.getReference("Customers").child(savedID).child("Inventory");


        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Inventory post = dataSnapshot.getValue(Inventory.class);

                list.add(post);


                viewInventory.setAdapter(adapter2);


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


        viewInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {


                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        ViewInventory.this);

                // set title


                final EditText input = new EditText(ViewInventory.this);
                input.setHeight(100);
                input.setWidth(340);
                input.setGravity(Gravity.LEFT);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                input.setImeOptions(EditorInfo.IME_ACTION_DONE);
                alertDialogBuilder.setView(input);


                LayoutInflater inflater = getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.dialog_inventoryedit, null);
                final AlertDialog.Builder builder = new AlertDialog.Builder(ViewInventory.this);
                builder.setView(dialoglayout);
                final  AlertDialog dialog = builder.create();
                etItemNumber = dialoglayout.findViewById(R.id.etItemNumber);

                dialog.show();

                Button btnSubmitQuan = (Button) dialoglayout.findViewById( R.id.btnSumbitQuan );
                Button btnExit = (Button) dialoglayout.findViewById(R.id.btnExit);
                Button btnDelete = (Button) dialoglayout.findViewById(R.id.btnDelete);

                btnSubmitQuan.setOnClickListener(new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {

                        final Integer quantity = Integer.parseInt(etItemNumber.getText().toString());

                        reff.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Inventory post = dataSnapshot.getValue(Inventory.class);
                                // System.out.println(post.getItemName() + post.getItemQuantity());

                                if(post.itemName == list.get(position).itemName)
                                {
                                    post.setItemQuantity(quantity);
                                    reff.child(dataSnapshot.getKey()).setValue(post);
                                    adapter2.notifyDataSetChanged();
                                    Toast.makeText(ViewInventory.this, "Quantity Updated", Toast.LENGTH_LONG).show();
                                }


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


                    }

                });

                btnExit.setOnClickListener(new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();



                        reff.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Inventory post = dataSnapshot.getValue(Inventory.class);


                                list.add(post);
                                viewInventory.setAdapter(adapter2);

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
                        adapter2.refreshEvents(list);


                    }



                });



                btnDelete.setOnClickListener(new View.OnClickListener(){


                    @Override
                    public void onClick(View v) {



                        reff.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                Inventory post = dataSnapshot.getValue(Inventory.class);

                                if(post.itemName == list.get(position).itemName)
                                {
                                    dataSnapshot.getRef().removeValue();
                                    adapter2.notifyDataSetChanged();
                                    Toast.makeText(ViewInventory.this, "Item Deleted", Toast.LENGTH_LONG).show();
                                }


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



                    }
                });


            }
        });


    }


}


