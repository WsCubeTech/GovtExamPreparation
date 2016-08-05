package com.govt_exam_preparation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.govt_exam_preparation.CustomExpListView;
import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Center_Detail_Model;
import com.govt_exam_preparation.model.Subjects_Sub_Category;
import com.govt_exam_preparation.model.Teachers_Sub_Category;

import java.util.ArrayList;


public class ParentLevelAdapter extends BaseExpandableListAdapter {
    private final Context mContext;

    ArrayList<Center_Detail_Model> center_list ;
    ArrayList<Subjects_Sub_Category> subject_list ;
    ArrayList<Teachers_Sub_Category> teacher_list ;

   /* private final List<String> mListDataHeader;
    private final Map<String, List<String>> mListData_SecondLevel_Map;
    private final Map<String, List<String>> mListData_ThirdLevel_Map;*/

    public ParentLevelAdapter(Context mContext, ArrayList<Center_Detail_Model> center_list_,
                              ArrayList<Subjects_Sub_Category> subject_list_,
                              ArrayList<Teachers_Sub_Category> teacher_list_) {
        this.mContext = mContext;
        center_list = center_list_;
        subject_list = subject_list_;
        teacher_list = teacher_list_;

  /*      // SECOND LEVEL
        String[] mItemHeaders;
        mListData_SecondLevel_Map = new HashMap<>();
        int parentCount = mListDataHeader.size();
        for (int i = 0; i < parentCount; i++) {
            String content = mListDataHeader.get(i);
            switch (content) {
                case "Level 1.1":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child);
                    break;
                case "Level 1.2":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_one_two_child);
                    break;
                default:
                    mItemHeaders = mContext.getResources().getStringArray(R.array.items_array_expandable_level_two);
            }
            mListData_SecondLevel_Map.put(mListDataHeader.get(i), Arrays.asList(mItemHeaders));
        }*/
      /*  // THIRD LEVEL
        String[] mItemChildOfChild;
        List<String> listChild;
        mListData_ThirdLevel_Map = new HashMap<>();
        for (Object o : mListData_SecondLevel_Map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object object = entry.getValue();
            if (object instanceof List) {
                List<String> stringList = new ArrayList<>();
                Collections.addAll(stringList, (String[]) ((List) object).toArray());
                for (int i = 0; i < stringList.size(); i++) {
                    mItemChildOfChild = mContext.getResources().getStringArray(R.array.items_array_expandable_level_three);
                    listChild = Arrays.asList(mItemChildOfChild);
                    mListData_ThirdLevel_Map.put(stringList.get(i), listChild);
                }
            }subject_list.size()
        }*/
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final CustomExpListView secondLevelExpListView = new CustomExpListView(this.mContext);
      //  String parentNode = (String) getGroup(groupPosition);
        secondLevelExpListView.setAdapter(new SecondLevelAdapter(this.mContext, center_list.get(groupPosition).getSub_cat(),
                  center_list.get(groupPosition).getSub_cat().get(childPosition).getTeacher_list()));
        secondLevelExpListView.setGroupIndicator(null);
//        secondLevelExpListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousGroup = -1;
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (groupPosition != previousGroup)
//                    secondLevelExpListView.collapseGroup(previousGroup);
//                previousGroup = groupPosition;
//            }
//        });
        return secondLevelExpListView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int count = center_list.get(groupPosition).getSub_cat().size();

        Log.d("child count",count+"");

        if(count>0)
        {
            return 1;
        }
        else {
            return count;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return center_list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = center_list.get(groupPosition).getCoaching_cources_name();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group, parent, false);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTextColor(mContext.getResources().getColor(R.color.colorRightAns));
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
