package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 10..
 */
public enum Strategy2 implements ClientStrategy {
    BULK_MESSAGE(1, ""),
    UPDATE_FRIEND_PROFILE(2, ""),;

    private int subCode;
    private String url;

    Strategy2(int subCode, String url) {
        this.subCode = subCode;
        this.url = url;
    }

    @Override
    public int getStrategyCode() {
        return 2;
    }

    @Override
    public int getSubTypeCode() {
        return this.subCode;
    }

    @Override
    public Class getDTO() {
        return null;
    }
}
