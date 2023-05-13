package org.example.observer;

public interface Notifiable<T> {
    boolean addNotifier(Notifier<T> notifier);

    boolean removeNotifier(Notifier<T> notifier);

    void notifyAll(T t);
}
