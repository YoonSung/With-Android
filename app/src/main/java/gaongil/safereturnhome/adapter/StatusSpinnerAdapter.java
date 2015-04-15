package gaongil.safereturnhome.adapter;

import java.util.ArrayList;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.model.UserStatus;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StatusSpinnerAdapter extends ArrayAdapter<UserStatus>{

	private Context context;
	private int layoutResourceId;
	private ArrayList<UserStatus> statusList;

	public StatusSpinnerAdapter(Context context, int layoutResourceId, ArrayList<UserStatus> statusList) {
		super(context, layoutResourceId, statusList);
		
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.statusList = statusList;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater(); 
			view = inflater.inflate(layoutResourceId, parent, false);
		}
		
		UserStatus userStatus = statusList.get(position);
		view.setId(position);
		
		ImageView spinnerRowImage = (ImageView) view.findViewById(R.id.status_list_row_img);
		spinnerRowImage.setBackgroundResource(userStatus.getImageResourceId());
		
		TextView spinnerRowTextView = (TextView) view.findViewById(R.id.status_list_row_txt); 
		spinnerRowTextView.setText(userStatus.getStringValue(context));
		
		return view;
	}	
}
