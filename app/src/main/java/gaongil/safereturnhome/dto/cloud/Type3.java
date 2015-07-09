package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 7..
 */
public final class Type3 implements CloudMessageData {
    @Override
    public int intValue() {
        return 3;
    }

    public enum SubType {}

    public Type3(SubType subType) {

    }
}
