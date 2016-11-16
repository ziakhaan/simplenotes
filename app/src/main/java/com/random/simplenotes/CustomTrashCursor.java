package com.random.simplenotes;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ziakhan on 21/10/16.
 */

public class CustomTrashCursor extends CursorAdapter {


    private static final String NOTE_TITLE = "title";
    private static final String RECORD_ID = "_id";
    private static final String RECORD_DATE = "date";
    LayoutInflater inflater;
    TextView tv, recordID, dateET;

    LinearLayout ll;
    String getText, existsRecordID;
    long datevalue;
    SimpleDateFormat dateFormatter;
    String listViewHeight;
    Context cont;

    String listHeightValue;

    LinearLayout.LayoutParams params;



    int widthof;
    int heightlistview = 100;

    CustomTrashCursor(Context context, Cursor c) {
        super(context, c, 0);

        cont= context;

        inflater = LayoutInflater.from(context);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");


    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return inflater.inflate(R.layout.customlistview, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {



        ll = (LinearLayout)view.findViewById(R.id.listViewLayout);
        setListViewHeight(ll,context);



        if (cursor != null) {
            getText = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
            existsRecordID = cursor.getString(cursor.getColumnIndex(RECORD_ID));
            datevalue = cursor.getLong(cursor.getColumnIndex(RECORD_DATE));


        }
        Date newdate = new Date(datevalue);
        tv = (TextView) view.findViewById(R.id.content);
        recordID = (TextView) view.findViewById(R.id.recordID);
        dateET = (TextView) view.findViewById(R.id.date);
        tv.setText(getText.trim());

        recordID.setText(existsRecordID);
        dateET.setText(dateFormatter.format(newdate));


    }

    private void setListViewHeight(LinearLayout ll,Context con) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(con);

        listHeightValue  = sp.getString(con.getResources().getString(R.string.listViewHeightkey),Constants.DEFAULT_VALUE_LISTVIEW_HEIGHT);


        switch (listHeightValue)
        {

            case "Tiny":

                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200);
                ll.setLayoutParams(params);

                break;

            case "Medium":
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,220);
                ll.setLayoutParams(params);

                break;

            case "Large":
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,250);
                ll.setLayoutParams(params);

                break;
            default:
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,150);
                ll.setLayoutParams(params);

        }




    }



}
