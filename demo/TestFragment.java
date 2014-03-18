package org.eski.list_splitter.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.eski.list_splitter.R;
import org.eski.list_splitter.expandable.ExpandableListSplitter;

public class TestFragment extends ExpandableListSplitter {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.main_menu, container, false);
        split(layout, new TestAdapter(getActivity()), savedInstanceState);
        return layout;
    }


}
