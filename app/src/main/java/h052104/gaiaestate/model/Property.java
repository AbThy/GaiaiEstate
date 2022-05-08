package h052104.gaiaestate.model;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.File;
import java.util.Date;

public class Property {

    String title;
    String location;
    float priceInMillion;
    int area;
    Date uploadDate;
    String imageKey;
    Bitmap image;

    public Property(String title, String location, float priceInMillion, int area, Date uploadDate, String imageKey) {
        this.title = title;
        this.location = location;
        this.priceInMillion = priceInMillion;
        this.area = area;
        this.uploadDate = uploadDate;
        this.imageKey = imageKey;
        image = null;
    }

    public Property(){
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPriceInMillion() {
        return priceInMillion;
    }

    public void setPriceInMillion(float priceInMillion) {
        this.priceInMillion = priceInMillion;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Date getUploadDate(){
        return uploadDate;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap bitmap){
        this.image = bitmap;
    }
}
