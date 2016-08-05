package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Notification_Model;
import com.govt_exam_preparation.others.My_URL;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by wscubetech on 19/4/16.
 */
public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.ViewHolder> {

    ArrayList<Notification_Model> notification_list;

    Activity act;

    public Notification_Adapter(Activity act, ArrayList<Notification_Model> notification_list_) {
        this.act = act;
        this.notification_list = notification_list_;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_notification, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Notification_Model model = notification_list.get(position);

        holder.notification_title.setText(model.getTitle());
        holder.notification_date.setText(model.getDate());
//       holder.notification_description.setText(notification_list.get(position).getDescription());

        Picasso.with(act)
                .load(My_URL.image_url + model.getImage())
                .placeholder(R.drawable.ic_photo_gallery)
                .resize(128, 128)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.notification_image);

    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView notification_title, notification_date, notification_description;
        ImageView notification_image;


        public ViewHolder(View v) {
            super(v);

            notification_title = (TextView) v.findViewById(R.id.notification_title);
            notification_date = (TextView) v.findViewById(R.id.notification_date);
            //           notification_description=(TextView)v.findViewById(R.id.notification_description);
            notification_image = (ImageView) v.findViewById(R.id.notification_image);

        }
    }

}
