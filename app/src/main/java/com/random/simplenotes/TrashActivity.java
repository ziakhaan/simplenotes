package com.random.simplenotes;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrashActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView trashView;
    CustomTrashCursor customCursor;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    Cursor trashCursor;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        dataBaseHelper = new DataBaseHelper(this);

        trashCursor = dataBaseHelper.fetchAllDeletedNotes();
        toolbar = (Toolbar) findViewById(R.id.trashbar);
        setSupportActionBar(toolbar);


        trashView = (ListView) findViewById(R.id.listViewTrash);
        customCursor = new CustomTrashCursor(this,trashCursor);
        trashView.setAdapter(customCursor);
        trashView.setOnItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.trashactivitymenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.deleteAll:
                long delresult = dataBaseHelper.deleteAll();
                Toast.makeText(this,"DELETED RESULT" + delresult,Toast.LENGTH_LONG).show();
                customCursor.notifyDataSetChanged();

                return true;

           default:
               return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        customCursor.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView getRecordID = (TextView) view.findViewById(R.id.recordID);
        Constants.ROW_NUMBER = Integer.parseInt(getRecordID.getText().toString());

        Intent editIntent = new Intent(this,TrashViewActivity.class);
        editIntent.putExtra(Constants.ROW_NAME,Constants.ROW_NUMBER);
        startActivity(editIntent);


    }


}
