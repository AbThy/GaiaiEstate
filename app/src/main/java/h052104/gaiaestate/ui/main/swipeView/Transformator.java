package h052104.gaiaestate.ui.main.swipeView;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

//https://github.com/dipanshukr/Viewpager-Transformation/wiki/Zoom-Out-Transformation
public class Transformator implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.6f;
    private static final float MIN_ALPHA = 0.1f;

    @Override
    public void transformPage(View page, float position) {

        if (position <-1){
            page.setAlpha(0);
        }
        else if (position <=1){
            page.setScaleX(Math.max(MIN_SCALE,1-Math.abs(position)));
            page.setScaleY(Math.max(MIN_SCALE,1-Math.abs(position)));
            page.setAlpha(Math.max(MIN_ALPHA,1-Math.abs(position)));
        }
        else { page.setAlpha(0);}
    }
}