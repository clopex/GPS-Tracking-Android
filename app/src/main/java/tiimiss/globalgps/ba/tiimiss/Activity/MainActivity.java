package tiimiss.globalgps.ba.tiimiss.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Utillities.BaseUtillity;
import tiimiss.globalgps.ba.tiimiss.Utillities.NetworkCheck;

public class MainActivity extends AppCompatActivity {

    EditText etUserName, etPassword;
    private String userID = "sss";
    //Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        etUserName = (EditText)findViewById(R.id.input_username);
        etPassword = (EditText)findViewById(R.id.input_password);
        //btnLogin = (Button)findViewById(R.id.btnLogin);

    }

    public void loginClicked(View v) {
        setupKeyborad();
        NetworkCheck networkCheck = new NetworkCheck();

        if (networkCheck.isAvailable(this)){
            getUserID();
        } else {
            Toast toast = Toast.makeText(this, "Internet not available", Toast.LENGTH_SHORT);
            toast.show();
        }

        Log.i("ID", userID);

    }

    private void getUserID() {
        String url = "http://api.tiimiss.com:1337/auth/local";
        Map<String, String> params = new HashMap();
        params.put("identifier", String.valueOf(etUserName.getText()));
        params.put("password", String.valueOf(etPassword.getText()));

        JSONObject parametars = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parametars, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Intent intent = new Intent(MainActivity.this, Tasks.class);
                    intent.putExtra("USERID", response.getString("id"));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    //userID = response.getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    private void setupKeyborad() {
        etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    BaseUtillity.hideKeyboard(v);
                }
            }
        });

        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    BaseUtillity.hideKeyboard(v);
                }
            }
        });
    }
}
