package learn.example.com.readjsonfromassets;

/**
 * Created by jean on 8/16/2016.
 */
public class Routes {
    int id, isStopPoint, isActive, isShown;
    String routeNumber;
    String stopTitle, stopID, stopTitle_ru;
    Double latitude, longitude, stopLatitude, stopLongitude;

    public int getIsStopPoint() {
        return isStopPoint;
    }

    public int getIsActive() {
        return isActive;
    }

    public int getIsShown() {
        return isShown;
    }

    public String getStopID() {
        return stopID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getStopLatitude() {
        return stopLatitude;
    }

    public Double getStopLongitude() {
        return stopLongitude;
    }

    public int getId() {
        return id;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public String getStopTitle() {
        return stopTitle;
    }

    public String getStopTitle_ru() {
        return stopTitle_ru;
    }
}
