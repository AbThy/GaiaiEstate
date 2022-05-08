package h052104.gaiaestate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import h052104.gaiaestate.model.Property;

public class EditPropertyActivity extends AppCompatActivity {

    private Property p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        // Secret key - app safety
        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secretKey != 4254)
            finish();
    }
}