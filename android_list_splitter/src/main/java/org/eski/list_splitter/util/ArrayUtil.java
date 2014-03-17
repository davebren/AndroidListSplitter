package org.eski.list_splitter.util;

public class ArrayUtil {
    public static int[] convertToCumulative(int[] array) {
        int[] ret = new int[array.length];
        ret[0] = 0;
        for (int i=1; i < array.length; i++) ret[i] = ret[i-1] + array[i];
        return ret;
    }

}

