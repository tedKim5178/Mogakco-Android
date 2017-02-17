package gdghackathon.mogakco.model;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mk on 2017-02-18.
 */

public class Event {
    public String address;
    public String date;
    public String description;
    public String image_url;
    public String latlng;
    public String name;
    public String type;

    public Event(){}

    public Event(String address, String date, String description, String image_url, String latlng, String name, String type){
        this.address = address;
        this.date = date;
        this.description = description;
        this.image_url = image_url;
        this.latlng = latlng;
        this.name = name;
        this.type = type;
    }

    public Map<String, Object> toMap(){
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

    public static Event parseSnapshot(DataSnapshot snapshot){
        Event event = new Event();
        event.address = (String) snapshot.child("address").getValue();
        event.date = (String) snapshot.child("date").getValue();
        event.description = (String) snapshot.child("description").getValue();
        event.image_url = (String) snapshot.child("image_url").getValue();
        event.latlng = (String) snapshot.child("latlng").getValue();
        event.name = (String) snapshot.child("name").getValue();
        event.type = (String) snapshot.child("type").getValue();

        return event;
    }
}
