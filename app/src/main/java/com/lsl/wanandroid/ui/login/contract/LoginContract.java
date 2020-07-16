package com.lsl.wanandroid.ui.login.contract;

import com.lsl.wanandroid.base.IBaseView;
import com.lsl.wanandroid.bean.User;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle3.components.support.RxFragment;

public class LoginContract {
    public interface ILoginView extends IBaseView {
        void onLogin(User user);

        void onError(String error);
    }

    public interface IPresenter {
        void login(String userName, String passWord, RxAppCompatActivity activity);
    }
}
