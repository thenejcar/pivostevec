package si.kisek.pivovarna.pivostevec.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import si.kisek.pivovarna.pivostevec.R;
import si.kisek.pivovarna.pivostevec.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private EditText address;
    private EditText userId;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Utils.getApiAddress(this) != null && Utils.getUserId(this) != null) {
            // user is logged in, go straight to Main
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        } else {


            setContentView(R.layout.activity_login);
            address = (EditText) findViewById(R.id.address);
            userId = (EditText) findViewById(R.id.userId);
            button = (Button) findViewById(R.id.button);
        }
    }

    public void onLogin(View view) {
        String addrString = address.getText().toString();
        String userIdString = userId.getText().toString();

        //TODO more validation (maybe even ping the server to confirm?)
        if (addrString.length() > 0 && userIdString.length() > 0) {

            if (addrString.charAt(addrString.length()-1) != '/')
                addrString += "/";

            Utils.storeApiAddress(this, addrString);
            Utils.storeUserId(this, userIdString);

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
