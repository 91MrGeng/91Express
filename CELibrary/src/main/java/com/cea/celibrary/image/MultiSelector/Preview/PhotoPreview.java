package com.cea.celibrary.image.MultiSelector.Preview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cea.celibrary.R;
import com.cea.celibrary.image.MultiSelector.BitmapTransform;

import java.io.File;

import uk.co.senab.photoview.PhotoView;



public class PhotoPreview extends LinearLayout implements OnClickListener {

    private PhotoView ivContent;
    private OnClickListener l;
    private Context context;

    public PhotoPreview(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.view_photopreview, this, true);
        ivContent = (PhotoView) findViewById(R.id.iv_content_vpp);
        ivContent.setOnClickListener(this);
//        initImageLoader();

    }

    public PhotoPreview(Context context, AttributeSet attrs) {
        this(context);
    }

    public PhotoPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context);
    }



    public void loadImage(final String path) {
        final int MAX_WIDTH = 500;
        final int MAX_HEIGHT = 500;
        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

        Glide.with(context).load(new File(path)).transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .fitCenter().into(ivContent);

    }
    public void loadImageUri(final String path) {
        final int MAX_WIDTH = 500;
        final int MAX_HEIGHT = 500;
        int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));

        Glide.with(context).load(path).transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                .fitCenter().into(ivContent);

    }
    @Override
    public void setOnClickListener(OnClickListener l) {
        this.l = l;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_content_vpp && l != null) {
            l.onClick(ivContent);
        }
    }
}
