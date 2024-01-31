package tuna.ttt.core.message;

/**
 * Description TODO
 * DATA 2024-01-29
 *
 * @Author ttt
 */
public class RpcResponseMessage extends Message{

    /**
     * 返回值
     */
    private Object returnValue;
    /**
     * 异常值
     */
    private Exception exceptionValue;

    @Override
    public int getMessageType() {
        return RPC_MESSAGE_TYPE_RESPONSE;
    }


    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Exception getExceptionValue() {
        return exceptionValue;
    }

    public void setExceptionValue(Exception exceptionValue) {
        this.exceptionValue = exceptionValue;
    }
}
