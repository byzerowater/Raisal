package me.fourground.litmus.data.model;

/**
 * Created by YoungSoo Kim on 2017-04-11.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class UpdateNickNameRequest {

    private String userNm;

    public UpdateNickNameRequest(String userNm) {
        this.userNm = userNm;
    }

    public String getUserNm() {
        return userNm;
    }
}
