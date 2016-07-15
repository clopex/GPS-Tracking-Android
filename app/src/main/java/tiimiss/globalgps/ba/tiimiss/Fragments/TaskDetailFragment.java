package tiimiss.globalgps.ba.tiimiss.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Utillities.GlobalVariables;

/**
 * Created by adismulabdic on 7/4/16.
 */
public class TaskDetailFragment extends Fragment{

    private static final String API_KEY ="&key=AIzaSyCn-mjLI1IsdC77biotl22KnpBP5rc4xYc";
    private static final String GOOGLEAPI_URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    String latlngStartLocation, latlngEndLocation;
    private RequestQueue requestQueue;

    TextView txtTaskName, txtStartLocation, txtEndLocation;

    public TaskDetailFragment(){}

    public static TaskDetailFragment newInstance(){
        TaskDetailFragment taskDetailFragment = new TaskDetailFragment();
        return taskDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_detail_layout, container, false);

        txtTaskName = (TextView)view.findViewById(R.id.txtTaskName);
        txtStartLocation = (TextView)view.findViewById(R.id.txtStartLocation);
        txtEndLocation = (TextView)view.findViewById(R.id.txtEndLocation);
        requestQueue = Volley.newRequestQueue(getContext());

        txtTaskName.setText(((GlobalVariables)getActivity().getApplication()).getTaskName());
        getAddressFromCoordinates();
        //txtStartLocation.setText(((GlobalVariables)getActivity().getApplication()).getLongitudeStartLocation());
        //txtEndLocation.setText(((GlobalVariables)getActivity().getApplication()).getLongitudeEndLocation());
        //Bundle bundle = this.getArguments();
        //txtTaskName.setText(bundle.getString("TaskName"));

        return view;
    }

    private void getAddressFromCoordinates() {
        latlngStartLocation = ((GlobalVariables)getActivity().getApplication()).getLatitudeStartLocation()+","+((GlobalVariables)getActivity().getApplication()).getLongitudeStartLocation();
        latlngEndLocation = ((GlobalVariables)getActivity().getApplication()).getLatitudeEndLocation()+","+((GlobalVariables)getActivity().getApplication()).getLongitudeEndLocation();
        String urlStartLocation = GOOGLEAPI_URL+latlngStartLocation+API_KEY;
        String urlEndLocation = GOOGLEAPI_URL+latlngEndLocation+API_KEY;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, urlStartLocation, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    txtStartLocation.setText(address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET, urlEndLocation, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String address = response.getJSONArray("results").getJSONObject(0).getString("formatted_address");
                    txtEndLocation.setText(address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
        requestQueue.add(request2);
    }
}
