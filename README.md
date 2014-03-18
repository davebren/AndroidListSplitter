AndroidListSplitter
===================

A custom view library that enables ExpandableListViews to be split automatically between orientation changes.

[![ScreenShot](https://raw.github.com/eskimoapps/AndroidListSplitter/master/screenshots/youtube_image.png)](http://youtu.be/_YH4ca8sqWU)


![Screenshot](screenshots/combined.png)



You can convert between any number of list views by simply extending the ExpandableListSplitter and ExpandableListSplitterAdapter.


## Steps for ExpandableListSplitter (Reference the files in the demo folder):


1. Copy the folder org.eski.list_splitter into your source folder.

2. Create a fragment will inflate your list view(s), and extend ExpandableListSplitter.

3. Extend ExpandableListSplitterAdapter, and have your constructor call the super constructor with the total number of group rows, and the number of children in each group.
   1. DO NOT override the getGroupCount, getChildCount, getGroupId, or getChildId methods, these will be handled by the base class by the base class.
   2. When overriding getGroupView use getSplitGroupIndex in order to get the group position across all the split ExpandableListViews
   3. If you need to override onGroupExpanded and onGroupCollapsed, make sure you call the super methods, they retain the expansion state of your groups.
   4. Override the copy() method to return a new instance of your adapter.  This is used to split your adapter.

4. In OnCreateView, call split(), and pass in your inflated layout, and your adapter.

5. When you create a new instance of your fragment, before you add it call setResourceIds.
  1. Pass an int[] containing all of the layout's ExpandableListView ids, for each orientation layout (see TestActivity.java in demo folder).

6. Enjoy.








### LICENSE

Copyright 2014 David Breneisen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
