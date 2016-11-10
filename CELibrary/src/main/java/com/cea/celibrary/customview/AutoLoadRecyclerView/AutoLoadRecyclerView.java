package com.cea.celibrary.customview.AutoLoadRecyclerView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;


public class AutoLoadRecyclerView extends RecyclerView implements LoadFinishCallBack {

    private onLoadMoreListener loadMoreListener;    //加载更多回调
    private boolean isLoadingMore;                  //是否加载更多

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        isLoadingMore = false;  //默认无需加载更多
        addOnScrollListener(new AutoLoadScrollListener(null, true, true));
    }

    /**
     * 配置显示图片，需要设置这几个参数，快速滑动时，暂停图片加载
     *
     * @param glide         Glide实例对象
     * @param pauseOnScroll
     * @param pauseOnFling
     */
    public void setOnPauseListenerParams(Glide glide, boolean pauseOnScroll, boolean pauseOnFling) {

        addOnScrollListener(new AutoLoadScrollListener(glide, pauseOnScroll, pauseOnFling));

    }

    public void setLoadMoreListener(onLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void loadFinish(Object obj) {
        isLoadingMore = false;
    }

    @Override
    public void loadFinish() {
        isLoadingMore = false;
    }

    //加载更多的回调接口
    public interface onLoadMoreListener {
        void loadMore();
    }

    public boolean isLoadingMore() {
        return isLoadingMore;
    }


    /**
     * 滑动自动加载监听器
     */
    private class AutoLoadScrollListener extends OnScrollListener {

        private Glide glide;
        private final boolean pauseOnScroll;
        private final boolean pauseOnFling;

        public AutoLoadScrollListener(Glide glide, boolean pauseOnScroll, boolean pauseOnFling) {
            super();
            this.pauseOnScroll = pauseOnScroll;
            this.pauseOnFling = pauseOnFling;
            this.glide = glide;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //由于GridLayoutManager是LinearLayoutManager子类，所以也适用
            if (getLayoutManager() instanceof LinearLayoutManager) {
                int lastVisibleItem = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                int totalItemCount = AutoLoadRecyclerView.this.getAdapter().getItemCount();

                //有回调接口，且不是加载状态，且计算后剩下1个item，且处于向下滑动，则自动加载
                int tagNum = getInt(totalItemCount);
                if (loadMoreListener != null && !isLoadingMore && lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    loadMoreListener.loadMore();
                    isLoadingMore = true;
                }
            }
        }

        //当屏幕停止滚动时为0；当屏幕滚动且用户使用的触碰或手指还在屏幕上时为1；由于用户的操作，屏幕产生惯性滑动时为2
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            //根据newState状态做处理
            if (glide != null) {
                switch (newState) {
                    case 0:
                        glide.with(getContext()).resumeRequests();
                        break;

                    case 1:
                        if (pauseOnScroll) {
                            glide.with(getContext()).pauseRequests();
                        } else {
                            glide.with(getContext()).resumeRequests();
                        }
                        break;

                    case 2:
                        if (pauseOnFling) {
                            glide.with(getContext()).pauseRequests();
                        } else {
                            glide.with(getContext()).resumeRequests();
                        }
                        break;
                }
            }
        }
    }

    private int getInt(int n) {
        String str = String.valueOf(n);
        //把整数转化为字符串
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < str.length(); i++) {
            if (i < 3) {
                sb.setCharAt(i, str.charAt(i));
                continue;
            } else sb.setCharAt(i, '0');
        }
        String s = sb.toString();
        int e = Integer.valueOf(s);
//        L.d("保留前3位数字 -> " + e);
        return e;
    }
}
