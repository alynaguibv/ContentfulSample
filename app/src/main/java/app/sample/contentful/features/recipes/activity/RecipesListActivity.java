package app.sample.contentful.features.recipes.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.sample.contentful.R;
import app.sample.contentful.base.activity.MyActivity;
import app.sample.contentful.features.recipedetails.activity.RecipeDetailsActivity;
import app.sample.contentful.features.recipedetails.fragment.RecipeDetailsFragment;
import app.sample.contentful.features.recipes.presenter.RecipesPresenter;
import app.sample.contentful.features.recipes.response.Recipe;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailsActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipesListActivity extends MyActivity<RecipesPresenter> {

    private static List<Recipe> ITEMS = null;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private View recyclerView;
    private ProgressBar progressBar;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        progressBar = findViewById(R.id.progress);
        refreshButton = findViewById(R.id.refreshBtn);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;

        getPresenter().loadRecentArticles();

        refreshButton.setOnClickListener(v -> getPresenter().loadRecentArticles());
    }

    public void setupRecyclerView(List<Recipe> items) {
        ITEMS = items;
        ((RecyclerView) recyclerView).setAdapter(new ArticleRecyclerViewAdapter(this, ITEMS, mTwoPane));
    }

    public static class ArticleRecyclerViewAdapter
            extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder> {

        private final RecipesListActivity mParentActivity;
        private final List<Recipe> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe item = (Recipe) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(RecipeDetailsFragment.ARG_RECIPE, item);
                    RecipeDetailsFragment fragment = new RecipeDetailsFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailsActivity.class);
                    intent.putExtra(RecipeDetailsFragment.ARG_RECIPE, item);

                    context.startActivity(intent);
                }
            }
        };

        ArticleRecyclerViewAdapter(RecipesListActivity parent,
                                   List<Recipe> items,
                                   boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.titleView.setText(mValues.get(position).getName());
            String photoUrl = mValues.get(position).getPhoto();
            if (photoUrl != null) {
                Picasso.get().load(photoUrl).fit().into(holder.img);
            }
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView titleView;
            final AppCompatImageView img;
            final ImageView detailsImg;

            ViewHolder(View view) {
                super(view);
                titleView = view.findViewById(R.id.name);
                img = view.findViewById(R.id.img);
                detailsImg = view.findViewById(R.id.details_img);
            }
        }
    }

    @Override
    public RecipesPresenter getPresenter() {
        RecipesPresenter myPresenter = new RecipesPresenter();
        myPresenter.attachView(this);
        return myPresenter;
    }

    @Override
    protected void onDestroy() {
        getPresenter().detachView();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        super.showLoading();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        progressBar.setVisibility(View.GONE);
    }
}
