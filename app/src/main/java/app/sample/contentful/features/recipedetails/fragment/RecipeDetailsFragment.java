package app.sample.contentful.features.recipedetails.fragment;

import android.app.Activity;
import android.text.util.Linkify;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import app.sample.contentful.R;
import app.sample.contentful.base.fragment.MyFragment;
import app.sample.contentful.features.recipedetails.activity.RecipeDetailsActivity;
import app.sample.contentful.features.recipedetails.presenter.ArticleDetailsPresenter;
import app.sample.contentful.features.recipes.activity.RecipesListActivity;
import app.sample.contentful.features.recipes.response.Recipe;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link RecipesListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailsActivity}
 * on handsets.
 */
public class RecipeDetailsFragment extends MyFragment<ArticleDetailsPresenter> {
    /**
     * The fragment argument representing the article that this fragment
     * represents.
     */
    public static final String ARG_RECIPE = "recipeArg";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailsFragment() {
    }

    @Override
    protected ArticleDetailsPresenter createPresenter() {
        return new ArticleDetailsPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.item_detail;
    }

    @Override
    public void onViewReady() {
        if (getArguments() != null && getArguments().containsKey(ARG_RECIPE)) {

            Recipe mItem = (Recipe) getArguments().getSerializable(ARG_RECIPE);
            Activity activity = this.getActivity();

            TextView descriptionView = mainView.findViewById(R.id.descriptionView);
            TextView chefView = mainView.findViewById(R.id.chefView);
            TextView caloriesView = mainView.findViewById(R.id.caloriesView);
            LinearLayout tagsView = mainView.findViewById(R.id.tagsView);

            if (activity != null) {
                CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
                if (appBarLayout != null) {
                    appBarLayout.setTitle(mItem.getName());
                }
            }

            if (mItem.getDescription() != null && !mItem.getDescription().isEmpty()) {
                descriptionView.setText(mItem.getDescription());
                Linkify.addLinks(descriptionView, Linkify.WEB_URLS);
            }

            if (mItem.getChef() != null && !mItem.getChef().isEmpty()) {
                chefView.setText(getString(R.string.chef_string) + " " + mItem.getChef());
            }

            if (mItem.getCalories() != null && mItem.getCalories() != 0) {
                caloriesView.setText(getString(R.string.calories_string) + " " + mItem.getCalories() + "");
            }

            if (mItem.getTags() != null && !mItem.getTags().isEmpty()) {
                int i = 0;
                for (String tag : mItem.getTags()) {
                    TextView tagView = new TextView(getContext());
                    tagView.setTextSize(12);
                    tagView.setTextColor(getResources().getColor(R.color.colorAccent));
                    tagView.setText((i == 0 ? "Tags: " : "") + tag + (i != mItem.getTags().size() - 1 ? ", " : ""));
                    i++;
                    Linkify.addLinks(tagView, Linkify.WEB_URLS);
                    tagsView.addView(tagView);
                }
                tagsView.invalidate();
            }
        }
    }

    @Override
    public void handleError(Throwable e) {

    }

    @Override
    public ArticleDetailsPresenter getPresenter() {
        return presenter;
    }
}
