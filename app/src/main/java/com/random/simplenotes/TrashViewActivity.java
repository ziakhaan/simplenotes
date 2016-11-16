package com.random.simplenotes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

public class TrashViewActivity extends AppCompatActivity {

    LinedEditText content;
    EditText title;
    int position;
    Bundle extras;
    Cursor c;
    String NOTE_TITLE= "title";
    String CONTENT = "content";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteedit);


        content = new LinedEditText(this);
        content = (LinedEditText)findViewById(R.id.customview);
        title = (EditText)findViewById(R.id.title);
        extras = getIntent().getExtras();
        position = extras.getInt(Constants.ROW_NAME);
        toolbar= (Toolbar) findViewById(R.id.edittoolbar);
        setSupportActionBar(toolbar);
        updateData(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.trashmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.permanentdelete:

                DataBaseHelper deletedb = new DataBaseHelper(this);
                int deletedata = deletedb.deleteData(position);
                Toast.makeText(this,String.valueOf(deletedata),Toast.LENGTH_LONG).show();

                Intent dintent = new Intent(this,TrashActivity.class);
                dintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(dintent);


                return true;

            case R.id.restore:
                DataBaseHelper restoredb = new DataBaseHelper(this);
                int restore = restoredb.restoreAction(position,System.currentTimeMillis());
                Intent restoreintent = new Intent(this,TrashActivity.class);
                restoreintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(restoreintent);




                return true;

            default:
                return super.onOptionsItemSelected(item);



        }




    }

    private void updateData(int pos) {

        DataBaseHelper db = new DataBaseHelper(this);
        c = db.getData(pos);


        if (c != null && c.moveToFirst()) {


            title.setText(c.getString(c.getColumnIndex(NOTE_TITLE)));
            content.setText(c.getString(c.getColumnIndex(CONTENT)));



        } else {

            Toast.makeText(this, "CURSOR NULL", Toast.LENGTH_LONG).show();
        }




    }


}
