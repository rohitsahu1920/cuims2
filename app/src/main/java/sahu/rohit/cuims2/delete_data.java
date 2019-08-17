package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class delete_data extends AppCompatActivity {

    Button delete;
    EditText title,body,subject;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        delete = findViewById(R.id.notedeleteButton);
        title = findViewById(R.id.noteTitle);
        body = findViewById(R.id.noteBody);
        subject = findViewById(R.id.subjectNote);

        db = new DatabaseHelper(this);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title1 = title.getText().toString();
                String body1 = body.getText().toString();
                String sub1 = subject.getText().toString();

                if(title1.isEmpty() || body1.isEmpty() || sub1.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please fill all the Details",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Boolean ins = db.delete(sub1);
                    if(ins == true)
                    {
                        Toast.makeText(getApplicationContext(),"Note Delete Successfully",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),notesShow.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"There is problem in creating notes. Please contact to developer",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });




    }
}
