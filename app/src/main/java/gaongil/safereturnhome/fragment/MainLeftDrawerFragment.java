package gaongil.safereturnhome.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.util.HashMap;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.adapter.StatusSpinnerAdapter;
import gaongil.safereturnhome.eventbus.OttoBus;
import gaongil.safereturnhome.model.UserStatus;
import gaongil.safereturnhome.support.*;


@EFragment(R.layout.drawer_main_left)
public class MainLeftDrawerFragment extends Fragment {

    @Pref
    PreferenceUtil_ preferenceUtil;

    @ViewById(R.id.drawer_main_left_user_spinner_status)
    Spinner mLeftDrawerStatusSpinner;

    @ViewById(R.id.drawer_main_left_user_img_profile)
    ImageButton mLeftDrawerProfileImageButton;

    @ViewById(R.id.drawer_main_left_user_btn_alarm)
    Button mLeftDrawerAlarmButton;

    @ViewById(R.id.main_user_txt_currentstatus)
    TextView mMainTextViewCurrentStatus;

    @Bean
    OttoBus bus;

    //TimePickerDialog
    private TimePickerDialogFragment mTimePickerDialogFragment;
    private int mProfileSize;


    @AfterViews
    void init() {
        bus.register(this);

        mProfileSize = preferenceUtil.profileSize().get();

        mTimePickerDialogFragment = new TimePickerDialogFragment_();
        initSpinner();
        initProfileImage();

        updateAlarmView(
                preferenceUtil.alarmHour().get(),
                preferenceUtil.alarmMinute().get()
        );

        updateStatusView(preferenceUtil.statusEnumPosition().get());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bus.unregister(this);
    }

    @Click(R.id.drawer_main_left_user_img_profile)
    void modifyProfile() {
        Crop.pickImage(this.getActivity());
    }

    @Click(R.id.drawer_main_left_user_btn_alarm)
    void modifyAlarm() {
        Bundle bundleToTimePickerDialogFragment = new Bundle();
        bundleToTimePickerDialogFragment.putInt(
                Constant.BUNDLE_KEY_TIMEPICKER_HOUR,
                preferenceUtil.alarmHour().get()
        );
        bundleToTimePickerDialogFragment.putInt(
                Constant.BUNDLE_KEY_TIMEPICKER_MINUTE,
                preferenceUtil.alarmMinute().get()
        );

        mTimePickerDialogFragment.setArguments(bundleToTimePickerDialogFragment);

        FragmentTransaction ft = this.getActivity().getSupportFragmentManager().beginTransaction();
        // Adding the fragment object to the fragment transaction
        ft.add(mTimePickerDialogFragment, Constant.TIME_PICKER);
        ft.commit();
    }


    private void initSpinner() {
        /**
         * Spinner Setting
         */

        StatusSpinnerAdapter statusSpinnerAdapter = new StatusSpinnerAdapter(this.getActivity(), R.layout.status_list_row, UserStatus.getList());
        mLeftDrawerStatusSpinner.setAdapter(statusSpinnerAdapter);
        mLeftDrawerStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            private boolean isInitializedCall = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isInitializedCall) {
                    isInitializedCall = false;
                    return;
                }

                int enumPosition = view.getId();

                //TODO check if status same previous status, did not update anithing especially network things
                preferenceUtil.statusEnumPosition().put(enumPosition);
                updateStatusView(enumPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initProfileImage() {
        ViewGroup.LayoutParams profileLayoutParam = mLeftDrawerProfileImageButton.getLayoutParams();
        profileLayoutParam.width = mProfileSize;
        profileLayoutParam.height = mProfileSize;

        Drawable profile = ImageUtil.getProfileImage(getActivity(), Constant.PROFILE_IMAGE_NAME);

        ImageUtil.setCircleImageToTargetView(mLeftDrawerProfileImageButton, profile);

        //update MainActivity Content
        bus.post(profile);
    }

    @Subscribe
    public void updateAlarmView(HashMap<String, Integer> params) {
        updateAlarmView(
                params.get(Constant.OTTO_KEY_HOUR),
                params.get(Constant.OTTO_KEY_MINUTE)
        );
    }


    /**
     * *********************************************************************
     * Update View Area Start
     */

    private void updateProfileView(Bitmap profile) {

        if (profile == null) {
            return;
        }

        ImageUtil.setCircleImageToTargetView(mLeftDrawerProfileImageButton, profile);

        //update MainActivity Content
        bus.post(new BitmapDrawable(getResources(),profile));
    }

    private void updateAlarmView(int hour, int minute) {
        /**
         * Change Button Text
         */
        String timezone = Constant.TIME_ZONE_AM;
        if (hour > 12) {
            timezone = Constant.TIME_ZONE_PM;
            hour -= 12;
        }

        String displayTime = String.format("%s %02d:%02d", timezone, hour, minute);
        mLeftDrawerAlarmButton.setText(displayTime);

        //update MainActivity Content
        bus.post(displayTime);
    }

    private void updateStatusView(int position) {
        mLeftDrawerStatusSpinner.setSelection(position);
        UserStatus userStatus = UserStatus.getList().get(position);


        Log.d(Constant.TAG, userStatus.toString());

        //update MainActivity Content
        bus.post(userStatus);
    }
    /*
     * Update View Area End
	 ************************************************************************/


    /**
     * *********************************************************************
     * LeftDrawer Image Crop Start
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        if (requestCode == Crop.REQUEST_PICK && resultCode == this.getActivity().RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri outputUri = Uri.fromFile(new File(this.getActivity().getCacheDir(), "cropped"));
        new Crop(source).output(outputUri).asSquare().start(this.getActivity());
    }

    private void handleCrop(int resultCode, Intent result) {

        if (resultCode == this.getActivity().RESULT_OK) {
            Bitmap croppedImage = null;

            try {
                croppedImage = StaticUtils.scaleBitmap(this.getActivity(), Crop.getOutput(result), mProfileSize, mProfileSize);
            } catch (Exception e) {
                //TODO
                e.printStackTrace();
            }

            // TODO Send Image by Network. (All Device Common Size Image)
            // Save Proper Image

            try {
                //ImageUtil.saveProfileImage(croppedImage, ""+getGenerateUserId());
                //TODO Replace upper script
                ImageUtil.saveProfileImage(getActivity(), croppedImage, Constant.PROFILE_IMAGE_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }

            updateProfileView(croppedImage);

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this.getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    /*
	 * LeftDrawer Image Crop End
	 ************************************************************************/
}
