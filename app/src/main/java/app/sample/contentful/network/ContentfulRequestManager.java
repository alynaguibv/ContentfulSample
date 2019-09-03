package app.sample.contentful.network;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Map;

import app.sample.contentful.features.recipes.response.Recipe;

public class ContentfulRequestManager {

    private static ContentfulRequestManager instance;
    private CDAClient client;

    private ContentfulRequestManager() {
        CDAClient.Builder builder = CDAClient.builder();
        builder.setSpace(NetworkConstants.CONTENTFUL_SPACE_ID);
        builder.setToken(NetworkConstants.CONTENTFUL_ACCESS_TOKEN);
        builder.setEnvironment("master");
        client = builder.build();
    }

    @Contract(pure = true)
    public static ContentfulRequestManager get() {
        synchronized (ContentfulRequestManager.class) {
            if (instance == null) {
                instance = new ContentfulRequestManager();
            }
        }
        return instance;
    }

    public ArrayList<Recipe> getEntries() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            CDAArray cdArray = client.fetch(CDAEntry.class).all();
            if (cdArray != null) {
                if (cdArray.entries() != null) {
                    for (Map.Entry<String, CDAEntry> mEntry : cdArray.entries().entrySet()) {
                        if (mEntry.getValue() != null) {
                            CDAEntry entry = mEntry.getValue();
                            Recipe recipe = Recipe.get(entry);
                            if (recipe != null) {
                                recipes.add(recipe);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
