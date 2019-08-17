package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;

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

                String sub1 = sub.getText().toString();
                Boolean delete = db.delete(sub1);
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
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
