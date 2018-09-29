package com.example.narayan.event;

public class Events {

    String id;
    String eventname;
    String coordinator;
    String eventdate;
    String attendees;

    public Events() {

    }

    public Events(String id, String eventname, String coordinator, String eventdate, String attendees) {
        this.id = id;
        this.eventname = eventname;
        this.coordinator = coordinator;
        this.eventdate = eventdate;
        this.attendees = attendees;
    }

    public String getId() {
        return id;
    }

    public String getEventname() {
        return eventname;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public String getEventdate() {
        return eventdate;
    }

    public String getAttendees() {
        return attendees;
    }
}