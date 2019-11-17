package com.example.devicedetectorapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread thread = new Thread(NetworkThread);
        thread.start();
    }



   // Here is where I copied code to understand it better (Kyle).



    //Testing git conflict merging

    //This is Kyle's Comment to test

    //Comment...


    //Adding another comment

  //I like Ice Cream

 //Erika was here


    //I like 3.14


}
