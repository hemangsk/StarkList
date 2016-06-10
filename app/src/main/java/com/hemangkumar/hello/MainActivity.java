package com.hemangkumar.hello;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    TextView name_user;
    TextView work_user;
    TextView remaining;
    TextView completed;


    DrawerLayout drawerLayout;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> completedTasks = new ArrayList<String>();
    ListView listView;
    ArrayAdapter<String> itemsAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDetails();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.button);


        listView = (ListView) findViewById(R.id.listView);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        loadArray(this);
        itemsAdapter.notifyDataSetChanged();
        updateStuff();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items.size() >= 0)
                    completedTasks.add(items.remove(position));
                itemsAdapter.notifyDataSetChanged();

                updateStuff();
            }
        });




    }

    public void openCompleted(View v){
        Intent completed = new Intent(this, Completed.class);
        startActivity(completed);
    }

    public void showmsg(View v){

        final EditText txtUrl = new EditText(this);


        txtUrl.setHint("What'd you like to do?");

        new AlertDialog.Builder(this)
                .setTitle("ADD A TASK")
                .setMessage("")
                .setView(txtUrl)
                .setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String item = txtUrl.getText().toString();
                        items.add(item);
                        itemsAdapter.notifyDataSetChanged();
                        updateStuff();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .show();
    }

    public void updateStuff(){

        saveArray();

        remaining = (TextView) findViewById(R.id.remaining);
        remaining.setText(String.valueOf(items.size()));

        completed = (TextView) findViewById(R.id.textView5);
        completed.setText((String.valueOf(completedTasks.size())));



    }



    public boolean saveArray()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mEdit1 = sp.edit();

        mEdit1.putInt("Items_size", items.size());

        for(int i=0;i<items.size();i++)
        {
            mEdit1.remove("Item_" + i);
            mEdit1.putString("Item_" + i, items.get(i));
        }


        mEdit1.putInt("Completed_size", completedTasks.size());

        for(int i=0; i<completedTasks.size(); i++){
            mEdit1.remove("Complete_" + i);
            mEdit1.putString("Complete_" +i, completedTasks.get(i));
        }
     //   Log.e("C", completedTasks.toString());

        mEdit1.commit();
        return true;
    }


    public void loadArray(Context mContext)
    {
        SharedPreferences mSharedPreference1 =   PreferenceManager.getDefaultSharedPreferences(this);
        items.clear();
        int size = mSharedPreference1.getInt("Items_size", 0);

        for(int i=0;i<size;i++)
        {
            items.add(mSharedPreference1.getString("Item_" + i, null));
        }

        completedTasks.clear();
        int size2 = mSharedPreference1.getInt("Completed_size", 0);

        for(int i=0;i<size2;i++){
            completedTasks.add(mSharedPreference1.getString("Complete_" + i, null));
        }

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

    @Override
    protected void onDestroy() {
        updateStuff();
        super.onDestroy();
    }
}
