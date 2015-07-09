package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 7..
 */
public final class Type1 implements CloudMessageData {
    @Override
    public int intValue() {
        return 1;
    }

    public enum SubType {
        CHAT_MESSAGE(1),
        ADMIN_NOTIFICATION(2),
        UPDATE_FRIEND_STATUS(3),
        START_TO_RETURN_HOME(4);

        private final int code;

        SubType(int code) {
            this.code = code;
        }
    }

    private int code;
    private Object data;

    protected Type1(SubType subType, Object data) {
        this.code = subType.code;
        this.data = data;
    }
}
