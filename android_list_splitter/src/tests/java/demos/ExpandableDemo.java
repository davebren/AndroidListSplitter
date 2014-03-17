package demos;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;


public class ExpandableDemo extends ActivityInstrumentationTestCase2<TestActivity> {
    private TestActivity activity;

    public ExpandableDemo(Class<TestActivity> activityClass) {
        super(activityClass);
    }
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }
    public void testStartDemo() throws Exception {
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(TestActivity.class.getName(), null, false);
    }

}
