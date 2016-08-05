package com.govt_exam_preparation.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Center_Detail_Model;
import com.govt_exam_preparation.model.Subjects_Sub_Category;
import com.govt_exam_preparation.model.Teachers_Sub_Category;

import java.util.ArrayList;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
   /* private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;*/

    ArrayList<Center_Detail_Model> center_list ;
    ArrayList<Subjects_Sub_Category> subject_list ;
    ArrayList<Teachers_Sub_Category> teacher_list ;

    public ExpandableListAdapter(Context context, ArrayList<Center_Detail_Model> center_list_,
                                 ArrayList<Subjects_Sub_Category> subject_list_) {
        this._context = context;
        this.center_list = center_list_;
        this.subject_list = subject_list_;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return childPosititon;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.row_second, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return center_list.get(groupPosition).getSub_cat().size();
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
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}