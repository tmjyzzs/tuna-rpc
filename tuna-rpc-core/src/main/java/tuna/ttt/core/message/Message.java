package tuna.ttt.core.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Description 根据消息的字节类型，获取对应的消息 class
 * DATA 2024-01-29
 *
 * @Author ttt
 */
public abstract class Message implements Serializable {

    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();


    public static final int  RPC_MESSAGE_TYPE_RESPONSE = 102;
    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
}
