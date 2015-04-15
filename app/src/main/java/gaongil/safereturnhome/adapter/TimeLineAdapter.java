package gaongil.safereturnhome.adapter;

import gaongil.safereturnhome.exception.InvalidMessageException;
import gaongil.safereturnhome.model.MessageData;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TimeLineAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<MessageData> mMessageList = null;

    public TimeLineAdapter(Context context, List<MessageData> messageDataList) {

        mContext = context;
        this.mMessageList = messageDataList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mMessageList.size();
    }

    @Override
    public MessageData getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        MessageData message = getItem(position);
        try {
            return message.getTimeLineView(mContext, mLayoutInflater);
        } catch (InvalidMessageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return view;
    }

    //notifyDataSetChanged();
}
