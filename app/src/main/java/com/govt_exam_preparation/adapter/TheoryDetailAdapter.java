package com.govt_exam_preparation.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.govt_exam_preparation.R;
import com.govt_exam_preparation.dialogs.MyDialog;
import com.govt_exam_preparation.model.TheoryDetailModel;
import com.govt_exam_preparation.others.My_URL;
import com.govt_exam_preparation.others.TouchImageView;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by wscubetech on 26/6/16.
 */
public class TheoryDetailAdapter extends RecyclerView.Adapter<TheoryDetailAdapter.ViewHolder> {

    Activity activity;
    ArrayList<TheoryDetailModel> arrayTheoryDetailModel = new ArrayList<>();

    public TheoryDetailAdapter(Activity activity, ArrayList<TheoryDetailModel> arrayTheoryDetailModel) {
        this.activity = activity;
        this.arrayTheoryDetailModel = arrayTheoryDetailModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.row_theory_detail, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TheoryDetailModel detailModel = arrayTheoryDetailModel.get(position);
        holder.txtTitle.setText(detailModel.getTheoryTitle());

        WebSettings settings = holder.webView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        holder.webView.loadData(detailModel.getTheoryDesc(), "text/html; charset=utf-8", null);

        String image = detailModel.getTheoryImage();
        if (image.length() > 1) {
            holder.imgTheory.setVisibility(View.VISIBLE);
            Picasso.with(activity)
                    .load(My_URL.image_url + detailModel.getTheoryImage())
                    .placeholder(R.drawable.ic_photo_gallery)
                    .into(holder.imgTheory);

            holder.imgTheory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showEnlargedImageDialog(detailModel.getTheoryImage());
                }
            });
        } else {
            holder.imgTheory.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayTheoryDetailModel.size();
    }

    public void showEnlargedImageDialog(String image) {
        Dialog dialog = new MyDialog(activity).getMyDialog(R.layout.dialog_enlarged_image);
        dialog.setCancelable(true);
        TouchImageView imageView = (TouchImageView) dialog.findViewById(R.id.img);
        Picasso.with(activity)
                .load(My_URL.image_url + image)
                .placeholder(R.drawable.ic_photo_gallery)
                .into(imageView);
        dialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDesc;
        ImageView imgTheory;
        WebView webView;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtTitle);
            //txtDesc = (TextView) v.findViewById(R.id.txtDescription);
            webView = (WebView) v.findViewById(R.id.webView);
            imgTheory = (ImageView) v.findViewById(R.id.imgTheory);
        }
    }
}
