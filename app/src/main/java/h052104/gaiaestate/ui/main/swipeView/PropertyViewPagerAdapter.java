package h052104.gaiaestate.ui.main.swipeView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;

import h052104.gaiaestate.R;
import h052104.gaiaestate.model.Property;

public class PropertyViewPagerAdapter extends PagerAdapter {
    private Context ctx;
    private ArrayList<Property> properties;

    public PropertyViewPagerAdapter(Context ctx, ArrayList<Property> properties) {
        this.ctx = ctx;
        this.properties = properties;
    }



    @Override
    public int getCount() {
        return properties.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.property_view, container, false);

        ImageView imageView = view.findViewById(R.id.propertyImageView);
        TextView title = view.findViewById(R.id.propertyNameText);
        TextView location = view.findViewById(R.id.propertyPlaceText);
        TextView price = view.findViewById(R.id.propertyPriceText);
        TextView area = view.findViewById(R.id.propertyAreaText);

        Property prop = properties.get(position);

        Bitmap image = prop.getImage();
        imageView.setImageBitmap(image);
        title.setText(prop.getTitle());
        location.setText(prop.getLocation());
        String priceString = prop.getPriceInMillion() + " M. Ft";
        price.setText(priceString);
        area.setText(prop.getArea() + "m²");

        container.addView(view, 0);
        // Previously indexnek a position-t használtam
        // Sajnos nem értem miért 0 az index, itt nem találtam több infót
        // https://developer.android.com/reference/androidx/viewpager/widget/PagerAdapter#instantiateItem(android.view.ViewGroup,int)
        // de aimango azt mondta így jó lesz és jó lett
        // ¯\_(ツ)_/¯
        // https://stackoverflow.com/questions/9402970/android-viewpager-throwing-indexoutofbounds-exception-when-setting-current-item

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
