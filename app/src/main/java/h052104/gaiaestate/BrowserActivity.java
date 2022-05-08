package h052104.gaiaestate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import h052104.gaiaestate.model.Property;
import h052104.gaiaestate.ui.main.swipeView.Transformator;
import h052104.gaiaestate.ui.main.swipeView.PropertyViewPagerAdapter;


public class BrowserActivity extends AppCompatActivity {

    private FirebaseUser user;
    private ViewPager viewPager;
    private PropertyViewPagerAdapter adapter;
    private ArrayList<Property> properties;

    private int maxPrice = 999;
    private TextView maxPriceText;
    private SeekBar seekBar;

    private FirebaseFirestore firestore;
    private CollectionReference firestoreItems;
    private StorageReference storageReference;

    private NotificationHandler notifHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        properties = new ArrayList<>();
        // Secret key - app safety
        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secretKey != 4254)
            finish();

        // Authentication - Get user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Firestore
        firestore = FirebaseFirestore.getInstance();
        firestoreItems = firestore.collection("property");

        // Search
        maxPriceText = findViewById(R.id.maximumPriceTextView);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                maxPrice = i;
                maxPriceText.setText(i + "M Ft");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Notifications
        notifHandler = new NotificationHandler(this);

        // Pager & data
        viewPager = findViewById(R.id.pager);
        adapter = new PropertyViewPagerAdapter(this, properties);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new Transformator());
        viewPager.setPadding(30,0,30,0);
        queryData();

        // Animate upload button
        Animation animation = AnimationUtils.loadAnimation(findViewById(R.id.uploadButton).getContext(), R.anim.button_slide);
        findViewById(R.id.uploadButton).startAnimation(animation);
    }

    private void queryData() {
        properties.clear();
        notifHandler.send("Downloading properties.");
        firestoreItems.orderBy("uploadDate").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    Property p = document.toObject(Property.class);
                    storageReference = FirebaseStorage.getInstance().getReference("propertyImages/" + p.getImageKey());
                    try {
                        File local = File.createTempFile("tempFile", ".jpg");
                        storageReference.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                                p.setImage(bitmap);
                                properties.add(p);
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch(Exception ex) {
                        // TODO
                    }
                }
        });
    }

    public void queryByPrice(View view) {
        properties.clear();
        notifHandler.send("Downloading properties under " + maxPrice + " million.");
        adapter = new PropertyViewPagerAdapter(this, properties);
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new Transformator());
        viewPager.setPadding(30,0,30,0);
        firestoreItems.orderBy("priceInMillion").limit(10).whereLessThanOrEqualTo("priceInMillion",maxPrice).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                Property p = document.toObject(Property.class);
                storageReference = FirebaseStorage.getInstance().getReference("propertyImages/" + p.getImageKey());
                try {
                    File local = File.createTempFile("tempFile", ".jpg");
                    storageReference.getFile(local).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(local.getAbsolutePath());
                            p.setImage(bitmap);
                            properties.add(p);
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch(Exception ex) {
                    // TODO
                }
            }
        });
    }

    public void openUpload(View view){
        Intent i = new Intent(this, UploadActivity.class);
        i.putExtra("SECRET_KEY", 4254);
        startActivity(i);
    }

}