package com.govt_exam_preparation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Details_Model;

import java.util.ArrayList;


/**
 * Created by wscubetech on 12/3/16.
 */
public class Job_Detail_Adapter extends ArrayAdapter<Details_Model> {

    private Context context;
    private ArrayList<Details_Model> exam_details = new ArrayList<Details_Model>();
    int Layout = 0;



    public Job_Detail_Adapter(Context context_, int resource,
                                ArrayList<Details_Model> exam_details_) {
        super(context_, resource, exam_details_);
        // TODO Auto-generated constructor stub
        context = context_;
        Layout = resource;
        exam_details = exam_details_;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderFour viewHolderFour = null;
        if (convertView == null) {
            viewHolderFour = new ViewHolderFour();

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(Layout, null);

            viewHolderFour.icon = (ImageView)convertView.findViewById(R.id.icon);
            viewHolderFour.title = (TextView)convertView.findViewById(R.id.title);
            viewHolderFour.description = (TextView)convertView.findViewById(R.id.description);

            convertView.setTag(viewHolderFour);

        } else {
            viewHolderFour = (ViewHolderFour) convertView.getTag();
        }

        viewHolderFour.icon.setImageResource(exam_details.get(position).getIcon());
        viewHolderFour.title.setText(exam_details.get(position).getTitle());
        viewHolderFour.description.setText(exam_details.get(position).getDescription());

        return convertView;
    }


    class ViewHolderFour {
        ImageView icon;
        TextView title,description;

    }
}
