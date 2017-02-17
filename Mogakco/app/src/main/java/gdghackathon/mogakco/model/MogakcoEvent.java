package gdghackathon.mogakco.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by choijinjoo on 2017. 2. 16..
 */

@IgnoreExtraProperties
public class MogakcoEvent implements Serializable{
    String name;
    String image_url;
    String description;
    String date;
    String type;

    String address;
    float latitude;
    float longitude;

    public MogakcoEvent() {
    }

    public MogakcoEvent(String name) {
        this.name = name;
    }

    public MogakcoEvent(String name, String image_url) {
        this.name = name;
        this.image_url = image_url;
    }

    public MogakcoEvent(String name, String image_url, float latitude, float longitude) {
        this.name = name;
        this.image_url = image_url;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
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
}
