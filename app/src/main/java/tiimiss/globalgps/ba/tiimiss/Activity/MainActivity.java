package tiimiss.globalgps.ba.tiimiss.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import tiimiss.globalgps.ba.tiimiss.R;
import tiimiss.globalgps.ba.tiimiss.Utillities.BaseUtillity;

public class MainActivity extends AppCompatActivity {

    EditText etEmailAddress, etPassword;
    //Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayShowTitleEnabled(false);
        setContentView(R.layout.activity_main);

        etEmailAddress = (EditText)findViewById(R.id.input_email_address);
        etPassword = (EditText)findViewById(R.id.input_password);
        //btnLogin = (Button)findViewById(R.id.btnLogin);

    }

    public void loginClicked(View v) {
        setupKeyborad();
        Intent intent = new Intent(this, Tasks.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void setupKeyborad() {
        etEmailAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
