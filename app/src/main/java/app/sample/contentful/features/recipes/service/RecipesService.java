package app.sample.contentful.features.recipes.service;

import java.util.ArrayList;

import app.sample.contentful.features.recipes.response.Recipe;
import app.sample.contentful.network.ContentfulRequestManager;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RecipesService {

    public Observable<ArrayList<Recipe>> getRecipes(Observer<ArrayList<Recipe>> observer) {
        Observable<ArrayList<Recipe>> observable = Observable.defer(() -> {
            ArrayList<Recipe> array = ContentfulRequestManager.get().getEntries();
            return Observable.just(array);
        });
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        return observable;
    }

}
