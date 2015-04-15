package gaongil.safereturnhome.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.TimePicker;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.HashMap;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.eventbus.OttoBus;
import gaongil.safereturnhome.support.*;

@EFragment
public class TimePickerDialogFragment extends DialogFragment {
	
    int mHour;
    int mMinute;

    @Pref
    PreferenceUtil_ preferenceUtil;

    @Bean
    OttoBus bus;

    //TODO DELETE
    @ViewById(R.id.drawer_main_left_user_btn_alarm)
    Button mLeftDrawerAlarmButton;

    // This handles the message send from TimePickerDialogFragment on setting Time
    Handler mTimePickerDialogHandler = new Handler(){
        @Override
        public void handleMessage(Message message){
            Bundle bundleFromTimePickerFragment = message.getData();

            int hour = bundleFromTimePickerFragment.getInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR);
            int minute = bundleFromTimePickerFragment.getInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE);


            // Save New Alarm Time
            preferenceUtil.alarmHour().put(hour);
            preferenceUtil.alarmMinute().put(minute);

            updateAlarmView(hour, minute);
        }
    };


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
    	//Creating a bundle object to pass currently set time to the fragment
    	Bundle b = getArguments();

    	//Getting the hour of day from bundle
        mHour = b.getInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR);

        //Getting the minute of hour from bundle
        mMinute = b.getInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE);
        
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
        	
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				
				mHour = hourOfDay;
                mMinute = minute;
                
                // Creating a bundle object to pass currently set time to the fragment
                Bundle bundle = new Bundle();

                // Adding currently set hour to bundle object
                bundle.putInt(Constant.BUNDLE_KEY_TIMEPICKER_HOUR, mHour);

                // Adding currently set minute to bundle object
                bundle.putInt(Constant.BUNDLE_KEY_TIMEPICKER_MINUTE, mMinute);
                
                // Creating an instance of Message
                Message message = new Message();

                // Setting bundle object on the message object m
                message.setData(bundle);

                // Message m is sending using the message handler instantiated in MainActivity class
                mTimePickerDialogHandler.sendMessage(message);
			}
		};        

        
		// Opening the TimePickerDialog window
		return new TimePickerDialog(getActivity(), listener, mHour, mMinute, false);
    }

    //TODO otto apply to MainLeftDrawerFraglement
    private void updateAlarmView(final int hour, final int minute) {
        bus.post(new HashMap<String, Integer>(){{
            put(Constant.OTTO_KEY_HOUR, hour);
            put(Constant.OTTO_KEY_MINUTE, minute);
        }});
    }
}
