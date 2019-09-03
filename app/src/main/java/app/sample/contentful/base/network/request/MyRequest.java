package app.sample.contentful.base.network.request;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class MyRequest<M, E> {

    private M response;
    private E errorModel;
    protected String path;
    protected String baseURL;

    public M parseResponse(String response) {
        Gson gson = new Gson();
        try {
            setResponse((M) gson.fromJson(response, getModelClass()));
            return getResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class<M> getModelClass() {
        Type genericSuperClass = getClass().getGenericSuperclass();

        ParameterizedType parametrizedType = null;
        while (parametrizedType == null) {
            if ((genericSuperClass instanceof ParameterizedType)) {
                parametrizedType = (ParameterizedType) genericSuperClass;
            } else {
                genericSuperClass = ((Class<?>) genericSuperClass).getGenericSuperclass();
            }
        }

        return (Class<M>) parametrizedType.getActualTypeArguments()[0];
    }

    public String getPath() {
        return path;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public M getResponse() {
        return response;
    }

    public E getErrorModel() {
        return errorModel;
    }

    public void setErrorModel(E errorModel) {
        this.errorModel = errorModel;
    }

    public void setResponse(M response) {
        this.response = response;
    }
}
