package me.fourground.raisal.util;

import java.util.List;

/**
 * Created by YoungSoo Kim on 2017-03-27.
 * 4ground Ltd
 * byzerowater@gmail.com
 */
public class ListUtil {

    public static int getListCount(List list) {
        int count = 0;
        if (list != null) {
            count = list.size();
        }

        return count;
    }
}
