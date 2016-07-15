package tiimiss.globalgps.ba.tiimiss.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tiimiss.globalgps.ba.tiimiss.Adapters.TasksAdapter;
import tiimiss.globalgps.ba.tiimiss.Dividers.DividerItems;
import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Utillities.TaskItems;

public class Tasks extends AppCompatActivity {

    private final String TAG = "Tasks";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TasksAdapter mAdapter;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

       /* ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setLogo(R.drawable.tiimiss_log);
        actionBar.setDisplayUseLogoEnabled(true);*/

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItems(this));
        mLayoutManager = new LinearLayoutManager(Tasks.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Intent i = getIntent();
        userID = i.getExtras().getString("USERID");

        //new getJson().execute("http://api.tiimiss.com:1338/task/getTasksForUser/577ffbd32fd921472931cad5");
        new getJson().execute("http://api.tiimiss.com:1337/task/getTasksForUser/"+userID);
        //String test = "http://api.tiimiss.com:1338/task/getTasksForUser/"+userID;
        //Log.i("URL",test );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

   public class getJson extends AsyncTask<String, String, String>{
       @Override
       protected void onPostExecute(String s) {
           List<TaskItems> data = new ArrayList<>();

           try {
               JSONArray jsonArray = new JSONArray(s);

               /*JSONObject jsonObject = jsonArray.getJSONObject(0);
               String name = jsonObject.getJSONObject("body").getJSONObject("tms").getJSONObject("route").getString("name");
               String longitude = jsonObject.getJSONObject("body").getJSONObject("tms").getJSONObject("route").getJSONObject("startLocation").getJSONArray("coordinates").getString(0);
               Log.i("PROBA", name + ":"+longitude);*/
               for (int i=0; i<jsonArray.length(); i++){
                   JSONObject jsonObject = jsonArray.getJSONObject(i);
                   TaskItems taskItems = new TaskItems();
                   taskItems.taskName = jsonObject.getString("name");
                   taskItems.latitudeStartLocation = jsonObject.getJSONObject("body").getJSONObject("tms").getJSONObject("route").getJSONObject("startLocation").getJSONArray("coordinates").getString(0);
                   taskItems.longitudeStartLocation = jsonObject.getJSONObject("body").getJSONObject("tms").getJSONObject("route").getJSONObject("startLocation").getJSONArray("coordinates").getString(1);
                   taskItems.latitudeEndLocation = jsonObject.getJSONObject("body").getJSONObject("tms").getJSONObject("route").getJSONObject("endlocation").getJSONArray("coordinates").getString(0);
                   taskItems.longitudeEndLocation = jsonObject.getJSONObject("body").getJSONObject("tms").getJSONObject("route").getJSONObject("endlocation").getJSONArray("coordinates").getString(1);
                   data.add(taskItems);
               }


               mAdapter = new TasksAdapter(Tasks.this, data);
               mRecyclerView.setAdapter(mAdapter);
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       @Override
       protected String doInBackground(String... params) {
           HttpURLConnection connection = null;
           BufferedReader reader = null;

           try {
               URL url = new URL(params[0]);
               connection = (HttpURLConnection)url.openConnection();
               connection.connect();

               int response_code = connection.getResponseCode();
               if (response_code == HttpURLConnection.HTTP_OK){
                   InputStream stream = connection.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(stream));
                   StringBuffer buffer = new StringBuffer();
                   String line;
                   while ((line = reader.readLine()) != null){
                       buffer.append(line);
                   }
                   return (buffer.toString());
               } else {
                   return ("error");
               }

           } catch (MalformedURLException e) {
               e.printStackTrace();
               return e.toString();
           } catch (IOException e) {
               e.printStackTrace();
               return e.toString();
           } finally {
               connection.disconnect();
           }
           //return null;
       }
   }

    private void getTasks() {

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://api.myjson.com/bins/35jxq";
        String url = "http://api.tiimiss.com:1338/task/getTasksForUser/577ffbd32fd921472931cad5";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                //String testString =
                /*GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<ItemTasks> posts;
                posts = Arrays.asList(mGson.fromJson(response, ItemTasks[].class));
                mAdapter = new TasksAdapter(Tasks.this, posts);
                mRecyclerView.setAdapter(mAdapter);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error "+error.getMessage());
            }
        });
        queue.add(stringRequest);

    }
}
