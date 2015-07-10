package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 10..
 */
public enum Strategy3 implements ClientStrategy {
    RECEIVE;

    private int subCode;

    @Override
    public int getStrategyCode() {
        return 3;
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
