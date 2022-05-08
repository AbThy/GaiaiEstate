package h052104.gaiaestate;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import h052104.gaiaestate.model.Property;

public class UploadActivity extends AppCompatActivity {

    private static final String LOG_TAG = UploadActivity.class.getName();
    private FirebaseUser user;
    private FirebaseFirestore firestore;
    private CollectionReference firestoreItems;
    private Uri imageUri;
    private ImageView imagePreview;
    private FirebaseStorage fStorage;
    private StorageReference fSReference;
    public ActivityResultLauncher<String> getImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_property);
        // Secret key - app safety
        int secretKey = getIntent().getIntExtra("SECRET_KEY", 0);
        if(secretKey != 4254)
            finish();

        // Authentication - Get user
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.isAnonymous()){
            Toast.makeText(UploadActivity.this, "You must log in to upload!",Toast.LENGTH_LONG).show();
            finish();
        }

        // Animate view
        Animation animation = AnimationUtils.loadAnimation(findViewById(R.id.uploadView).getContext(), R.anim.fade_in);
        findViewById(R.id.uploadView).startAnimation(animation);

        // Firestore
        firestore = FirebaseFirestore.getInstance();
        firestoreItems = firestore.collection("property");

        // Storage
        fStorage = FirebaseStorage.getInstance();
        fSReference = fStorage.getReference();

        // Image preview for upload
        imagePreview = findViewById(R.id.uploadImagePreview);

        // Image selection intent
        getImage = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageUri = result;
                        imagePreview.setImageURI(imageUri);
                    }
                }
        );
    }

    public void cancel(View view) {
        finish();
    }

    public void upload(View view)
    {
        String title = ((EditText)findViewById(R.id.editTextPropertyTitle)).getText().toString();
        String location = ((EditText)findViewById(R.id.editTextLocation)).getText().toString();
        float price;
        int area;
        try {
            price = Float.parseFloat(((EditText) findViewById(R.id.editTextPrice)).getText().toString());
            area = Integer.parseInt(((EditText) findViewById(R.id.editTextSquareMeterArea)).getText().toString());
        } catch(Exception ex){
            Toast.makeText(UploadActivity.this, "Wrong format in price or property area fields!", Toast.LENGTH_SHORT).show();
            return;
        }
        Date uploadDate = new Date();
        String imageKeyInStorage = null;

        if(title.equals("") || location.equals("")){
            Toast.makeText(UploadActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        Resources res = this.getResources();
        if(imageUri == null){
            Toast.makeText(UploadActivity.this, "Please select an image for your property!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            imageKeyInStorage = uploadImage();
        } catch (Exception ex){
            Toast.makeText(UploadActivity.this, "Unable to upload picture! Please try again later.", Toast.LENGTH_SHORT).show();
        }

        Property p = new Property(title, location, price, area, uploadDate, imageKeyInStorage);
        try {
            firestoreItems.add(p);
        } catch (Exception ex){
            Log.e(LOG_TAG, "Error uploading property! - MSG: " + ex.getMessage());
            Toast.makeText(UploadActivity.this, "Error uploading property!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(UploadActivity.this, "You have uploaded your property \"" + title + "\"!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String uploadImage() throws Exception{
        final boolean[] uploadSuccess = {true};

        String key = UUID.randomUUID().toString();
        StorageReference imgStorage = fSReference.child("propertyImages/" + key + ".jpg");
        imgStorage.putFile(imageUri)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception ex){
                        uploadSuccess[0] = false;
                    }
                });

        if(uploadSuccess[0] == false){
            throw new Exception("Upload failed!");
        } else {
            return key + ".jpg";
        }
    }

    public void getImage(View view) {
        getImage.launch("image/jpeg");
    }
}