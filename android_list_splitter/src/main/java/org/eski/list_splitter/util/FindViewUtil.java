package org.eski.list_splitter.util;

import android.view.View;

public class FindViewUtil {
    public static int getIndexContainingIds(View layout, int[][] ids) {
        int ret = -1;
        boolean containsIds = true;
        for (int i=0; i < ids.length; i++) {
            for (int j=0; j < ids[i].length; j++) {
                if (layout.findViewById(ids[i][j]) == null) containsIds = false; break;
            }
            if (containsIds) {
                ret = i;
                break;
            }
            containsIds = true;
        }
        return ret;
    }

}
