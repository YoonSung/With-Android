package gaongil.safereturnhome.support;

import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * Created by yoon on 15. 1. 28..
 */
public class DrawableListener implements DrawerLayout.DrawerListener {

    private DrawerLayout drawerLayout;
    private View rightDrawerView, leftDrawerView;
    private View activity;


    public DrawableListener(View mainContentLayout, DrawerLayout drawerLayout, View leftDrawerView, View rightDrawerView) {
        this.drawerLayout = drawerLayout;
        this.leftDrawerView = leftDrawerView;
        this.rightDrawerView = rightDrawerView;
        this.activity = mainContentLayout;
    }


    // It's for lastTranslate Saved Variation
    private float lastTranslate = 0.0f;

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        // Its Drawer action Conflict Prevent Code
        if (drawerView == leftDrawerView) {
            drawerLayout.closeDrawer(rightDrawerView);
        } else {
            drawerLayout.closeDrawer(leftDrawerView);
        }

        if (drawerView == rightDrawerView) {
            // if rightDrawer Action, Opposite Direction Set
            slideOffset *= -1;
        }

        float moveFactor = (drawerLayout.getWidth() * Constant.DRAWER_SLIDE_WIDTH_RATE * slideOffset);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            activity.setTranslationX(moveFactor);

        } else {
            TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
            anim.setFillAfter(true);
            activity.startAnimation(anim);

            lastTranslate = moveFactor;
        }
    }

    @Override
    public void onDrawerOpened(View drawerView) {
    }

    @Override
    public void onDrawerClosed(View drawerView) {
    }

    @Override
    public void onDrawerStateChanged(int newState) {
    }
}
