package com.govt_exam_preparation.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Subjects_Sub_Category;
import com.govt_exam_preparation.model.Teachers_Sub_Category;

import java.util.ArrayList;

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    ArrayList<Subjects_Sub_Category> subject_list ;
    ArrayList<Teachers_Sub_Category> teacher_list ;

    public SecondLevelAdapter(Context mContext, ArrayList<Subjects_Sub_Category> subject_list_,
                              ArrayList<Teachers_Sub_Category> teacher_list_) {
        this.mContext = mContext;
        this.subject_list = subject_list_;
        this.teacher_list = teacher_list_;
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
        final String childText = subject_list.get(groupPosition).getTeacher_list().get(childPosition).getCoaching_teacher_name();

  //      if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);
 //       }
        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        TextView qualification = (TextView) convertView
                .findViewById(R.id.qualification);

        txtListChild.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        txtListChild.setText(childText);
        qualification.setText(subject_list.get(groupPosition).getTeacher_list().get(childPosition).getCoaching_teacher_qualification());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return subject_list.get(groupPosition).getTeacher_list().size();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return subject_list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return subject_list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = subject_list.get(groupPosition).getCoaching_cources_sub_name();
 //       if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group_second, parent, false);
  //      }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle);
        lblListHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);

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
