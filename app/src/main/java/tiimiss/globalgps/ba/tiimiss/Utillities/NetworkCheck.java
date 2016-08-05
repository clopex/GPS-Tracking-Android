package tiimiss.globalgps.ba.tiimiss.Utillities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by User 3 on 5.8.2016.
 */
public class NetworkCheck {

    public static boolean isAvailable(Context context) {
        boolean isInternetAvailable = false;

        try {
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && (networkInfo.isConnected())) {
                isInternetAvailable = true;
            }
        } catch (Exception e) {

        }
        return isInternetAvailable;
    }
}
