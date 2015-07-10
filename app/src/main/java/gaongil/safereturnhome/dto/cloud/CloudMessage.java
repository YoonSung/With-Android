package gaongil.safereturnhome.dto.cloud;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gaongil.safereturnhome.dto.cloud.client.ClientDTO;

/**
 * Created by yoon on 15. 7. 7..
 */
public class CloudMessage {

    @JsonProperty("strategy")
    private int strategyCode;

    @JsonProperty("type")
    private int subTypeCode;

    @JsonProperty("data")
    private Object data;

    private CloudMessage() {
    }

    public CloudMessage(ClientStrategy strategyCode, ClientDTO data) {
        if (strategyCode == null || data == null)
            throw new IllegalArgumentException();

        this.strategyCode = strategyCode.getStrategyCode();
        this.subTypeCode = strategyCode.getSubTypeCode();

        this.data = data;
    }

    public int getStrategyCode() {
        return strategyCode;
    }

    public int getSubTypeCode() {
        return subTypeCode;
    }

    public Object getData() {
        return data;
    }

    @JsonIgnore
    public ClientStrategy getStrategy() {
        ClientStrategy[] strategys = null;
        switch (this.strategyCode) {
            case 1:
                strategys = Strategy1.values();
                break;
            case 2:
                strategys = Strategy2.values();
                break;
            case 3:
                strategys = Strategy3.values();
                break;
            case 4:
                strategys = Strategy4.values();
                break;
        }

        for (ClientStrategy strategy : strategys) {
            if (strategy.getSubTypeCode() == subTypeCode)
                return strategy;
        }

        return null;
    }

    @Override
    public String toString() {
        return "CloudMessage{" +
                "data=" + data +
                ", subTypeCode=" + subTypeCode +
                ", strategyCode=" + strategyCode +
                '}';
    }
}
