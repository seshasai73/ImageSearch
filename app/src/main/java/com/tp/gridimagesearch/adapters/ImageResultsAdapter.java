package com.tp.gridimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tp.gridimagesearch.R;
import com.tp.gridimagesearch.models.ImageResults;

import java.util.List;

/**
 * Created by seshasa on 2/1/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResults> {
    public ImageResultsAdapter(Context context, List<ImageResults> images) {
        super(context, R.layout.result_grid_item , images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageResults img = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.result_grid_item, parent, false);
        }
        ImageView iv_Image = (ImageView) convertView.findViewById(R.id.iv_Image);
        TextView tv_Title = (TextView) convertView.findViewById(R.id.tv_Title);
        Picasso.with(getContext()).load(img.thumbUrl).into(iv_Image);
        iv_Image.setImageResource(0);
        tv_Title.setText(Html.fromHtml(img.title));
        return convertView;
    }
}
