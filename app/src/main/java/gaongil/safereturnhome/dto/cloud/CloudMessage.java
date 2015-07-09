package gaongil.safereturnhome.dto.cloud;

/**
 * Created by yoon on 15. 7. 7..
 */
public class CloudMessage {

    private int processType;
    private CloudMessageData data;

    private CloudMessage(CloudMessageData type){
        this.processType = type.intValue();
        this.data = type;
    }

    public static CloudMessage createType1(Type1.SubType subType, Object data) {
        return new CloudMessage(new Type1(subType, data));
    }

    // TODO
    public static CloudMessage createType2(Type2.SubType subType) {
        return new CloudMessage(new Type2(subType));
    }

    // TODO
    public static CloudMessage createType3(Type3.SubType subType) {
        return new CloudMessage(new Type3(subType));
    }

    // TODO
    public static CloudMessage createType4(Type4.SubType subType, Object data) {
        return new CloudMessage(new Type4(subType, data));
    }
}
