package com.hemangkumar.hello;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Hemang on 09/06/16.
 */
public class Intro extends AppCompatActivity{
    public static boolean firstRun;
    SharedPreferences prefs;
    String userName;
    String userWork;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(!loadStatus(this)) {

            Intent myIntent = new Intent(this, MainActivity.class);
            //  myIntent.putExtra("key", value); //Optional parameters
            startActivity(myIntent);
            finish();

        }
        setContentView(R.layout.intro);
        saveStatus();






    }

    public void startMain(View v){
        EditText name = (EditText) findViewById(R.id.editText);
        userName = name.getText().toString();

        EditText work = (EditText) findViewById(R.id.editText2);
        userWork = work.getText().toString();

        if(userName.length() == 0 || userWork.length()==0){
            Toast.makeText(Intro.this, "Enter a name and a cool description!", Toast.LENGTH_SHORT).show();

        }

        else{



            prefs.edit().putString("User", userName).commit();
            prefs.edit().putString("Work", userWork).commit();

            Intent myIntent = new Intent(this, MainActivity.class);
            //  myIntent.putExtra("key", value); //Optional parameters
            startActivity(myIntent);
            finish();

        }
    }
    public void saveStatus()
    {

        prefs.edit().putBoolean("first_Run", false).commit();
    }


    public Boolean loadStatus(Context mContext)
    {


       return prefs.getBoolean("first_Run", true);

    }
}
