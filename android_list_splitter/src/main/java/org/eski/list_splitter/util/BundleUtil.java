package org.eski.list_splitter.util;

import android.os.Bundle;

public class BundleUtil {
    public static int countIndexes(Bundle bundle, String baseKey) {
        int count = 0;
        while (bundle.containsKey(baseKey + count)) count++;
        return count;
    }
    public static int[] fillIntsFromKey(Bundle bundle, String baseKey) {
        int count = countIndexes(bundle, baseKey);
        int[] array = new int[count];
        for (int i=0; i < array.length; i++) array[i] = bundle.getInt(baseKey + i);
        return array;
    }
    public static int[][] fillIntArraysFromKey(Bundle bundle, String baseKey) {
        int count = countIndexes(bundle, baseKey);
        int[][] array = new int[count][];
        for (int i=0; i < array.length; i++) array[i] = bundle.getIntArray(baseKey + i);
        return array;
    }
}
