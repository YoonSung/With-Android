package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 10..
 */
public interface ClientStrategy {
    int getStrategyCode();
    int getSubTypeCode();
    Class getDTO();
}
