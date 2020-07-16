package com.lsl.wanandroid.ui.login.presenter;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.User;
import com.lsl.wanandroid.http.BaseObserver;
import com.lsl.wanandroid.http.BaseResponse;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.login.contract.LoginContract;
import com.lsl.wanandroid.ui.login.model.LoginModel;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.IPresenter {
    private LoginModel model;

    public LoginPresenter() {
        this.model = new LoginModel();
    }

    @Override
    public void login(String userName, String passWord, RxAppCompatActivity activity) {
        model.login(userName, passWord).compose(RxHelper.observableIO2Main(activity)).subscribe(new BaseObserver<BaseResponse<User>>() {
            @Override
            public void onSuccess(BaseResponse<User> data) {
                if (data.getData() != null) {
                    getBaseView().onLogin(data.getData());
                } else {
                    getBaseView().onError(data.getErrorMsg());
                }
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onError(error);
            }
        });
    }
}
