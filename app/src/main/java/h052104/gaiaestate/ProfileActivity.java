package h052104.gaiaestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Secret key - app safety
        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secretKey != 4254)
            finish();

        // Authentication - Get user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.isAnonymous()){
            Toast.makeText(ProfileActivity.this, "You have to log in to upload!",Toast.LENGTH_LONG).show();
            finish();
        }
    }
}