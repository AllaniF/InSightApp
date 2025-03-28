package service;

public interface Callback<T> {
    void onMessage(T t);
}