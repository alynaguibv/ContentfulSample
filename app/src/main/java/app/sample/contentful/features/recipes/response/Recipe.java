package app.sample.contentful.features.recipes.response;

import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;

import java.io.Serializable;
import java.util.ArrayList;

import app.sample.contentful.network.NetworkConstants;

public class Recipe implements Serializable {

    private String photo;
    private String description;
    private Double calories;
    private String name;
    private ArrayList<String> tags;
    private String chef;

    private Recipe() {
    }

    public static Recipe get(CDAEntry entry) {
        try {
            String name = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.NAME_FIELD);
            String title = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.TITLE_FIELD);

            if (name != null || title != null) {
                Recipe recipe = new Recipe();
                if (name != null && title == null) {
                    recipe.setName(name);
                }
                if (title != null && name == null) {
                    recipe.setName(title);
                }
                CDAEntry chefEntry = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.CHEF_FIELD);
                if (chefEntry != null) {
                    recipe.setChef(chefEntry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.NAME_FIELD));
                }
                String description = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.DESCRIPTION_FIELD);
                if (description != null) {
                    recipe.setDescription(description);
                }
                CDAAsset photoEntry = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.PHOTO_FIELD);
                if (photoEntry != null) {
                    String url = photoEntry.url();
                    if (url != null && !url.isEmpty()) {
                        if (!url.startsWith("https:") && !url.startsWith("http:")) {
                            recipe.setPhoto("https:" + url);
                        }
                    }
                }
                ArrayList<CDAEntry> tagEntries = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.TAGS_FIELD);
                if (tagEntries != null) {
                    ArrayList<String> tags = new ArrayList<>();
                    for (CDAEntry tagEntry : tagEntries) {
                        String tag = tagEntry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.NAME_FIELD);
                        tags.add(tag);
                    }
                    if (!tags.isEmpty()) {
                        recipe.setTags(tags);
                    }
                }
                Double calories = entry.getField(NetworkConstants.RecipeContentful.DEFAULT_LOCALE, NetworkConstants.RecipeContentful.CALORIES_FIELD);
                if (calories != null) {
                    recipe.setCalories(calories);
                }
                return recipe;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoto() {
        return photo;
    }

    private void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Double getCalories() {
        return calories;
    }

    private void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    private void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getChef() {
        return chef;
    }

    private void setChef(String chef) {
        this.chef = chef;
    }

}
