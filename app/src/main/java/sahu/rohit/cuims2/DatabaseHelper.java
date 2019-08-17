package sahu.rohit.cuims2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "cuims.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table cuims(title text, body text, subject text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists cuims");
    }

    public boolean insert(String title, String body, String subject){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("body",body);
        contentValues.put("subject",subject);
        long ins = db.insert("cuims",null,contentValues);
        if(ins == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean delete(String subject)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //long delete = db.delete("cuims", "title = "+title+" and body = "+body+" and subject = "+subject,null);
        long delete = db.delete("cuims", "subject = ?",new String[]{subject});

        if(delete == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public boolean update(String title, String body, String subject)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("body",body);
        contentValues.put("subject",subject);

        long update = (long) db.update("cuims",contentValues," title = ? or body = ? or subject = ?", new  String[]{title , body, subject});

        if(update == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

   public Cursor getListContent()
   {
       SQLiteDatabase db = this.getWritableDatabase();

       Cursor data = db.rawQuery("Select * from cuims",null);
       return data;
   }

   public List<Student> getAllNotes()
   {
       List<Student> studentList = new ArrayList<>();
       SQLiteDatabase db = getReadableDatabase();
       Cursor cursor = db.rawQuery("select * from cuims",null);
       if(cursor.moveToFirst())
       {
           do{
               String title = cursor.getString(0);
               String body = cursor.getString(1);
               String subject = cursor.getString(2);

               Student student = new Student(title,body,subject);
               studentList.add(student);
           }
           while (cursor.moveToNext());
       }

       return studentList;
   }
}
