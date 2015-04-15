package gaongil.safereturnhome.scene;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.adapter.ContactsAdapter;
import gaongil.safereturnhome.model.ContactInfo;
import gaongil.safereturnhome.support.Constant;

@EActivity(R.layout.activity_contacts)
public class ContactsActivity extends Activity {

    private ContactsAdapter contactsAdapter;

    @ViewById(R.id.contact_edt_search)
    EditText mEdtSearch;

    @ViewById(R.id.contact_data_container)
    LinearLayout mLinearLayout;

    @ViewById(R.id.contact_progressbar_relativelayout)
    RelativeLayout mRelativeLayout;

    @ViewById(R.id.contact_recyclerview)
    RecyclerView recyclerView;


    @AfterViews
    void init() {
        addContactsInList();
    }

    @Click(R.id.contact_btn_cancel)
    void cancle() {
        this.finish();
    }

    @Click(R.id.contact_btn_done)
    void addGroup() {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constant.INTENT_GROUP_SELECTED_CONTACTLIST, contactsAdapter.getCheckedList());
        setResult(RESULT_OK, intent);

        this.finish();
    }

    @TextChange(R.id.contact_edt_search)
    void searchTextChange() {
        // When user changed the Text
        String text = mEdtSearch.getText().toString().toLowerCase(Locale.getDefault());
        contactsAdapter.filter(text);
    }

    private void addContactsInList() {
        showProgressBar();

        try {
            initRecyclerView();
            ArrayList<ContactInfo> orderedContactList = initOrderedContactList();

            contactsAdapter = new ContactsAdapter(ContactsActivity.this, orderedContactList);
            recyclerView.setAdapter(contactsAdapter);

        } catch (Exception e) {
            Log.e(Constant.TAG, e.getMessage());
            e.printStackTrace();

        }

        hideProgressbar();
    }

    private void initRecyclerView() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private ArrayList<ContactInfo> initOrderedContactList() {

        ArrayList<ContactInfo> photoList = new ArrayList<>();

        // get Contacts Cursor
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        try {
            photoList.clear();

        } catch (Exception e) {
            Log.e(Constant.TAG, e.getMessage());
            e.printStackTrace();

        }

        String phoneName = null;
        String phoneNumber = null;
        ContactInfo contactObject = null;

        while (phones.moveToNext()) {

            // get Data From Contacts Cursor
            phoneName = phones.getString(phones
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phoneNumber = phones.getString(phones
                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            contactObject = new ContactInfo(phoneName, phoneNumber);
            photoList.add(contactObject);

        }

        phones.close();


        Collections.sort(photoList,
                new Comparator<ContactInfo>() {

                    @Override
                    public int compare(ContactInfo one, ContactInfo another) {
                        return one.getName().compareTo(another.getName());
                    }
                });


        return photoList;
    }

    @UiThread
    void showProgressBar() {
        mRelativeLayout.setVisibility(View.VISIBLE);
        mEdtSearch.setVisibility(View.GONE);
    }

    @UiThread
    void hideProgressbar() {
        mRelativeLayout.setVisibility(View.GONE);
        mEdtSearch.setVisibility(View.VISIBLE);
    }
}
