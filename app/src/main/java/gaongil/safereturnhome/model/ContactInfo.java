package gaongil.safereturnhome.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ContactInfo implements Parcelable {
      
    private String name;
    private String number;
    private boolean isSelected;
 
    @Override
	public String toString() {
		return "ContactInfo [name=" + name + ", number=" + number
				+ ", isSelected=" + isSelected + "]";
	}

	public ContactInfo(String phoneName, String phoneNumber) {
    	this.name = phoneName;
    	this.number = phoneNumber;
	}
    
    public String getName() {
        return name;
    }
 
    public void setName(String contactName) {
        this.name = contactName;
    }
 
    public String getNumber() {
        return number;
    }
 
    public void setNumber(String contactNumber) {
        this.number = contactNumber;
    }
 
    public boolean isSelected() {
        return isSelected;
    }
 
    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    /**
     * Parcel Code,
     * isSelected is not needed.
     */
    private ContactInfo(Parcel parcel) {
        this.name = parcel.readString();
        this.number = parcel.readString();
    }

	public static final Parcelable.Creator<ContactInfo> CREATOR =
            new Parcelable.Creator<ContactInfo>() {

        @Override
        public ContactInfo createFromParcel(Parcel source) {
            return new ContactInfo(source);
        }

        @Override
        public ContactInfo[] newArray(int size) {
            return new ContactInfo[size];
        }

    };
    
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(number);
	}
}