package me.fourground.raisal.util;

import android.content.Context;

import me.fourground.raisal.R;
import me.fourground.raisal.common.Const;

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

    public static String getStoreType(Context context, String osType) {

        StringBuilder storeType = new StringBuilder();

        if (!StringUtil.isEmpty(osType)) {
            if (osType.contains(Const.STORE_TYPE_ADR)) {
                storeType.append(context.getString(R.string.text_device_andorid));
            }

            if (osType.contains(Const.STORE_TYPE_IOS)) {

                if (!StringUtil.isEmpty(storeType.toString())) {
                    storeType.append(" , ");
                }
                storeType.append(context.getString(R.string.text_device_ios));
            }
        }

        return storeType.toString();
    }
}
