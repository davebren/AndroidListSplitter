package demos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eski.list_splitter.expandable.ExpandableListSplitterAdapter;
import org.eski.list_splitter.R;

public class TestAdapter extends ExpandableListSplitterAdapter {

    Activity activity;
    LayoutInflater inflater;

    public TestAdapter(Activity activity) {
        super(12, new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12});
        this.inflater = activity.getLayoutInflater();
        this.activity = activity;
    }

    public ExpandableListSplitterAdapter copy() {
        return new TestAdapter(activity);
    }
    public Object getGroup(int groupPosition) {
        return null;
    }
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }
    public boolean hasStableIds() {
        return false;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = inflater.inflate(R.layout.main_menu_group_row, null);
        TextView name = (TextView)convertView.findViewById(R.id.main_menu_group_name);
        name.setText("group " + getSplitGroupPosition(groupPosition));
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) convertView = inflater.inflate(R.layout.main_menu_group_row, null);
        TextView name = (TextView)convertView.findViewById(R.id.main_menu_group_name);
        name.setText("child " + childPosition);
        return convertView;
    }
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
