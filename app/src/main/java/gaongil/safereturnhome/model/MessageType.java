package gaongil.safereturnhome.model;

import gaongil.safereturnhome.R;

public enum MessageType {
	
	NORMAL(1, R.drawable.chat_gray_bubble, R.drawable.chat_blue_bubble, R.color.black),
	ANNOUNCE(2, R.drawable.chat_gray_bubble, R.drawable.chat_blue_bubble, R.color.main_color_gray),
	WARN(3, R.drawable.chat_gray_bubble, R.drawable.chat_blue_bubble, R.color.yellow),
	URGENT(4, R.drawable.chat_gray_bubble, R.drawable.chat_blue_bubble, R.color.main_color_red);
	
	private final int type;
	private final int receiveResourceId;
	private final int sendResource;
	private final int colorResourceId;
	
	MessageType(int type, int sendResource, int receiveResource, int colorResourceId) {
		this.type = type;
		this.receiveResourceId = receiveResource;
		this.sendResource = sendResource;
		this.colorResourceId = colorResourceId;
	}
	
	int getBubbleResourceId(boolean isReceiveMessage) {
		return isReceiveMessage == true ? receiveResourceId : sendResource;
	}
	
	int getColorResourceId() {
		return colorResourceId;
	}
	
	public int intValue() {
		return this.type;
	}
}
