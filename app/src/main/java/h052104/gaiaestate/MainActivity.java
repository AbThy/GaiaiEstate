package h052104.gaiaestate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private FirebaseAuth firebaseAuth;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        // Animate view
        Animation animation = AnimationUtils.loadAnimation(findViewById(R.id.mainPageView).getContext(), R.anim.fade_in);
        findViewById(R.id.mainPageView).startAnimation(animation);
    }

    public void login(View view) {
        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG, "Firebase task: signInWithEmailAndPassword succeeded!");
                    startApp();
                } else {
                    Log.d(LOG_TAG, "Error: firebaseAuth.createUserWithEmailAndPassword");
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void loginAsAnon(View view) {
        firebaseAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG, "Firebase task: signInAnonymously succeeded!");
                    startApp();
                } else {
                    Log.d(LOG_TAG, "Error: firebaseAuth.signInAnonymously");
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openRegisterActivity(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        i.putExtra("SECRET_KEY", 4254);
        startActivity(i);
    }

    public void startApp(){
        Intent i = new Intent(this, BrowserActivity.class);
        i.putExtra("SECRET_KEY", 4254);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", ((EditText)findViewById(R.id.editTextEmail)).getText().toString().trim());
        editor.apply();
    }

    @Override
    protected void onResume(){
        super.onResume();
        String emailDefault = preferences.getString("email", "");
        if(!emailDefault.equals("")){
            ((EditText) findViewById(R.id.editTextEmail)).setText(emailDefault);
        }
    }
}