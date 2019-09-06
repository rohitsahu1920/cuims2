package sahu.rohit.cuims2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class notesShow extends AppCompatActivity {

    DatabaseHelper db;
    EditText title,body,sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_show);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        db = new DatabaseHelper(this);
        Button btn = findViewById(R.id.noteSaveButton);
        title = findViewById(R.id.noteTitle);
        body = findViewById(R.id.noteBody);
        sub = findViewById(R.id.subjectNote);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1 = title.getText().toString();
                String body1 = body.getText().toString();
                String sub1 = sub.getText().toString();

                if(title1.isEmpty() || body1.isEmpty() || sub1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the Details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean ins = db.update(title1,body1,sub1);
                    if(ins == true)
                    {
                        Toast.makeText(getApplicationContext(),"Note Updated Successfully",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"There is problem in creating notes. Please contact to developer",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        Student student = (Student) getIntent().getExtras().getSerializable("STUDENT");
        title.setText(student.getTitle());
        body.setText(student.getBody());
        sub.setText(student.getSubject());
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_notes:

                delete_data();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), main_page.class));
    }

    public void delete_data()
    {
        final String sub1 = sub.getText().toString();
        final String title1 = title.getText().toString();
        final String body1 = body.getText().toString();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Note:- Data will be permanently delete..!")
                .setCancelable(true)
                .setTitle("Do you really want to Delete ?")
                .setIcon(R.drawable.ic_warning)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Boolean delete = db.delete(title1,body1,sub1);
                        if(delete == true)
                        {
                            Toast.makeText(getApplicationContext(),"Record Deleted... :)",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),notes_activity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Problem In Deletion",Toast.LENGTH_SHORT).show();
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
    }
}
