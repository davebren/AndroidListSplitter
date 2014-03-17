package dev_throwaways.expandable;


import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.Arrays;
import java.util.Random;

import util.ArrayComparison;

public class Indexing extends InstrumentationTestCase {
    private final static String log_tag = "testGroupIndexing";

    public void testGroupIndexing() {
        assertTrue(ArrayComparison.equals(getAllGroupIndexes(13,5),bruteGroupIndexes(13,5)));
        Random randGen = new Random();
        for (int i=0; i < 5000; i++) {
            int numGroups =  1 + randGen.nextInt(1000);
            int numChildren = 1+ randGen.nextInt(numGroups);
            assertTrue(ArrayComparison.equals(getAllGroupIndexes(numGroups,numChildren),bruteGroupIndexes(numGroups,numChildren)));
        }
    }
    public static int[][] getAllGroupIndexes(int numGroups, int numCopies) {
        int[][] ret = new int[numCopies][2];
        for (int i=0; i < ret.length; i++) {
            ret[i] = getGroupIndexes(i,numGroups,numCopies);
        }
        Log.d(log_tag, "candidate: " + Arrays.deepToString(ret));
        return ret;
    }
    public static int[] getGroupIndexes(int index, int numGroups, int numCopies) {
        int[] ret = new int[2];
        int remainder = numGroups % numCopies;
        int perGroupNoRemainder = numGroups / numCopies;
        ret[0] = index*(perGroupNoRemainder);
        if (index < remainder) ret[0] +=index ;
        else ret[0] += remainder;
        if (index < remainder) ret[1] = ret[0] + perGroupNoRemainder;
        else ret[1] = ret[0] + perGroupNoRemainder - 1;
        return ret;
    }
    public int[][] bruteGroupIndexes(int numGroups, int numCopies) {
        int[][] ret = new int[numCopies][2];
        int remainder = numGroups % numCopies;
        int perGroupNoRemainder = numGroups / numCopies;
        ret[0][0] = 0;
        ret[0][1] = perGroupNoRemainder - 1;
        if (remainder != 0) ret[0][1]++;
        for (int i=1; i < numCopies; i++) {
            ret[i][0] = ret[i-1][1] + 1;
            ret[i][1] = ret[i][0] + perGroupNoRemainder - 1;
            if (i < remainder) ret[i][1]++;
        }
        Log.d(log_tag, "brute: " + Arrays.deepToString(ret));
        return ret;
    }
}
