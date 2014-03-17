package org.eski.list_splitter.util;

public class BooleanUtil {
    public static boolean[] falsify(boolean[] array) {
        for (int i=0; i < array.length; i++) array[i] = false;
        return array;
    }
    public static boolean[] merge(boolean[] array1, boolean[] array2) {
        boolean[] ret = new boolean[array1.length];
        for (int i=0; i < array1.length; i++)
            if (array1[i] || array2[i]) array1[i] = true;
        return ret;
    }
    public static boolean[] merge(boolean[]... arrays) {
        boolean[] ret = new boolean[arrays[0].length];
        for (int i=0; i < arrays.length; i++) {
            for (int j=0; j < arrays[i].length; j++) {
                if (arrays[i][j]) ret[j] = true;
            }
        }
        return ret;
    }
    public static int getFirstTrueIndex(boolean[] array) {
        for (int i = 0; i < array.length; i++)  if (array[i]) return i;
        return 0;
    }
}
