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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = RegisterActivity.class.getPackage().toString();
    private FirebaseAuth firebaseAuth;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secretKey != 4254)
            finish();

        firebaseAuth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);

        // Animate view
        Animation animation = AnimationUtils.loadAnimation(findViewById(R.id.registerView).getContext(), R.anim.fade_in);
        findViewById(R.id.registerView).startAnimation(animation);
    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {
        String uName = ((EditText)findViewById(R.id.editTextUserName)).getText().toString().trim(); //TRIM!
        String email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString().trim();    //TRIM!
        String pass = ((EditText)findViewById(R.id.editTextPassword)).getText().toString();
        String passCheck = ((EditText)findViewById(R.id.editTextPasswordAgain)).getText().toString();
        CheckBox termsAcceptedCheckBox = findViewById(R.id.termsCheckBox);

        if(termsAcceptedCheckBox.isChecked() == false){
            Log.i(LOG_TAG, "Terms of use not checked!");
            Toast.makeText(RegisterActivity.this, "Please read and accept the terms of use!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!pass.equals(passCheck)) {
            Log.i(LOG_TAG, "Passwords do not match!");
            Toast.makeText(RegisterActivity.this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i(LOG_TAG, "Firebase task: createUserWithEmailAndPassword succeeded!");
                    startApp();
                } else {
                    Log.d(LOG_TAG, "Error: firebaseAuth.createUserWithEmailAndPassword");
                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

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
        editor.putString("userName", ((EditText)findViewById(R.id.editTextUserName)).getText().toString().trim());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String emailDefault = preferences.getString("email","");
        String userNameDefault = preferences.getString("userName","");
        if(!emailDefault.equals("") || !userNameDefault.equals("")) {
            Toast.makeText(RegisterActivity.this, "We restored some data from your last session!", Toast.LENGTH_SHORT).show();
        }
        if(!emailDefault.equals("")) {
            ((EditText) findViewById(R.id.editTextEmail)).setText(emailDefault);
            ((EditText) findViewById(R.id.editTextUserName)).setText(userNameDefault);
        }
    }
}