package tiimiss.globalgps.ba.tiimiss.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
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

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItems(this));
        mLayoutManager = new LinearLayoutManager(Tasks.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getTasks();
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
