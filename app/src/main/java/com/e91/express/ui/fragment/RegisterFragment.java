package com.e91.express.ui.fragment;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cea.celibrary.utils.L;
import com.e91.express.R;
import com.e91.express.base.BaseFragment;
import com.e91.express.di.component.ApiServiceComponent;
import com.e91.express.mvp.presenter.RegisterPresenter;
import com.e91.express.mvp.view.RegisterView;
import com.squareup.okhttp.Response;

import butterknife.Bind;
import butterknife.OnClick;
import nucleus.factory.RequiresPresenter;

/**
 * @author devin
 * @Class RegisterFragment
 * @Date 16/7/3
 */
@RequiresPresenter(RegisterPresenter.class)
public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterView {

    @Bind(R.id.user)
    EditText user;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.mobile)
    EditText mobile;

    Response response = null;

/*    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try {
                        L.i(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };*/

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void injectPresenter() {
        super.injectPresenter();
        ApiServiceComponent.Instance.get().inject(getPresenter());
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }

    @OnClick(R.id.register)
    public void register() {
        L.i("onclick");
        getPresenter().register(user.getText().toString(),password.getText().toString(),mobile.getText().toString());
        //post();
    }

    /*public void post() {
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),"{\"username\":\"11111\",\"password\":\"1111111\",\"mobile\":\"131924000114\"}");
        final Request request = new Request.Builder().url(CEConstants.Url.BASE_URL+ RegisterApi.URL.REGISTER_URL)
                .post(body).build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    response = client.newCall(request).execute();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/

    @Override
    public void showErrMsg(String errMsg, Object o) {
        L.i("errMsg"+errMsg);
        Toast.makeText(getActivity(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress(String pgMsg, Object o) {
        L.i("loading");
    }

    @Override
    public void showSuccessMsg(String successMsg, Object o) {
        L.i("successMsg"+successMsg);
        Toast.makeText(getActivity(), successMsg, Toast.LENGTH_SHORT).show();
    }

}
