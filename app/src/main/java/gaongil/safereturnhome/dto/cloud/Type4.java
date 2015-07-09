package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 7..
 */
public final class Type4 implements CloudMessageData {
    @Override
    public int intValue() {
        return 4;
    }

    public enum SubType {
        GROUP_INVITATION(1);

        private final int code;

        SubType(int code) {
            this.code = code;
        }
    }

    private int code;
    private Object data;

    public Type4(SubType subType, Object data) {
        this.code = subType.code;
        this.data = data;
    }
}
