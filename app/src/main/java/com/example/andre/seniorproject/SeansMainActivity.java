package com.example.andre.seniorproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SeansMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seansmain);


    }


    public void OnClick(View v)
    {

        Intent i = new Intent(this, SpinnerTest.class);

        startActivity(i);
    }

    public void OnClick2(View v)
    {

        Intent i = new Intent(this, ViewInventory.class);

        startActivity(i);
    }

    public void OnClick3(View v)
    {

        Intent i = new Intent(this,OrderForm.class);

        startActivity(i);
    }
}
