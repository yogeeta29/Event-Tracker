package com.example.narayan.event;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateEvents extends MenuActivity {

    //private DatabaseReference databaseReference;

    EditText eventName, coordinator,eventdate;
    Button submit;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        submit = (Button) findViewById(R.id.submit);
        eventName = (EditText) findViewById(R.id.eventName);
        coordinator = (EditText) findViewById(R.id.coordinator);
        eventdate = (EditText) findViewById(R.id.eventdate);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(position)+ " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddEvent();
            }
        });
    }

    public void AddEvent() {
        String eventname = eventName.getText().toString().trim();
        String coord = coordinator.getText().toString().trim();
        String date = eventdate.getText().toString().trim();
        String attendee = "0";

        if (!TextUtils.isEmpty(eventname)) {
            String id = databaseEvents.push().getKey();
            Events events = new Events(id, eventname, coord, date,attendee);
            databaseEvents.child(id).setValue(events);
            Toast.makeText(this,"Event Added",Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You should enter a name", Toast.LENGTH_LONG).show();
        }

    }

}
