package app.sample.contentful.features.recipes.presenter;

import java.util.ArrayList;

import app.sample.contentful.base.presenter.MyPresenter;
import app.sample.contentful.features.recipes.activity.RecipesListActivity;
import app.sample.contentful.features.recipes.response.Recipe;
import app.sample.contentful.features.recipes.service.RecipesService;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RecipesPresenter extends MyPresenter<RecipesListActivity> {

    public void loadRecentArticles() {
        RecipesService recipesService = new RecipesService();
        Observer observer = new Observer<ArrayList<Recipe>>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (isViewAttached()) {
                    getView().showLoading();
                }
            }

            @Override
            public void onNext(ArrayList<Recipe> entries) {
                if (isViewAttached()) {
                    getView().hideLoading();
                    getView().setupRecyclerView(entries);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (isViewAttached()) {
                    getView().hideLoading();
                }
            }

            @Override
            public void onComplete() {

            }
        };
        recipesService.getRecipes(observer);
    }
}
