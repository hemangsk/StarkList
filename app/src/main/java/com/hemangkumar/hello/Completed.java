package com.hemangkumar.hello;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Hemang on 10/06/16.
 */
public class Completed  extends AppCompatActivity{
    TextView name_user;
    TextView work_user;
    ListView listView;
    ArrayList<String> completedTasks = new ArrayList<String>();
    ArrayAdapter<String> itemsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed);
        getDetails();
        loadArray(this);
        displayCompletedTasks();


    }

    public void getDetails(){
        name_user = (TextView) findViewById(R.id.textView);
        work_user = (TextView) findViewById(R.id.textView2);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = pref.getString("User", " ");
        String work = pref.getString("Work", " ").toUpperCase();
        name_user.setText(name);
        work_user.setText(work);
    }


    public void loadArray(Context mContext)
    {
        SharedPreferences pref =   PreferenceManager.getDefaultSharedPreferences(this);

        Log.v("Hey", pref.getString("User", " "));
        Log.v("Heyo", String.valueOf(pref.getInt("Completed_Size", 0)));
        completedTasks.clear();

        int size2 = pref.getInt("Completed_size", 0);
        Log.e("size2", completedTasks.toString());
        for(int i=0;i<size2;i++){
            completedTasks.add(pref.getString("Complete_" + i, null));
        }

        Log.e("Completed Tasks", completedTasks.toString());

    }

    public void displayCompletedTasks(){

        listView = (ListView) findViewById(R.id.listView);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, completedTasks);
        listView.setAdapter(itemsAdapter);
    }
}
