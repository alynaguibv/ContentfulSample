package app.sample.contentful.base.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import app.sample.contentful.R;
import app.sample.contentful.base.presenter.MyPresenter;
import app.sample.contentful.base.view.MyView;

public class MyActivity<P extends MyPresenter> extends AppCompatActivity implements MyView {

    P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void handleError(Throwable e) {
        Toast.makeText(this, getString(R.string.msg_general_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public P getPresenter() {
        return presenter;
    }
}
