package gaongil.safereturnhome.scene;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.WithApp;
import gaongil.safereturnhome.model.ContactInfo;
import gaongil.safereturnhome.support.Constant;

@EActivity(R.layout.activity_group)
public class GroupActivity extends Activity {

    @App
    WithApp app;

    @ViewById(R.id.group_edt_display_groupmember)
    EditText edtDisplayMemberList;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (requestCode == Constant.REQUEST_CODE_GROUPTOCONTACT) {
			if (resultCode == RESULT_OK) {
                ArrayList<ContactInfo> selectedContactList = data.getParcelableArrayListExtra(Constant.INTENT_GROUP_SELECTED_CONTACTLIST);
				
				for (ContactInfo contactInfo : selectedContactList) {
					edtDisplayMemberList.append(contactInfo.getName() + Constant.TEXT_SEPERATOR_COMMA);
				}
				
				//TODO SaveData from Memory.
			}
		}
	}

    @Click(R.id.group_btn_addmember)
    void moveContactActivity() {
        startActivityForResult(new Intent(GroupActivity.this, ContactsActivity_.class), Constant.REQUEST_CODE_GROUPTOCONTACT);
    }

    @Click(R.id.group_btn_create)
    void createGroup() {
        //app.NETWORK.createGroup();
    }

    @Click(R.id.group_btn_cancle)
    void cancle() {
        this.finish();
    }

}
