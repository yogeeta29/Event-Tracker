package com.example.narayan.event;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventActivity extends Activity {

    LinearLayout eventDetails;
    View events;
    TextView eventName,startTime,endTime;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        /*
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        setActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle;
       */


        eventDetails=(LinearLayout)findViewById(R.id.llEvents);

        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        events=inflater.inflate(R.layout.college_events,null);

        eventDetails.addView(events);

        eventName=(TextView)events.findViewById(R.id.tvEventName);
        startTime=(TextView)events.findViewById(R.id.tvStartTime);
        endTime=(TextView)events.findViewById(R.id.tvEndTime);

    }


}
