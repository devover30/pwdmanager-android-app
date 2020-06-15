package info.devram.tizori.controller;

public interface LoginAsyncResponse {

    void getToken(String token);

    void responseError(Boolean isNetworkError);
}
