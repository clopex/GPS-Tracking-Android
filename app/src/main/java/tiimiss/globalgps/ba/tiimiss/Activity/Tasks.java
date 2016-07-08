package tiimiss.globalgps.ba.tiimiss.Activity;

import android.content.Intent;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

import tiimiss.globalgps.ba.tiimiss.Adapters.TasksAdapter;
import tiimiss.globalgps.ba.tiimiss.Dividers.DividerItems;
import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Utillities.ItemTasks;

public class Tasks extends AppCompatActivity {

    private final String TAG = "Tasks";
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TasksAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

       // ActionBar actionBar = getSupportActionBar();
        //actionBar.setIcon();


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItems(this));
        mLayoutManager = new LinearLayoutManager(Tasks.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getTasks();
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

    private void getTasks() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.com/bins/35jxq";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response);
                GsonBuilder builder = new GsonBuilder();
                Gson mGson = builder.create();
                List<ItemTasks> posts;
                posts = Arrays.asList(mGson.fromJson(response, ItemTasks[].class));
                mAdapter = new TasksAdapter(Tasks.this, posts);
                mRecyclerView.setAdapter(mAdapter);
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
