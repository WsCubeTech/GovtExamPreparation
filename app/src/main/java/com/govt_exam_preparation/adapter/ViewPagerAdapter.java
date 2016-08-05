package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.model.Coaching_List_Model;
import com.govt_exam_preparation.others.My_URL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by wscubetech on 13/6/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    ArrayList<String> arrayImages = new ArrayList<>();
    Activity activity;
    LayoutInflater inflater;
    Coaching_List_Model coachingModel;

    public ViewPagerAdapter(Activity activity, ArrayList<String> arrayImages, Coaching_List_Model coachingModel) {
        this.activity = activity;
        this.arrayImages = arrayImages;
        this.coachingModel = coachingModel;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayImages.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = inflater.inflate(R.layout.row_image_coaching_center, container, false);
        initAndSet(v, position);
        container.addView(v);
        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void initAndSet(View v, int position) {
        ImageView imgCoachingCenter = (ImageView) v.findViewById(R.id.imgCoachingCenter);

        String photo = arrayImages.get(position).trim();

        if (photo.trim().length() < 1) {
            if (coachingModel.getImage().length() > 1) {
                Picasso.with(activity)
                        .load(My_URL.image_url + coachingModel.getImage())
                        .placeholder(R.drawable.ic_photo_gallery)
                        .into(imgCoachingCenter);
            }
        } else {
            imgCoachingCenter.setColorFilter(Color.TRANSPARENT);
            Picasso.with(activity)
                    .load(My_URL.image_url + photo)
                    .placeholder(R.drawable.ic_photo_gallery)
                    .into(imgCoachingCenter);
        }
    }
}
