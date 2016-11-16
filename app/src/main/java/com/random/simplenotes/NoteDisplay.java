package com.random.simplenotes;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ziakhan on 23/09/16.
 */

public class NoteDisplay extends AppCompatActivity {

        LinedEditText content;
        EditText title;
        int position;
        SQLiteDatabase db;
    String NOTE_TITLE= "title";
    String CONTENT = "content";
    String getFontPreference;
    long createdDate;
    public static final String CREATED_DATE="createddate";

    Toolbar toolbar;
    TextView createdDateView;

    Bundle extras;
    Cursor c;
    SimpleDateFormat dateFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.noteedit);
        toolbar = (Toolbar) findViewById(R.id.edittoolbar);
        setSupportActionBar(toolbar);

        toolbar.setBackgroundColor(this.getResources().getColor(R.color.olive));

        createdDateView = (TextView) findViewById(R.id.createdDate);
        content = new LinedEditText(this);
        content = (LinedEditText)findViewById(R.id.customview);
        title = (EditText)findViewById(R.id.title);
       extras = getIntent().getExtras();
        position = extras.getInt(Constants.ROW_NAME);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");



        updateData(position);
        fontSizeChange(this);






    }

    private void updateData(int pos) {

    DataBaseHelper db = new DataBaseHelper(this);


       c = db.getData(pos);


    if (c != null && c.moveToFirst()) {

        createdDate = c.getLong(c.getColumnIndex(CREATED_DATE));
        Date date = new Date(createdDate);

       //title.setText(c.getString(c.getColumnIndex(NOTE_TITLE)));
       title.setText(c.getString(c.getColumnIndex(NOTE_TITLE)));

        if(createdDate>0)
        {
            createdDateView.setText("Created Date: "+dateFormat.format(date));

        }else
        {

            createdDateView.setVisibility(View.GONE);

        }







        content.setText(c.getString(c.getColumnIndex(CONTENT)));



    } else {

        Toast.makeText(this, "CURSOR NULL", Toast.LENGTH_LONG).show();
    }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.simplemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.save:

                boolean updateResult;
                updateResult = updateData(this);
                if(updateResult)
                {
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else{

                    Toast.makeText(this,"Your Note could be saved because of some problem with the device",Toast.LENGTH_LONG).show();

                }



                return true;


            case R.id.discard:

                Intent i = new Intent(this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);



                return true;


            case R.id.deletebutton:

                DataBaseHelper deletedb = new DataBaseHelper(this);
                int deletedata = deletedb.deleteAction(position,System.currentTimeMillis());

                Toast.makeText(this,String.valueOf(deletedata),Toast.LENGTH_LONG).show();

                Intent dintent = new Intent(this,MainActivity.class);
                dintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(dintent);

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }



    }


    @Override
    protected void onResume() {
        super.onResume();
        fontSizeChange(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        c.close();

    }


    private boolean updateData(Context context)
    {
        DataBaseHelper db = new DataBaseHelper(context);
        long update= db.updateData(title.getText().toString(),content.getText().toString(),position,System.currentTimeMillis());


        return true;

    }

    @Override
    public void onBackPressed() {

        boolean preferenceResult;
        SharedPreferences booleanprefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceResult=booleanprefs.getBoolean(getResources().getString(R.string.back_button),false);

        if(preferenceResult)
        {

            updateData(this);
            Intent intentbackpress = new Intent(this,MainActivity.class);
            intentbackpress.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intentbackpress);



        }else{
            super.onBackPressed();

        }



    }




    private void fontSizeChange(Context context)
    {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        getFontPreference= sp.getString(context.getResources().getString(R.string.fontsizekey), Constants.DEFAULT_VALUE_FONTSIZE);

        switch (getFontPreference){

            case "Small":

                content.setTextSize(TypedValue.COMPLEX_UNIT_SP,Constants.FONT_SMALL);

                break;

            case "Medium":

                content.setTextSize(TypedValue.COMPLEX_UNIT_SP,Constants.FONT_MEDIUM);

                break;

            case "Large":

                content.setTextSize(TypedValue.COMPLEX_UNIT_SP,Constants.FONT_LARGE);
                break;

            default:
                content.setTextSize(TypedValue.COMPLEX_UNIT_SP,Constants.FONT_SMALL);
                break;

        }

    }
}
