package com.random.simplenotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

/**
 * Created by ziakhan on 16/09/16.
 */
public class DataBaseHelper
{

    public static final int DATABASE_VERSION=6;

    public static final String DATABASE_NAME="mynotes";

    SQLiteDatabase db;

    DataBaseHelper dbhelper;
    Context context;

    long rowResult;

    DataBase innerDBClass;

    Cursor c;

    DataBaseHelper(Context context)
    {
      innerDBClass = new DataBase(context);
        this.context=context;
    }


   long insertData(String title,String content,long date)
    {

        db = innerDBClass.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBase.NOTE_TITLE,title);
        cv.put(DataBase.NOTE_CONTENT,content);
        cv.put(DataBase.NOTE_DATE,date);
        cv.put(DataBase.CREATED_DATE,date);
        cv.put(DataBase.DELETE_FLAG,0);
        rowResult= db.insert(DataBase.TABLE_NAME,null,cv);



        return rowResult;



    }


   int updateData(String title,String content,int position,long updateDate)
    {

        db = innerDBClass.getWritableDatabase();
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(DataBase.NOTE_TITLE,title);
        cvUpdate.put(DataBase.NOTE_CONTENT,content);
        cvUpdate.put(DataBase.NOTE_DATE,updateDate);



        int numberOfRowsAffected=  db.update(DataBase.TABLE_NAME,cvUpdate,DataBase.ID+"=?",new String[]{String.valueOf(position)});


        return numberOfRowsAffected;



    }


    int deleteData(int position)
    {

        db = innerDBClass.getWritableDatabase();
       int number= db.delete(DataBase.TABLE_NAME,DataBase.ID + "=?",new String[]{String.valueOf(position)});
        db.close();
        return number;


    }


    int deleteAction(int position,long date)
    {

        db = innerDBClass.getWritableDatabase();
        ContentValues cvdel = new ContentValues();
        cvdel.put(DataBase.DELETE_FLAG,1);
        cvdel.put(DataBase.NOTE_DATE,date);



        int numberOfRowsAffected=  db.update(DataBase.TABLE_NAME,cvdel,DataBase.ID+"=?",new String[]{String.valueOf(position)});


        return numberOfRowsAffected;


    }

    int restoreAction(int position,long date)
    {
        db = innerDBClass.getWritableDatabase();
        ContentValues cvdel = new ContentValues();
        cvdel.put(DataBase.DELETE_FLAG,0);
        cvdel.put(DataBase.NOTE_DATE,date);

        int numberOfRowsAffected=  db.update(DataBase.TABLE_NAME,cvdel,DataBase.ID+"=?",new String[]{String.valueOf(position)});


        return numberOfRowsAffected;


    }



    Cursor fetchAllNotes()
    {

        db =  innerDBClass.getWritableDatabase();

        Cursor newCursor = db.query(DataBase.TABLE_NAME,new String[]{DataBase.ID, DataBase.NOTE_CONTENT,DataBase.NOTE_TITLE,DataBase.NOTE_DATE,DataBase.DELETE_FLAG},DataBase.DELETE_FLAG+"=?",new String[]{String.valueOf(0)},null,null,null);
        //Cursor newCursor = db.query(DataBase.TABLE_NAME,new String[]{DataBase.ID, DataBase.NOTE_CONTENT,DataBase.NOTE_TITLE,DataBase.NOTE_DATE,DataBase.DELETE_FLAG},null,null,null,null,null);

        return newCursor;


    }

    Cursor fetchAllDeletedNotes()
    {

        db =  innerDBClass.getWritableDatabase();

        Cursor newCursor = db.query(DataBase.TABLE_NAME,new String[]{DataBase.ID, DataBase.NOTE_CONTENT,DataBase.NOTE_TITLE,DataBase.NOTE_DATE,DataBase.DELETE_FLAG},DataBase.DELETE_FLAG+"=?",new String[]{String.valueOf(1)},null,null,null);


        return newCursor;


    }


   Cursor getData(int pos)
    {
        db = innerDBClass.getWritableDatabase();
        Cursor titleCur = db.query(DataBase.TABLE_NAME,new String[]{DataBase.ID, DataBase.NOTE_CONTENT,DataBase.NOTE_TITLE,DataBase.NOTE_DATE,DataBase.CREATED_DATE},DataBase.ID + "=?",new String[]{String.valueOf(pos)},null,null,null);
        return titleCur;

    }

    long deleteAll()
    {


        db=innerDBClass.getWritableDatabase();
        long resultdelall = db.delete(DataBase.TABLE_NAME,DataBase.DELETE_FLAG + "=?",new String[]{String.valueOf(1)});
        return resultdelall;
    }




    static private class DataBase extends SQLiteOpenHelper
    {
        // DataBase dbase;

        Context context;
        //TABLE
        public static final String ID ="_id";

        public static final String TABLE_NAME="mynotes";
        public static final String TIME="time";
        public static final String NOTE_TITLE="title";
        public static final String NOTE_CONTENT="content";
        public static final String NOTE_DATE="date";
        public static final String DELETE_FLAG="deleteflag";
        public static final String CREATED_DATE="createddate";


        /*String TABLE    =   "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTO INCREEMENT, " + NOTE_TITLE +
                                                " TEXT, " + NOTE_CONTENT + " TEXT, "+ NOTE_DATE +" INTEGER);" ;*/


        public static final String name = "CREATE TABLE "+ TABLE_NAME+" ("+ ID +" INTEGER PRIMARY KEY \n" +
               "AUTOINCREMENT, " + NOTE_TITLE + " VARCHAR(250), "+NOTE_CONTENT +" VARCHAR(250), "+ NOTE_DATE + " INTEGER, "+ DELETE_FLAG + " INTEGER, " + CREATED_DATE + " INTEGER);";

        DataBase(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(name);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
            Toast.makeText(context,"On Upgrade called",Toast.LENGTH_LONG).show();

        }



    }



}
