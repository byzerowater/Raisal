package me.fourground.litmus.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-29.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class RegisterData {

    /**
     * appId : string
     * message : string
     * success : true
     */

    private String appId;
    private String message;
    private boolean success;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "RegisterData{" +
                "appId='" + appId + '\'' +
                ", message='" + message + '\'' +
                ", success=" + success +
                '}';
    }
}
