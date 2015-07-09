package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 7..
 */
public final class Type2 implements CloudMessageData {
    @Override
    public int intValue() {
        return 2;
    }

    enum SubType {
        BULK_MESSAGE(1, ""),
        UPDATE_FRIEND_PROFILE(2, ""),;

        SubType(int code, String url) {

        }
    }



    public Type2(SubType subType) {

    }
}
