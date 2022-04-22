package h052104.gaiaestate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private static final String LOG_TAG = RegisterActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void cancel(View view) {

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
                    Toast.makeText(RegisterActivity.this, "!! Firebase task: createUserWithEmailAndPassword failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void startApp(){
        Intent i = new Intent(this, BrowserActivity.class);
        startActivity(i);
    }
}