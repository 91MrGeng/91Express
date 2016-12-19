package com.e91.express.ui.fragment.driver;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.e91.express.R;
import com.e91.express.base.BaseFragment;
import com.e91.express.mvp.presenter.ReceivingPresenter;
import com.e91.express.mvp.view.ReceivingView;
import com.e91.express.ui.view.TurnCardListView;

import butterknife.Bind;

/**
 * =====================================
 * 项目名称：com.e91.express.ui.fragment
 * 类描述：
 * 创建人：大风车
 * 创建时间：2016/10/23 15:39
 * 修改人：大风车
 * 修改时间：2016/10/23 15:39
 * 版本说明：Version1.0
 * =====================================
 */

public class ReceivingFragment extends BaseFragment<ReceivingPresenter> implements ReceivingView {

    private Activity mContext;
    @Bind(R.id.card_list)
    TurnCardListView list;

    public static ReceivingFragment newInstance() {
        Bundle args = new Bundle();
        ReceivingFragment fragment = new ReceivingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_receive;
    }


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        mContext = getActivity();
        setEvent();
        setAdapetr();
    }

    private void setEvent() {
        list.setOnTurnListener(new TurnCardListView.OnTurnListener() {
            @Override
            public void onTurned(int position) {
                Toast.makeText(mContext, "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapetr() {
        list.setAdapter(new BaseAdapter() {
            int[] colors = {R.color.card4};

            @Override
            public int getCount() {
                return colors.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View child, ViewGroup parent) {
                if (child == null) {
                    child = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
                }
                child.findViewById(R.id.image).setBackgroundColor(colors[position]);
                return child;
            }
        });
    }

    @Override
    public void showErrMsg(String errMsg, Object o) {

    }

    @Override
    public void showProgress(String pgMsg, Object o) {

    }

    @Override
    public void showSuccessMsg(String successMsg, Object o) {

    }





}
