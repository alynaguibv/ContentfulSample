package app.sample.contentful.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import app.sample.contentful.BuildConfig;
import app.sample.contentful.base.presenter.MyPresenter;
import app.sample.contentful.base.view.MyView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MyFragment<P extends MyPresenter> extends Fragment implements MyView {

    protected P presenter;

    protected abstract P createPresenter();

    protected abstract int getLayoutRes();

    private Unbinder mUnbinder;

    protected View mainView;

    int i = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (getLayoutRes() != -1) {
            mainView = inflater.inflate(getLayoutRes(), container, false);
        }
        ButterKnife.setDebug(BuildConfig.DEBUG);
        mUnbinder = ButterKnife.bind(this, mainView);
        presenter.attachView(this);
        onViewReady();
        return mainView;
    }

    public abstract void onViewReady();

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

}
