package gaongil.safereturnhome.adapter;

import gaongil.safereturnhome.exception.InvalidMessageException;
import gaongil.safereturnhome.model.MessageData;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ChatAdapter extends BaseAdapter {

	private final String TAG = ChatAdapter.class.getName();
	
	Context mContext;
	List<MessageData> mMessageList;
	LayoutInflater mLayoutInflater;
	
	public ChatAdapter(Context context, List<MessageData> messageList) {
		this.mContext = context;
		this.mMessageList = messageList;
		mLayoutInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return mMessageList.size();
	}

	@Override
	public MessageData getItem(int index) {
		return mMessageList.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		MessageData message = getItem(position);
		
		// Type check and mapping proper XML Layout
		// Input data to layout component
		try {
			return message.getChatView(mContext, mLayoutInflater);
			
		} catch (InvalidMessageException e) {
			Log.e(TAG, e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
}
