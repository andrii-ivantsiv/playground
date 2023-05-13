package org.example.observer;

public interface Notifier<T> {
    void notify(T t);
}
