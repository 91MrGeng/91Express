package com.cea.celibrary.image.LargePicture;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.cea.celibrary.R;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;


public class ImageDetailFragment extends Fragment {
    private String mImageUrl;
    private String mThumbUrl;
    private ImageView mImageView, mThumbImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher, mAttacherThumb;


    public static ImageDetailFragment newInstance(String imageUrl, String thumbUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        args.putString("thumburl", thumbUrl);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
        mThumbUrl = getArguments() != null ? getArguments().getString("thumburl") : null;
//		T.showShort(getActivity(), mThumbUrl);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mThumbImageView = (ImageView) v.findViewById(R.id.image_thumb);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacherThumb = new PhotoViewAttacher(mThumbImageView);
        mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        mAttacherThumb.setOnPhotoTapListener(new OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                // TODO Auto-generated method stub
                getActivity().finish();
            }
        });


        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Glide.with(getActivity()).load(mThumbUrl).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                progressBar.setVisibility(View.GONE);
                mImageView.setVisibility(View.VISIBLE);
                mThumbImageView.setVisibility(View.GONE);
                mImageView.setImageDrawable(resource.getCurrent());
                mAttacherThumb.update();
                mAttacher.update();
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                progressBar.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                mThumbImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                String message = "图片加载失败";
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }

        });


//		ImageLoader.getInstance().displayImage(mThumbUrl, mThumbImageView, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {
//				mImageView.setVisibility(View.GONE);
//				mThumbImageView.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				mAttacherThumb.update();
//			}
//		});

//
//		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//			@Override
//			public void onLoadingStarted(String imageUri, View view) {
//				progressBar.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//				String message = null;
//				switch (failReason.getType()) {
//				case IO_ERROR:
//					message = "下载错误";
//					break;
//				case DECODING_ERROR:
//					message = "图片无法显示";
//					break;
//				case NETWORK_DENIED:
//					message = "网络有问题，无法下载";
//					break;
//				case OUT_OF_MEMORY:
//					message = "图片太大无法显示";
//					break;
//				case UNKNOWN:
//					message = "未知的错误";
//					break;
//				}
//				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//				progressBar.setVisibility(View.GONE);
//			}
//			@Override
//			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//				progressBar.setVisibility(View.GONE);
//				mImageView.setVisibility(View.VISIBLE);
//				mThumbImageView.setVisibility(View.GONE);
//				mAttacher.update();
//			}
//		});
    }

}
