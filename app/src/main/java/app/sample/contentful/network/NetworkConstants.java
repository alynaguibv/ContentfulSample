package app.sample.contentful.network;

import okhttp3.MediaType;

public class NetworkConstants {

    public static final String CONTENTFUL_SPACE_ID = "kk2bw5ojx476";
    public static final String CONTENTFUL_ACCESS_TOKEN = "7ac531648a1b5e1dab6c18b0979f822a5aad0fe5f1109829b8a197eb2be4b84c";
    //
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public final class RecipeContentful {
        public static final String PHOTO_FIELD = "photo";
        public static final String DESCRIPTION_FIELD = "description";
        public static final String CALORIES_FIELD = "calories";
        public static final String NAME_FIELD = "name";
        public static final String TITLE_FIELD = "title";
        public static final String TAGS_FIELD = "tags";
        public static final String CHEF_FIELD = "chef";
        public static final String DEFAULT_LOCALE = "en-US";

    }
}
