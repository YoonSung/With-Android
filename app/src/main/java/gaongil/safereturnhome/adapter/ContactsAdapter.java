package gaongil.safereturnhome.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.provider.ContactsContract.Contacts;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.ContactInfo;

//TODO Performance Issue. Maintain Two ArrayList -> reQuery
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ListViewHolder> {

    Context mContext;
    LayoutInflater mInflater;
    private List<ContactInfo> mainDataList = null;
    private ArrayList<ContactInfo> arraylist;
    private ArrayList<ContactInfo> selectedList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ListViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView number;
        public CheckBox check;


        public ListViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.contact_txt_name);
            number = (TextView) view.findViewById(R.id.contact_txt_phonenum);
            check = (CheckBox) view.findViewById(R.id.contact_checkbox);
        }
    }

    public ContactsAdapter(Context context, List<ContactInfo> mainDataList) {

        mContext = context;
        this.mainDataList = mainDataList;
        mInflater = LayoutInflater.from(mContext);

        //For Filtering. this is total list placed. mainDataList is dynamically changed by filtering
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(mainDataList);

        this.selectedList = new ArrayList<>();
    }


    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_list_row, parent, false);

        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        ContactInfo contactInfo = arraylist.get(position);

        holder.name.setText(mainDataList.get(position).getName());
        holder.number.setText(mainDataList.get(position).getNumber());
        holder.check.setChecked(mainDataList.get(position).isSelected());

        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                ContactInfo contactInfo = mainDataList.get(position);
                contactInfo.setSelected(compoundButton.isChecked());
                ApplyToSelectedList(contactInfo);
            }
        });

    }

    public ArrayList<ContactInfo> getCheckedList() {
        return this.selectedList;
    }

    private void ApplyToSelectedList(ContactInfo contactInfo) {

        if (this.selectedList.contains(contactInfo)) {
            this.selectedList.remove(contactInfo);
        } else {
            this.selectedList.add(contactInfo);
        }

    }

    @Override
    public int getItemCount() {
        return mainDataList.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mainDataList.clear();
        if (charText.length() == 0) {
            mainDataList.addAll(arraylist);
        } else {
            for (ContactInfo wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mainDataList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
