package tiimiss.globalgps.ba.tiimiss.Utillities;

import android.app.Application;

/**
 * Created by User 3 on 12.7.2016.
 */
public class GlobalVariables extends Application {
    private String latitudeStartLocation;
    private String longitudeStartLocation;
    private String latitudeEndLocation;
    private String longitudeEndLocation;
    private String taskName, userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLatitudeStartLocation() {
        return latitudeStartLocation;
    }

    public void setLatitudeStartLocation(String latitudeStartLocation) {
        this.latitudeStartLocation = latitudeStartLocation;
    }

    public String getLongitudeStartLocation() {
        return longitudeStartLocation;
    }

    public void setLongitudeStartLocation(String longitudeStartLocation) {
        this.longitudeStartLocation = longitudeStartLocation;
    }

    public String getLatitudeEndLocation() {
        return latitudeEndLocation;
    }

    public void setLatitudeEndLocation(String latitudeEndLocation) {
        this.latitudeEndLocation = latitudeEndLocation;
    }

    public String getLongitudeEndLocation() {
        return longitudeEndLocation;
    }

    public void setLongitudeEndLocation(String longitudeEndLocation) {
        this.longitudeEndLocation = longitudeEndLocation;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
