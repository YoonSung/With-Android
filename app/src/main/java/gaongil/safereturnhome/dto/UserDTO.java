package gaongil.safereturnhome.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by yoon on 15. 7. 1..
 */
public class UserDTO {

    private String phoneNumber;

    private String nickname;

    private String imagePath;

    @JsonIgnore
    public boolean isValidPhoneNumber() {

        if (this.phoneNumber == null)
            return false;

        if (!this.phoneNumber.startsWith("010"))
            return false;

        if (this.phoneNumber.length() != 11)
            return false;

        return true;

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
