package org.eski.list_splitter.expandable;

import android.widget.BaseExpandableListAdapter;

public abstract class ExpandableListSplitterAdapter extends BaseExpandableListAdapter {

    boolean[] expanded_groups;
    int numCopies = 1;
    int index = 0;

    int numGroups = 0;
    int[] numChildrenPerGroup;
    int firstGroupIndex = 0;
    int lastGroupIndex = 0;
    int groupCount = 0;

    public ExpandableListSplitterAdapter(int numGroups, int[] numChildrenPerGroup) {
        this.numGroups = numGroups;
        this.numChildrenPerGroup = numChildrenPerGroup;
        expanded_groups = new boolean[numGroups];
    }

    /**
     *  Implement this method and have it create a new instance of your adapter.
     *  @return copy
     */
    public abstract ExpandableListSplitterAdapter copy();

    void setIndexAndNumCopies(int index, int numCopies) {
        this.index = index;
        this.numCopies = numCopies;
        int remainder = numGroups % numCopies;
        int perGroupNoRemainder = numGroups / numCopies;
        firstGroupIndex = index*(perGroupNoRemainder);
        if (index < remainder) firstGroupIndex +=index ;
        else firstGroupIndex += remainder;
        if (index < remainder) lastGroupIndex = firstGroupIndex + perGroupNoRemainder;
        else lastGroupIndex = firstGroupIndex + perGroupNoRemainder - 1;
        groupCount = lastGroupIndex - firstGroupIndex + 1;
    }
    public int getGroupCount() {
        return groupCount;
    }
    int getTotalRowsWithExpandedCount() {
        int count = getGroupCount();
        for (int i=0; i < getGroupCount(); i++) {
            if (expanded_groups[getSplitGroupPosition(i)]) count += getChildrenCount(i);
        }
        return count;
    }
    public int getChildrenCount(int groupPosition) {
        return numChildrenPerGroup[groupPosition];
    }
    public void onGroupExpanded(int groupPosition) {
        expanded_groups[getSplitGroupPosition(groupPosition)] = true;
    }
    public void onGroupCollapsed(int groupPosition) {
        expanded_groups[getSplitGroupPosition(groupPosition)] = false;
    }
    boolean[] getExpandedGroups() {
        return expanded_groups;
    }
    public long getGroupId(int groupPosition) {
        return getSplitGroupPosition(groupPosition);
    }
    public long getChildId(int groupPosition, int childPosition) {
        int childCount = 0;
        for (int i=0; i < groupPosition; i++) {
            childCount += getChildrenCount(groupPosition);
        }
        return childCount + childPosition;
    }
    public int getSplitGroupPosition(int groupPosition) {
        return groupPosition + firstGroupIndex;
    }
}
