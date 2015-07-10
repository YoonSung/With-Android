package gaongil.safereturnhome.dto.cloud;

import gaongil.safereturnhome.dto.cloud.client.PlainText;

/**
 * Created by yoon on 15. 7. 10..
 */
public enum Strategy1 implements ClientStrategy {
    CHAT_MESSAGE(1, PlainText.class),
    ADMIN_NOTIFICATION(2, null),
    UPDATE_FRIEND_STATUS(3, null),
    START_TO_RETURN_HOME(4, null);

    private final int subCode;
    private final Class dto;

    Strategy1(int subCode, Class dto) {
        this.subCode = subCode;
        this.dto = dto;
    }

    @Override
    public int getStrategyCode() {
        return 1;
    }

    @Override
    public int getSubTypeCode() {
        return subCode;
    }

    @Override
    public Class getDTO() {
        return dto;
    }
}
