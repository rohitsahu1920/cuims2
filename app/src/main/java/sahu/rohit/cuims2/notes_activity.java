package sahu.rohit.cuims2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class notes_activity extends AppCompatActivity {


    Activity activity =this;
    DatabaseHelper db;
    ListView listView;
    List<Student> list;
    ArrayList<String> theList;
    Student title;
    String body;
    String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_activity);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_Note);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(notes_activity.this, notes_create_activity.class);
                startActivity(intent);
            }
        });

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listview);
        theList = new ArrayList<>();

        retive();

      listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

              Student student = list.get(i);
              final String title = student.getTitle();
              final String body = student.getBody();
              final String subject = student.getSubject();

              builder.setMessage("Note:- Data will be permanently delete..!")
                      .setCancelable(true)
                      .setTitle("Do you really want to Delete ?")
                      .setIcon(R.drawable.ic_warning)
                      .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialogInterface, int i) {
                              Boolean delete = db.delete(title,body,subject);
                              if(delete == true)
                              {
                                  Toast.makeText(getApplicationContext(),"Record Deleted Successfully",Toast.LENGTH_LONG).show();
                                  retive();
                              }
                              else
                              {
                                  Toast.makeText(getApplicationContext(),"Problem in deleting Record",Toast.LENGTH_LONG).show();
                              }
                          }
                      });

              builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      dialogInterface.cancel();
                  }
              });

              AlertDialog alert11 = builder.create();
              alert11.show();

              return false;
          }
      });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Student student = list.get(i);
                Intent intent = new Intent(notes_activity.this, notesShow.class);
                intent.putExtra("STUDENT", student);
                startActivity(intent);
            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.item_update:
                retive();
                Toast.makeText(getApplicationContext(),"List Refreshed :)", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void retive()
    {
        list = db.getAllNotes();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), main_page.class));
    }

}
