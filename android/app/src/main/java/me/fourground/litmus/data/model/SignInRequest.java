package me.fourground.litmus.data.model;

/**
 * Created by YoungSoo Kim on 2017-03-15.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class SignInRequest {

    /**
     * 사용자채널고유아이디
     */
    private String userUid;
    /**
     * 이메일
     */
    private String email;
    /**
     * 채널사코드(G,F)
     */
    private String channelCode;

    public SignInRequest(String userUid, String email, String channelCode) {
        this.userUid = userUid;
        this.email = email;
        this.channelCode = channelCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}
