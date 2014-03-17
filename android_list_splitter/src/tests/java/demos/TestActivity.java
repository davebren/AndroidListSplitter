package demos;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.eski.list_splitter.R;
import org.eski.list_splitter.expandable.ExpandableListSplitter;


public class TestActivity extends FragmentActivity {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.main_container);
        int[] two = new int[] {R.id.main_menu_navigation_list_1, R.id.main_menu_navigation_list_2};
        int[] three = new int[] {R.id.main_menu_navigation_list_1, R.id.main_menu_navigation_list_2, R.id.main_menu_navigation_list_3};
        TestFragment fragment = new TestFragment();
        fragment.setResourceIds(two, three);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment, "tag");
    }


}
