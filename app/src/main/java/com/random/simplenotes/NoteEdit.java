package com.random.simplenotes;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by ziakhan on 21/09/16.
 */
public class NoteEdit extends AppCompatActivity {


    LinedEditText content;
    EditText title;
    String getFontPreference;
    Toolbar toolbar;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteedit);
        toolbar= (Toolbar) findViewById(R.id.edittoolbar);
        setSupportActionBar(toolbar);

        content = new LinedEditText(this);
        content = (LinedEditText) findViewById(R.id.customview);
        title = (EditText)findViewById(R.id.title);
        fontSizeChange(this);





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

                    boolean insertResult;
                    insertResult = insertData(this);

                    if(insertResult)
                    {
                        Intent intent = new Intent(this,MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }else{

                        Toast.makeText(this,"Your Note could be saved because of some problem with the device",Toast.LENGTH_LONG).show();

                    }

                    return true;


                case R.id.discard:
                    clearAll();
                    Intent i = new Intent(this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    return true;




                default:
                    return super.onOptionsItemSelected(item);


            }



    }

    private void clearAll() {

        title.setText("");
        content.setText("");

    }

    private boolean insertData(Context context)
    {

        DataBaseHelper db = new DataBaseHelper(context);
        long result = db.insertData(title.getText().toString(),content.getText().toString(),System.currentTimeMillis());

        return true;

    }

    @Override
    public void onBackPressed() {

        boolean preferenceResult;
        SharedPreferences booleanprefs = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceResult=booleanprefs.getBoolean(getResources().getString(R.string.back_button),false);

        if(preferenceResult)
        {

            insertData(this);
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
