package org.eski.list_splitter.expandable;

import java.util.List;

class CandidateHelper {
    int[] topRowIndexes;
    int[] topRowPositions;
    int[] totalRowsExpanded;
    int[] firstGroups;
    int[] cumulativeRowCounts;
    int[] convertedTopRowIndexes;

    int[] newCumulativeRowCounts;

    CandidateHelper(int[] topRowIndexes, int[] topRowPositions,
                    int[] totalRowsExpanded, int[] firstGroups,
                    int[] cumulativeRowCounts, int[] convertedTopRowIndexes,
                    ExpandableListSplitterAdapter[] adapters)
    {
        this.topRowIndexes = topRowIndexes;
        this.totalRowsExpanded = totalRowsExpanded;
        this.firstGroups = firstGroups;
        this.cumulativeRowCounts = cumulativeRowCounts;
        this.convertedTopRowIndexes = convertedTopRowIndexes;
        newCumulativeRowCounts = getCumulativeRowCounts(adapters);
    }
    private int[] getCumulativeRowCounts(ExpandableListSplitterAdapter[] adapters) {
        int[] ret = new int[adapters.length];
        ret[0] = 0;
        for (int i = 1; i < adapters.length; i++ ) {
            ret[i] = adapters[i-1].getTotalRowsWithExpandedCount() + ret[i-1];
        }
        return ret;
    }
    public boolean isCandidate(int fromIndex, int toIndex) {
        int fromRowIndex = topRowIndexes[fromIndex] + cumulativeRowCounts[fromIndex];
        if (toIndex == 0 && newCumulativeRowCounts.length < 2) return true;
        if (toIndex == 0 && fromRowIndex <= newCumulativeRowCounts[toIndex+1]) return true;
        if (toIndex == newCumulativeRowCounts.length -1
                && fromRowIndex >= newCumulativeRowCounts[toIndex]) return true;
        if (fromRowIndex >= newCumulativeRowCounts[toIndex]
                && fromRowIndex <= newCumulativeRowCounts[toIndex + 1]) return true;
        return false;
    }
    public int getTopCandidate(List<Integer> candidates, long[] lastTouch) {
        long mostRecent = -1;
        int mostRecentCandidate = -1;
        for (Integer candidate : candidates) {
            if (lastTouch[candidate] > mostRecent) {
                mostRecent = lastTouch[candidate];
                mostRecentCandidate = candidate;
            }
        }
        return mostRecentCandidate;
    }
    public int getRowInNewGroup(int fromIndex, int toIndex) {
        if (toIndex == 0) return cumulativeRowCounts[fromIndex] + topRowIndexes[fromIndex];
        return topRowIndexes[fromIndex] + cumulativeRowCounts[fromIndex] - newCumulativeRowCounts[toIndex];
    }
}
