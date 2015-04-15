package gaongil.safereturnhome.model;

import gaongil.safereturnhome.R;
import gaongil.safereturnhome.exception.InvalidMessageException;
import gaongil.safereturnhome.support.Constant;
import gaongil.safereturnhome.support.ImageUtil;
import gaongil.safereturnhome.support.RoundedAvatarDrawable;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class MessageData {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int writerId;

    @DatabaseField
    private int groupId;

    @DatabaseField
    private String content;

    @DatabaseField(dataType = DataType.DATE)
    private Date date;

    @DatabaseField(dataType = DataType.ENUM_INTEGER)
    private MessageType type;

    @DatabaseField
    private boolean isReceived;

    private SimpleDateFormat writtenTimeFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

    //It's for ORMLite
    public MessageData() {
    }

    public MessageData(int groupId, int writerId, String content, Date date,
                       MessageType type, boolean isReceived) {
        this.groupId = groupId;
        this.writerId = writerId;
        this.content = content;
        this.date = date;
        this.type = type;
        this.isReceived = isReceived;
    }

    //getImageResourceId
    public View getChatView(Context context, LayoutInflater layoutInflater) throws InvalidMessageException {
        if (this.type == null)
            throw new InvalidMessageException();

        int layoutId = isReceived ? R.layout.chat_message_rcv : R.layout.chat_message_sent;
        View view = layoutInflater.inflate(layoutId, null);

        String imageName = isReceived ? Constant.PROFILE_IMAGE_NAME : writerId + "";
        //TODO Change Above code

        System.out.println("imageName : " + imageName);
        Drawable profileDrawable = ImageUtil.getProfileImage(context, imageName);
        System.out.println("profileDrawable : " + profileDrawable);
        BitmapDrawable bImage = (BitmapDrawable) profileDrawable;
        System.out.println("bImage : " + bImage);
        RoundedAvatarDrawable rondedAvatarImg = new RoundedAvatarDrawable(bImage.getBitmap());
        //Test Image Load End

        ImageView profile = (ImageView) view.findViewById(R.id.message_img_profile);
        profile.setImageDrawable(new RoundedAvatarDrawable(rondedAvatarImg.getBitmap()));

        // message set
        TextView content = (TextView) view.findViewById(R.id.message_content);
        content.setText(this.content);
        content.setTextColor(context.getResources().getColor(this.type.getColorResourceId()));
        content.setTypeface(null, Typeface.BOLD);

        // writtenTime set
        TextView receivedTime = (TextView) view.findViewById(R.id.message_time);
        receivedTime.setText(writtenTimeFormat.format(this.date));

        // bubbleImage set
        LinearLayout messageBubble = (LinearLayout) view.findViewById(R.id.message_linearlayout);
        messageBubble.setBackgroundResource(this.type.getBubbleResourceId(this.isReceived));

        return view;
    }

    public View getTimeLineView(Context context, LayoutInflater layoutInflater) throws InvalidMessageException {
        if (this.type == null)
            throw new InvalidMessageException();
        View view = layoutInflater.inflate(R.layout.timeline_list_row, null);

        //Test Image Load Start
        //TODO connect MessageData Resource
        //TODO getRoundedImage Method is to be static
        BitmapDrawable bImage = (BitmapDrawable) context.getResources().getDrawable(R.drawable.test_chat_user1);
        RoundedAvatarDrawable rondedAvatarImg = new RoundedAvatarDrawable(bImage.getBitmap());
        //Test Image Load End

        ImageView profile = (ImageView) view.findViewById(R.id.drawer_main_right_img_profile);
        profile.setImageDrawable(new RoundedAvatarDrawable(rondedAvatarImg.getBitmap()));

        // message set
        TextView message = (TextView) view.findViewById(R.id.drawer_main_right_txt_message);
        message.setText(this.content);
        message.setTextColor(context.getResources().getColor(this.type.getColorResourceId()));

        // writtenTime set
        TextView receivedTime = (TextView) view.findViewById(R.id.drawer_main_right_txt_receivedtime);
        receivedTime.setText(writtenTimeFormat.format(this.date));

        return view;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getWriterId() {
        return writerId;
    }

    public Date getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public boolean isReceived() {
        return isReceived;
    }
}
