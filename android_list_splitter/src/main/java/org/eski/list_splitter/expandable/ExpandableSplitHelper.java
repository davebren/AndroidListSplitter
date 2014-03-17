package org.eski.list_splitter.expandable;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ExpandableListView;

import org.eski.list_splitter.util.ArrayUtil;
import org.eski.list_splitter.util.BooleanUtil;
import org.eski.list_splitter.util.BundleUtil;

import java.util.LinkedList;
import java.util.List;

class ExpandableSplitHelper {

    ExpandableListView[] listViews;
    ExpandableListSplitterAdapter[] adapters;

    private final static String LIST_TOP_ROW_INDEX_KEY ="list_top_row_index";
    private final static String LIST_TOP_ROW_POSITION_KEY ="list_top_row_position";
    private final static String EXPANDED_GROUPS_KEY = "expanded_groups";
    private final static String LAST_TOUCHED_KEY = "last_touched_key";
    private final static String TOTAL_ROWS_EXPANDED_KEY = "total_rows_expanded_left";
    private final static String FIRST_GROUP_KEY = "first_group";

    private long[] lastTouched;

    public void onSplit(ExpandableListView[] listViews, ExpandableListSplitterAdapter[] adapters, Bundle onSavedInstanceState) {
        this.listViews = listViews;
        this.adapters = adapters;
        lastTouched = new long[listViews.length];
        for (int i=0; i < listViews.length; i++) listViews[i].setOnTouchListener(createTouchListener(i));
        restoreSplitScreen(onSavedInstanceState);
    }
    private void restoreSplitScreen(Bundle savedInstanceState) {
        if (savedInstanceState == null) return;
        boolean[] expandedGroups = savedInstanceState.getBooleanArray(EXPANDED_GROUPS_KEY);
        if (expandedGroups == null) return;
        long[] restoredLastTouch = savedInstanceState.getLongArray(LAST_TOUCHED_KEY);
        int currentSplit = 0;
        for (int i=0; i < expandedGroups.length; i++) {
            if (!expandedGroups[i]) continue;
            for (int j=currentSplit; j < adapters.length; j++) {
                if (i > adapters[j].lastGroupIndex) continue;
                listViews[j].expandGroup(i - adapters[j].firstGroupIndex);
                currentSplit = j;
                break;
            }
        }
        int[] topRowIndexes = BundleUtil.fillIntsFromKey(savedInstanceState, LIST_TOP_ROW_INDEX_KEY);
        int[] topRowPositions = BundleUtil.fillIntsFromKey(savedInstanceState, LIST_TOP_ROW_POSITION_KEY);
        int[] totalRowsExpanded = BundleUtil.fillIntsFromKey(savedInstanceState, TOTAL_ROWS_EXPANDED_KEY);
        int[] firstGroups = BundleUtil.fillIntsFromKey(savedInstanceState, FIRST_GROUP_KEY);
        int[] cumulativeRowCounts = ArrayUtil.convertToCumulative(totalRowsExpanded);
        int[] convertedTopRowIndexes = new int[cumulativeRowCounts.length];
        CandidateHelper candidateHelper = new CandidateHelper(topRowIndexes, topRowPositions, totalRowsExpanded,
                firstGroups, cumulativeRowCounts, convertedTopRowIndexes, adapters);
        for (int i=0; i < cumulativeRowCounts.length; i++) convertedTopRowIndexes[i] = cumulativeRowCounts[i] + topRowIndexes[i];
        for (int i=0; i < listViews.length; i++) {
            List<Integer> candidates = new LinkedList<Integer>();
            for (int j=0; j < convertedTopRowIndexes.length; j++) {
                if (candidateHelper.isCandidate(j, i)) candidates.add(j);
            }
            if (candidates.size() == 0) continue;
            int candidateWinner = candidateHelper.getTopCandidate(candidates, restoredLastTouch);
            int winnerRowIndex = candidateHelper.getRowInNewGroup(candidateWinner, i);
            listViews[i].setSelectionFromTop(winnerRowIndex, topRowPositions[candidateWinner]);
        }
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (listViews != null) saveSplitScreenInstanceState(savedInstanceState);
    }
    private void saveSplitScreenInstanceState(Bundle savedInstanceState) {
        boolean[][] allExpandedGroups = new boolean[adapters.length][];
        for (int i=0; i < allExpandedGroups.length; i++) allExpandedGroups[i] = adapters[i].getExpandedGroups();
        boolean[] expandedGroups = BooleanUtil.merge(allExpandedGroups);
        savedInstanceState.putBooleanArray(EXPANDED_GROUPS_KEY, expandedGroups);
        savedInstanceState.putLongArray(LAST_TOUCHED_KEY, lastTouched);
        for (int i=0; i < listViews.length; i++) {
            savedInstanceState.putInt(LIST_TOP_ROW_INDEX_KEY + i, listViews[i].getFirstVisiblePosition());
            View topRow = listViews[i].getChildAt(0);
            int topRowPosition = topRow == null ? 0 : topRow.getTop();
            savedInstanceState.putInt(LIST_TOP_ROW_POSITION_KEY + i, topRowPosition);
            savedInstanceState.putInt(TOTAL_ROWS_EXPANDED_KEY + i, adapters[i].getTotalRowsWithExpandedCount());
            savedInstanceState.putInt(FIRST_GROUP_KEY + i, adapters[i].firstGroupIndex);
        }
    }
    private ExpandableListView.OnTouchListener createTouchListener(final int index) {
        return new ExpandableListView.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                lastTouched[index] = System.currentTimeMillis();
                return false;
            }
        };
    }

}
