package com.example.narayan.event;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        final TextView eventname = (TextView) findViewById(R.id.eventname);
        final TextView eventdate = (TextView) findViewById(R.id.eventdate);
        final TextView eventcoord = (TextView) findViewById(R.id.eventcoord);
        Button attendbtn = (Button) findViewById(R.id.attendbtn);
        final TextView attendees = (TextView) findViewById(R.id.attendees);
        //final String attend  = String.valueOf(attendees.getText().toString());


        eventname.setText(getIntent().getStringExtra("name"));
        eventcoord.setText(getIntent().getStringExtra("coord"));
        eventdate.setText(getIntent().getStringExtra("date"));
        attendees.setText(getIntent().getStringExtra("attendees"));

        attendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddAttendee(attendees);
            }
        });

    }

        private void AddAttendee(TextView attendees) {
            String name = getIntent().getStringExtra("name");
            String coord = getIntent().getStringExtra("coord");
            String date = getIntent().getStringExtra("date");

            Integer attend = Integer.valueOf(getIntent().getStringExtra("attendees"));
            String Id = getIntent().getStringExtra("Id");
            attend = attend + 1;

            String temp = String.valueOf(attend);

            attendees.setText(temp);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events").child(Id);
            Events event = new Events (Id,name,coord,date,temp);
            databaseReference.setValue(event);
            //Toast.makeText(this,"Event Updated Succesfully",Toast.LENGTH_LONG).show();
        }
}
