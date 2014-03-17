package org.eski.list_splitter.expandable;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import org.eski.list_splitter.util.BundleUtil;
import org.eski.list_splitter.util.FindViewUtil;

public class ExpandableListSplitter extends Fragment {
    private static final String RESOURCE_IDS_KEY = "resource_ids";

    private int[][] resourceIds;
    private ExpandableSplitHelper expandableSplitHelper = new ExpandableSplitHelper();

    private ExpandableListView[] listViews;
    private ExpandableListSplitterAdapter[] adapters;

    private int currentSplitIndex = -1;

    public static ExpandableListSplitter newInstance(int[]... resourceIds) {
        ExpandableListSplitter fragment = new ExpandableListSplitter();
        fragment.setResourceIds(resourceIds);
        return fragment;
    }
    public void setResourceIds(int[]... resourceIds) {
        this.resourceIds = resourceIds;
        Bundle args = new Bundle();
        for (int i=0; i < resourceIds.length; i++) args.putIntArray(RESOURCE_IDS_KEY + i, resourceIds[i]);
        setArguments(args);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) resourceIds = BundleUtil.fillIntArraysFromKey(getArguments(), RESOURCE_IDS_KEY);
    }
    public void onSaveInstanceState(Bundle savedInstanceState) {
        if (listViews != null) expandableSplitHelper.onSaveInstanceState(savedInstanceState);
    }
    public void split(View layout, ExpandableListSplitterAdapter adapter, Bundle savedInstanceState) {
        if (layout == null || adapter == null) return;
        currentSplitIndex = FindViewUtil.getIndexContainingIds(layout, resourceIds);
        if (currentSplitIndex == -1 || resourceIds[currentSplitIndex].length == 0) return;
        listViews = new ExpandableListView[resourceIds[currentSplitIndex].length];
        adapters = new ExpandableListSplitterAdapter[resourceIds[currentSplitIndex].length];
        adapters[0] = adapter;
        for (int i=0; i < adapters.length; i++) {
            if (i != 0) adapters[i] = adapter.copy();
            adapters[i].setIndexAndNumCopies(i, resourceIds[currentSplitIndex].length);
        }
        for (int i=0; i < resourceIds[currentSplitIndex].length; i++) {
            listViews[i] = (ExpandableListView)layout.findViewById(resourceIds[currentSplitIndex][i]);
            listViews[i].setAdapter(adapters[i]);
        }
        expandableSplitHelper.onSplit(listViews, adapters, savedInstanceState);

    }


}
