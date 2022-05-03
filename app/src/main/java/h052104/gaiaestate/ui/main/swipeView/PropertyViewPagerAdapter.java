package h052104.gaiaestate.ui.main.swipeView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

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

        Property prop = properties.get(position);

        int image = (R.drawable.property0);
        imageView.setImageResource(image);
        title.setText(prop.getTitle());
        location.setText(prop.getLocation());
        String priceString = "" + prop.getPriceInMillion();
        price.setText(priceString);

        container.addView(view, position);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}