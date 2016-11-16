package com.random.simplenotes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    DataBaseHelper dbhelper;
    ListView notesView;
    CustomCursor dataCursor;
    int ROW_NUMBER;
    Cursor passCursor;
    Toolbar bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesView = (ListView)findViewById(R.id.listView);
        dbhelper = new DataBaseHelper(this);


        passCursor = dbhelper.fetchAllNotes();
        dataCursor = new CustomCursor(this,passCursor);


       notesView.setAdapter(dataCursor);
        notesView.setOnItemClickListener(this);

        bar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(bar);
        Log.v("Cursor Object", String.valueOf(passCursor.getCount()));











    }


    //SAVING ACTIVITY STATE METHODS

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(Constants.ROW_NAME,Constants.ROW_NUMBER);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    //METHOD ENDS

    //ACTIVITY LIFECYCLE


    @Override
    protected void onStart() {
        super.onStart();

        dataCursor.notifyDataSetChanged();


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        dataCursor.notifyDataSetChanged();



    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


      //  Constants.ROW_NUMBER=position;


        try{
            TextView getRecordID = (TextView) view.findViewById(R.id.recordID);
            Constants.ROW_NUMBER = Integer.parseInt(getRecordID.getText().toString());

            Intent editIntent = new Intent(this,NoteDisplay.class);
            editIntent.putExtra(Constants.ROW_NAME,Constants.ROW_NUMBER);
            startActivity(editIntent);



        }catch (NumberFormatException e)
        {

            Toast.makeText(this,"THIS RECORD DOES NOT EXIST",Toast.LENGTH_LONG).show();

        }

















    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.action_bar_items,menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {

            case R.id.addButton:
                Toast.makeText(this,"Add button is clicked",Toast.LENGTH_LONG).show();
                Intent addnoteIntent = new Intent(MainActivity.this,NoteEdit.class);
                addnoteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(addnoteIntent);
                return true;

            case R.id.settings:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SettingsFragment sf = new SettingsFragment();
                ft.addToBackStack(null);
                ft.replace(R.id.fragplacement,sf).commit();



                return true;

            case R.id.trash:

                Intent trashIntent = new Intent(this,TrashActivity.class);
                trashIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(trashIntent);




                return super.onOptionsItemSelected(item);



            default:
                return super.onOptionsItemSelected(item);



        }



    }

    //LIFECYCLE METHODS ENDS

    //CUSTOM EDITTEXT


    @Override
    public void onBackPressed() {
        dataCursor.notifyDataSetChanged();
        super.onBackPressed();

    }
}
