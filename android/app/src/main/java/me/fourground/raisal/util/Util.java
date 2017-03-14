package me.fourground.raisal.util;

/**
 * Created by YoungSoo Kim on 2016-07-29.
 * company Ltd
 * youngsoo.kim@yap.net
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
