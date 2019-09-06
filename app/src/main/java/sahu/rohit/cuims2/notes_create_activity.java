package sahu.rohit.cuims2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class notes_create_activity extends AppCompatActivity {

    DatabaseHelper db;
    EditText title,body,sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_create_activity);

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

                if (title1.isEmpty() || body1.isEmpty() || sub1.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all the Details", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean ins = db.insert(title1, body1, sub1);
                    if (ins == true) {
                        Toast.makeText(getApplicationContext(), "Note saved Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(notes_create_activity.this, notes_activity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "There is problem in creating notes. Please contact to developer", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), main_page.class));
    }
}
