package me.fourground.raisal.util;

/**
 * Created by YoungSoo Kim on 2017-03-22.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class Util {
    // Build API authorization string from a given access token.
    public static String buildAuthorization(String accessToken) {
        String authorization = null;
        if (!StringUtil.isEmpty(accessToken)) {
            authorization = "Bearer " + accessToken;
        }

        return authorization;
    }
}
