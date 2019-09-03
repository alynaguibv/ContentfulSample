package app.sample.contentful.network;

import java.io.IOException;

import app.sample.contentful.base.network.request.ErrorConstants;
import app.sample.contentful.base.network.request.ErrorModel;
import app.sample.contentful.base.network.request.MyRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AppRequestManager {

    private static AppRequestManager thisInstance;
    private static OkHttpClient client;

    public static AppRequestManager getInstance() {
        if (thisInstance == null) thisInstance = new AppRequestManager();
        client = new OkHttpClient();
        return thisInstance;
    }

    private AppRequestManager() {
    }

    public void start(MyRequest<?, ErrorModel> request) {
        ErrorModel errorModel = null;
        try {
            String responseBody = get(request.getBaseURL() + request.getPath());
            request.parseResponse(responseBody);
        } catch (Exception e) {
            errorModel = new ErrorModel(ErrorConstants.E500, e.getMessage());
            request.setErrorModel(errorModel);
            e.printStackTrace();
        }
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    Response post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(NetworkConstants.JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        }
    }

}
