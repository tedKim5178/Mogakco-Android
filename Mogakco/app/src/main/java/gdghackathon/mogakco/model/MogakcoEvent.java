package gdghackathon.mogakco.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by choijinjoo on 2017. 2. 16..
 */

@IgnoreExtraProperties
public class MogakcoEvent implements Serializable {
    String eventId;
    String name;
    String image_url;
    String description;
    String date;
    String type;
    String participants;

    String address;
    public String latlng;
    float longitude;

    public MogakcoEvent() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public MogakcoEvent(String name) {
        this.name = name;
    }

    public MogakcoEvent(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
    }

    public MogakcoEvent(String name, String image_url, String latlng) {
        this.name = name;
        this.image_url = image_url;
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image_url;
    }

    public void setImage(String image) {
        this.image_url = image;
    }


    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("eventId", eventId);
        result.put("name", name);
        result.put("image_url", image_url);
        result.put("description", description);
        result.put("date", date);
        result.put("type", type);
        result.put("participants", participants);
        result.put("address", address);
        result.put("latlng", latlng);
        result.put("longitude", longitude);
        return result;
    }
}
