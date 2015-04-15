package gaongil.safereturnhome.model;

import java.util.ArrayList;

public class Group {
	/**
	 * Data Schema
	 * 
	 * memberList ArrayList<User>
	 * 
	 * name - String
	 * isAlarmAllowed - boolean
	 * isLocationAllowed - boolean
	 * isMovementSensorAllowed - boolean
	 * isSoundSensorAllowed - boolean
	 * 
	 */
	private ArrayList<User> memberList;
	private String name;
	private boolean isAlarmAllowed;
	private boolean isLocationAllowed;
	private boolean isMovementAllowed;
	private boolean isSoundSensorAllowed;
	
	public void setMemberList(ArrayList<User> memberList) {
		this.memberList = memberList;
	}
	
	public ArrayList<User> getMemberList() {
		return memberList;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAlarmAllowed() {
		return isAlarmAllowed;
	}
	public void setAlarmAllowed(boolean isAlarmAllowed) {
		this.isAlarmAllowed = isAlarmAllowed;
	}
	public boolean isLocationAllowed() {
		return isLocationAllowed;
	}
	public void setLocationAllowed(boolean isLocationAllowed) {
		this.isLocationAllowed = isLocationAllowed;
	}
	public boolean isMovementAllowed() {
		return isMovementAllowed;
	}
	public void setMovementAllowed(boolean isMovementAllowed) {
		this.isMovementAllowed = isMovementAllowed;
	}
	public boolean isSoundSensorAllowed() {
		return isSoundSensorAllowed;
	}
	public void setSoundSensorAllowed(boolean isSoundSensorAllowed) {
		this.isSoundSensorAllowed = isSoundSensorAllowed;
	}
}
