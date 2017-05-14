package gdghackathon.mogakco.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mk on 2017-02-18.
 */

public class Event {
    public String eventId;
    public String address;
    public String date;
    public String description;
    public String image_url;
    public String participants;
    public String latlng;
    public String name;
    public String type;
    private static final String TAG = Event.class.getSimpleName();

    public Event() {
    }

    public Event(String address, String date, String description, String image_url, String latlng, String name, String type) {
        this.address = address;
        this.date = date;
        this.description = description;
        this.image_url = image_url;
        this.latlng = latlng;
        this.name = name;
        this.type = type;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("address", address);
        result.put("date", date);
        result.put("description", description);
        result.put("image_url", image_url);
        result.put("latlng", latlng);
        result.put("name", name);
        result.put("type", type);
        return result;
    }

    public static Event parseSnapshot(DataSnapshot snapshot) {
        Event event = new Event();
        event.address = (String) snapshot.child("address").getValue();
        event.date = (String) snapshot.child("date").getValue();
        Log.d(TAG,"날짜 테스트 in parseSnapshot : " + event.date);
        event.description = (String) snapshot.child("description").getValue();
        event.image_url = (String) snapshot.child("image_url").getValue();
        event.latlng = (String) snapshot.child("latlng").getValue();
        event.name = (String) snapshot.child("name").getValue();
        event.type = (String) snapshot.child("type").getValue();

        return event;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    //FIXME     .....
    public MogakcoEvent toMogakcoEvent() {
        MogakcoEvent mogakcoEvent = new MogakcoEvent();
        mogakcoEvent.setEventId(eventId);
        mogakcoEvent.setParticipants(getParticipants());
        mogakcoEvent.setAddress(getAddress());
        mogakcoEvent.setDescription(getDescription());
        mogakcoEvent.setImage(getImage_url());
        mogakcoEvent.setLatlng(getLatlng());
        mogakcoEvent.setName(getName());
        mogakcoEvent.setType(getType());
        return mogakcoEvent;
    }
}
