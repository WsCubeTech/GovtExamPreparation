package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Coaching_List_Model;
import com.govt_exam_preparation.others.My_URL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by wscubetech on 9/3/16.
 */
public class Coaching_List_Adapter extends RecyclerView.Adapter<Coaching_List_Adapter.ViewHolder> {

    ArrayList<Coaching_List_Model> coaching_list;

    Activity act;

    public Coaching_List_Adapter(Activity act, ArrayList<Coaching_List_Model> coaching_list_) {
        this.act = act;
        this.coaching_list = coaching_list_;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_coaching_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Coaching_List_Model coaching_list_model = coaching_list.get(position);

        holder.coaching_center_name.setText(coaching_list_model.getName());
        holder.center_contact_number.setText(coaching_list_model.getContact_no());
        holder.address.setText(coaching_list_model.getAddress());

        /*ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(act));
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher) //this is the image that will be displayed if download fails
                .cacheInMemory()
                .cacheOnDisc()
                .build();

        imageLoader.displayImage(My_URL.image_url + coaching_list.get(position).getImage(),
                holder.coaching_center_image, options);*/

        String photo = coaching_list_model.getImage();
        if (photo.length() > 0) {
            Picasso.with(act)
                    .load(My_URL.image_url + coaching_list_model.getImage())
                    .placeholder(R.drawable.ic_photo_gallery)
                    .into(holder.coaching_center_image);
        }


    }

    @Override
    public int getItemCount() {
        return coaching_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView coaching_center_name, center_contact_number, address;
        public ImageView coaching_center_image;

        public ViewHolder(View v) {
            super(v);

            coaching_center_name = (TextView) v.findViewById(R.id.coaching_center_name);
            center_contact_number = (TextView) v.findViewById(R.id.center_contact_number);
            address = (TextView) v.findViewById(R.id.address);

            coaching_center_image = (ImageView) v.findViewById(R.id.coaching_center_image);
        }
    }

}



