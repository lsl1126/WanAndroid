package com.lsl.wanandroid.ui.main.mvp.presenter;

import android.content.Context;

import com.lsl.wanandroid.base.BasePresenter;
import com.lsl.wanandroid.bean.ArticlesInfo;
import com.lsl.wanandroid.bean.ArticlesTree;
import com.lsl.wanandroid.http.BaseResObserver;
import com.lsl.wanandroid.http.RxHelper;
import com.lsl.wanandroid.ui.collect.CollectPresenter;
import com.lsl.wanandroid.ui.main.mvp.contract.ProjectContract;
import com.lsl.wanandroid.ui.main.mvp.model.ProjectModel;
import com.trello.rxlifecycle3.components.support.RxFragment;

import java.util.List;

/**
 * Create by lsl on 2020/6/14
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.IProjectView> implements ProjectContract.IProjectPresenter, CollectPresenter.CollectCallBack {
    private ProjectModel model;
    private CollectPresenter collectPresenter;
    private int mPosition = -1;

    public ProjectPresenter() {
        this.model = new ProjectModel();
    }

    //    //嵌套请求项目分类和首次显示的项目文章列表
//    @Override
//    public void getFirstProjectArticle(RxFragment fragment) {
//        model.getProjectTree().flatMap(new Function<BaseResponse<List<ProjectTree>>, ObservableSource<BaseResponse<ArticlesInfo>>>() {
//            @Override
//            public ObservableSource<BaseResponse<ArticlesInfo>> apply(BaseResponse<List<ProjectTree>> listBaseResponse) throws Exception {
//                if (listBaseResponse != null && listBaseResponse.getData() != null && listBaseResponse.getData().size() > 0) {
//                    if (fragment.getActivity() != null) {
//                        //切换至Ui主线程
//                        fragment.getActivity().runOnUiThread(() -> getBaseView().onProjectTree(listBaseResponse.getData()));
//                    }
//                    return model.getProjectArticles(1, listBaseResponse.getData().get(0).getId());
//                }
//                return null;
//            }
//        }).compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseObserver<ArticlesInfo>() {
//            @Override
//            public void onSuccess(ArticlesInfo data) {
//                getBaseView().onFirstProjectArticle(data.getDatas());
//            }
//
//            @Override
//            public void onFailure(Throwable e, String error) {
//                getBaseView().onError(error);
//            }
//        });
//    }
    @Override
    public void getProjectTree(RxFragment fragment) {
        model.getProjectTree().compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<List<ArticlesTree>>() {
            @Override
            public void onSuccess(List<ArticlesTree> data) {
                getBaseView().onProjectTree(data);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onProjectTreeError(error);
            }
        });
    }

    @Override
    public void getProjectArticle(RxFragment fragment, int page, int cid, boolean isLoadMore) {
        model.getProjectArticles(page, cid).compose(RxHelper.observableIO2Main(fragment)).subscribe(new BaseResObserver<ArticlesInfo>() {
            @Override
            public void onSuccess(ArticlesInfo data) {
                getBaseView().onProjectArticle(data.getDatas(), isLoadMore);
            }

            @Override
            public void onFailure(Throwable e, String error) {
                getBaseView().onProjectArticleError(error, isLoadMore);
            }
        });
    }

    public void collect(Context context, int id, int position, boolean isCollect) {
        mPosition = position;
        if (collectPresenter == null) {
            collectPresenter = new CollectPresenter(context, this);
        }
        if (isCollect) {
            collectPresenter.collect(id);
        } else {
            collectPresenter.unCollect(id);
        }
    }

    @Override
    public void onCollectSuccess(boolean isCollect) {
        getBaseView().onCollect(mPosition, isCollect);
    }

    @Override
    public void onCollectError(String error) {
        getBaseView().onCollectError(error);
    }
}
