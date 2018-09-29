package com.example.narayan.event;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CollegeEvents extends AppCompatActivity {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    List<Events> Eventslist;
    Events event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_events);

        listView = (ListView)findViewById(R.id.listView);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");

        Eventslist = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Eventslist.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()) // get the data under events table
                {
                    event = ds.getValue(Events.class);
                    Eventslist.add(event);
                }
                EventList adapter = new EventList(CollegeEvents.this,Eventslist);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               //Toast.makeText(getApplicationContext(),"Event Updated Succesfully",Toast.LENGTH_LONG).show();
                Events event = Eventslist.get(i);
                showUpdateDialog(event.getId(),event.getEventname());
                return false;
            }
        });*/


    }


}
