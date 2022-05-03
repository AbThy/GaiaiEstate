package h052104.gaiaestate.model;

public class Property {

    String title;
    String location;
    float priceInMillion;

    public Property(String title, String location, float priceInMillion) {
        this.title = title;
        this.location = location;
        this.priceInMillion = priceInMillion;
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
}
