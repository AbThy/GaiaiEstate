package h052104.gaiaestate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Button events
    public void googleLogin(View view) {
        // TODO
    }

    public void login(View view) {
        String userName = ((EditText)findViewById(R.id.editTextUserName)).getText().toString();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String logMessage = "User login: " + userName + ", with password: " + password + " .";
        Log.i(LOG_TAG, logMessage);
    }

    public void loginAsAnon(View view) {
        // TODO
    }

    public void openRegisterActivity(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}