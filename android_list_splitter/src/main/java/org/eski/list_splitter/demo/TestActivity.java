package org.eski.list_splitter.demo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import org.eski.list_splitter.R;


public class TestActivity extends Activity {

    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.main_container);
        int[] two = new int[] {R.id.main_menu_navigation_list_1, R.id.main_menu_navigation_list_2};
        int[] three = new int[] {R.id.main_menu_navigation_list_1, R.id.main_menu_navigation_list_2, R.id.main_menu_navigation_list_3};
        TestFragment fragment = new TestFragment();
        fragment.setResourceIds(two, three);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.main_container, fragment, "tag");
        transaction.commit();
    }


}
