package h052104.gaiaestate;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import java.util.Random;

import h052104.gaiaestate.model.Property;
import h052104.gaiaestate.ui.main.swipeView.PropertyViewPagerAdapter;


public class BrowserActivity extends AppCompatActivity {

    private FirebaseUser user;
    private ViewPager viewPager;
    private PropertyViewPagerAdapter adapter;
    private ArrayList<Property> properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        // Secret key - app safety
        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secretKey != 4254)
            finish();

        // Authentication - Get user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Slider
        viewPager = findViewById(R.id.pager);
        loadProperties();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(BrowserActivity.this, "Selected property " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void loadProperties() {
        properties = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            Property p = new Property();
            p.setTitle(i + "-es ingatlan");
            p.setLocation("Biatorbágy");
            p.setPriceInMillion(new Random().nextFloat());
            properties.add(p);
        }

        adapter = new PropertyViewPagerAdapter(this, properties);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100,0,0,100);
    }

}