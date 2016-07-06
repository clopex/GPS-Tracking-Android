package tiimiss.globalgps.ba.tiimiss.Utillities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adismulabdic on 7/3/16.
 */
public class ItemTasks {

    @SerializedName("task")
    private String taskName;
    @SerializedName("destFromLat")
    private String destFromLat;
    @SerializedName("destFromLng")
    private String destFromLng;
    @SerializedName("destToLat")
    private String destToLat;
    @SerializedName("destToLng")
    private String destToLng;
    @SerializedName("taskdesc")
    private String taskdesc;

    public ItemTasks(String taskName, String destFromLat, String destFromLng, String destToLat, String destToLng, String taskdesc) {
        this.taskName = taskName;
        this.destFromLat = destFromLat;
        this.destFromLng = destFromLng;
        this.destToLat = destToLat;
        this.destToLng = destToLng;
        this.taskdesc = taskdesc;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDestFromLat() {
        return destFromLat;
    }

    public String getDestFromLng() {
        return destFromLng;
    }

    public String getDestToLat() {
        return destToLat;
    }

    public String getDestToLng() {
        return destToLng;
    }

    public String getTaskdesc() {
        return taskdesc;
    }
}
