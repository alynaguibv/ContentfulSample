package app.sample.contentful.base.view;

import app.sample.contentful.base.presenter.MyPresenter;

public interface MyView {

    void showLoading();

    void hideLoading();

    void handleError(Throwable e);

    MyPresenter getPresenter();


}
