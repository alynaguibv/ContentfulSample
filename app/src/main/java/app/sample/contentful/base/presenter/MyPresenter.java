package app.sample.contentful.base.presenter;

import java.lang.ref.WeakReference;

import app.sample.contentful.base.view.MyView;

public abstract class MyPresenter<V extends MyView> {

    private WeakReference<V> viewWeakReference;

    private WeakReference<V> getViewWeakReference() {
        return viewWeakReference;
    }

    protected V getView() {
        if (isViewAttached()) {
            return getViewWeakReference().get();
        }
        return null;
    }

    public void attachView(V passedView) {
        setViewWeakReference(new WeakReference<>(passedView));
    }

    public void detachView() {
        if (getViewWeakReference() != null) {
            getViewWeakReference().clear();
            setViewWeakReference(null);
        }
    }

    protected boolean isViewAttached() {
        return getViewWeakReference() != null && getViewWeakReference().get() != null;
    }

    private void setViewWeakReference(WeakReference<V> viewWeakReference) {
        this.viewWeakReference = viewWeakReference;
    }
}
