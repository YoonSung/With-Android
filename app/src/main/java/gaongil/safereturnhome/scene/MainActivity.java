package gaongil.safereturnhome.scene;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.eventbus.OttoBus;
import gaongil.safereturnhome.model.UserStatus;
import gaongil.safereturnhome.network.WithNetwork;
import gaongil.safereturnhome.support.*;
import gaongil.safereturnhome.fragment.*;

@EActivity(R.layout.activity_main)
@WindowFeature({Window.FEATURE_NO_TITLE})
public class MainActivity extends FragmentActivity {

    @Bean
    OttoBus bus;

    @App
    WithApp app;

    /**
     * Drawer
     */
    @ViewById(R.id.main_toolbar_left_toggle)
    ImageButton leftDrawerToggle;

    @ViewById(R.id.main_toolbar_right_toggle)
    ImageButton rightDrawerToggle;

    @ViewById(R.id.drawer_main_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.drawer_main_left)
    View leftDrawerLayout;

    @ViewById(R.id.drawer_main_right)
    View rightDrawerLayout;

    //@FragmentArg
    MainLeftDrawerFragment mainLeftDrawerFragment;

    /**
     * MainContents
     */
    @ViewById(R.id.main_btn_addgroup)
    Button addGroup;

    @ViewById(R.id.main_user_img_profile)
    ImageView userProfile;

    @ViewById(R.id.main_user_img_emoticon)
    ImageView userEmoticon;

    @ViewById(R.id.main_user_txt_currentstatus)
    TextView currentStatus;

    @ViewById(R.id.main_user_txt_returnhome_time)
    TextView alarmTime;


    /**
     * Common Data
     */
    private int profileSize;

    @Pref
    PreferenceUtil_ preferenceUtil;

    @AfterViews
    public void init() {
        bus.register(this);
        setupDrawer();
        setupCommonData();

        //TODO
        //setupSensorInfo();


    }

    boolean isFirst = true;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(isFirst && hasFocus){
            isFirst = false;
            animate();
        }
    }

    /**
     * Animation Component
     */

    // User Status Layout
    @ViewById(R.id.main_linearlayout_user_status)
    LinearLayout userStatusLayout;

    // Test Group Layout TODO DELETE
    @ViewById(R.id.main_group_testgroup)
    RelativeLayout testUserGroup;

    // AddGroup Button. Upper Declared

    // Left, Right Toggle, Upper Declared

    @UiThread(delay = 200)
    void animate() {

        setVisible(new View[]{
                userStatusLayout,
                testUserGroup,
                addGroup,
                leftDrawerToggle,
                rightDrawerToggle
        });

        YoYo.with(Techniques.FadeInLeft)
                .duration(700)
                .playOn(leftDrawerToggle);

        YoYo.with(Techniques.FadeInRight)
                .duration(700)
                .playOn(rightDrawerToggle);

        YoYo.with(Techniques.FadeInUp)
                .duration(500)
                .playOn(userStatusLayout);

        YoYo.with(Techniques.FadeInUp)
                .duration(600)
                .playOn(testUserGroup);

        YoYo.with(Techniques.FadeInUp)
                .duration(600)
                .playOn(addGroup);

    }

    private void setVisible(View[] views) {
        for(View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    //It is need to fragment onActivityResult Called (Fragment Request startActivityForResult, result method called in main activity)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mainLeftDrawerFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Click(R.id.main_btn_addgroup)
    void addGroup() {

        //startActivity(new Intent(MainActivity.this, GroupActivity_.class));
        test();
    }

    @Background
    void test() {
        //boolean result = app.NETWORK.testRequest("test");
        //Log.i(Constant.TAG, "Network Request Result : "+result);

        //call @UIThread method if you manupulate ui component and interaction
    }

    @Click(R.id.main_toolbar_left_toggle)
    void leftToggle(View v) {
        drawerLayout.closeDrawer(rightDrawerLayout);
        drawerLayout.openDrawer(leftDrawerLayout);
    }

    @Click(R.id.main_toolbar_right_toggle)
    void rightToggle(View v) {
        drawerLayout.closeDrawer(leftDrawerLayout);
        drawerLayout.openDrawer(rightDrawerLayout);
    }

    @Subscribe
    public void updateStatus(UserStatus userStatus) {
        currentStatus.setText(userStatus.getStringValue(this));
        userEmoticon.setImageDrawable(getResources().getDrawable(userStatus.getImageResourceId()));
    }

    @Subscribe
    public void updateAlarm(String displayTime) {
        alarmTime.setText(displayTime);
    }

    @Subscribe
    public void updateProfile(Drawable profile) {
        ImageUtil.setCircleImageToTargetView(userProfile, profile);
    }

    private void setupCommonData() {
        profileSize = preferenceUtil.profileSize().get();

        //Appripriate Image Size Set
        LayoutParams profileLayoutParam = userProfile.getLayoutParams();
        profileLayoutParam.width = profileSize;
        profileLayoutParam.height = profileSize;
    }

    private void setupDrawer() {

        mainLeftDrawerFragment = new MainLeftDrawerFragment_();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.drawer_main_left, mainLeftDrawerFragment);
        fragmentTransaction.replace(R.id.drawer_main_right, new MainRightDrawerFragment_());
        fragmentTransaction.commit();

        DrawerLayout.DrawerListener drawerListener = new DrawableListener(findViewById(R.id.main_content_layout), drawerLayout, leftDrawerLayout, rightDrawerLayout);
        drawerLayout.setDrawerListener(drawerListener);
    }

    //TODO DELETE
    public void tempEventHandler(View v) {
        //MainActivity_.intent(ChatActivity_).start();
        startActivity(new Intent(MainActivity.this, ChatActivity_.class));
        overridePendingTransition(R.anim.activity_sliding_up,R.anim.activity_stop);
    }

}
