package com.example.narayan.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EventList extends ArrayAdapter<Events> {
    private Activity context;
    private List<Events> eventsList;
    FirebaseDatabase database;
    DatabaseReference ref;

    public EventList(Activity context, List<Events> eventsList) {
        super(context, R.layout.events_info, eventsList);
        this.context = context;
        this.eventsList = eventsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.events_info, null, true);

        //View listView = inflater.inflate(R.layout.activity_college_events, null, true);

        //listView = (ListView)listView.findViewById(R.id.listView);

       /* listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getContext(),"Event Updated Succesfully",Toast.LENGTH_LONG).show();
                return false;
            }
        });*/


        TextView eventname = (TextView) listViewItem.findViewById(R.id.eventname);
        TextView eventCoord = (TextView) listViewItem.findViewById(R.id.eventCoord);
        TextView eventdate = (TextView) listViewItem.findViewById(R.id.eventdate);
        ImageView leftArrow = (ImageView) listViewItem.findViewById(R.id.leftArrow);

        Button delete = (Button) listViewItem.findViewById(R.id.delete);
        Button update = (Button) listViewItem.findViewById(R.id.update);

        Events event = eventsList.get(position);

        eventname.setText(event.getEventname());
        eventCoord.setText(event.getCoordinator());
        eventdate.setText(event.getEventdate());

        final String attendees = event.getAttendees().toString();
        final String Id = event.getId().toString();

        final String name = String.valueOf(eventname.getText().toString());
        final String coord = String.valueOf(eventCoord.getText().toString());
        final String date = String.valueOf(eventdate.getText().toString());



        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteEvent(Id);
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUpdateDialog(Id,name,coord,date,attendees);
            }
        });


        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventDetails.class);
                intent.putExtra("Id", Id);
                intent.putExtra("name", name);
                intent.putExtra("coord", coord);
                intent.putExtra("date", date);
                intent.putExtra("attendees", attendees);


                context.startActivity(intent);
            }
        });

        return listViewItem;


    }

    private void showUpdateDialog(final String Id, String name, String coord, String date, final String attendees){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = context.getLayoutInflater();
        //View listViewItem = inflater.inflate(R.layout.events_info, null, true);
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText eventcoord = (EditText) dialogView.findViewById(R.id.coord);
        final TextView editText = (TextView) dialogView.findViewById(R.id.editText);
        Button Updatebutton = (Button) dialogView.findViewById(R.id.Updatebutton);
        final EditText eventdate = (EditText) dialogView.findViewById(R.id.eventdate);

        dialogBuilder.setTitle("Updating Event: " + name);

        eventcoord.setText(coord);
        editText.setText(name);
        eventdate.setText(date);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        Updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name= editText.getText().toString().trim();
                final String coordName= eventcoord.getText().toString().trim();
                final String date= eventdate.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    editText.setError("Name Required");
                    return;
                }

                /*database = FirebaseDatabase.getInstance();
                ref = database.getReference("events");

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds: dataSnapshot.getChildren()) // get the data under events table
                        {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(getContext(),"Duplicate",Toast.LENGTH_LONG).show();

                            }
                            else{

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

                updateEvents(Id,name,coordName,date,attendees);

                alertDialog.dismiss();
            }
        });



    }

    private boolean updateEvents(String id, String name,String coordName,String date,String attendees){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("events").child(id);
        Events event = new Events (id,name,coordName,date,attendees);
        databaseReference.setValue(event);
        Toast.makeText(getContext(),"Event Updated Succesfully",Toast.LENGTH_LONG).show();
        return true;
    }

    private void DeleteEvent(String eventId) {
        DatabaseReference dbEvent = FirebaseDatabase.getInstance().getReference("events").child(eventId);
        dbEvent.removeValue();

        Toast.makeText(getContext(),"Event is deleted", Toast.LENGTH_LONG).show();
    }

}

