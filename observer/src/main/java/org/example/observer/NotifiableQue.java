package org.example.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class NotifiableQue<T> extends ArrayDeque<T> implements Notifiable<T> {

    private final List<Notifier<T>> notifiers = new ArrayList<>();

    @Override
    public boolean addNotifier(Notifier<T> notifier) {
        return notifiers.add(notifier);
    }

    @Override
    public boolean removeNotifier(Notifier<T> notifier) {
        return notifiers.remove(notifier);
    }

    @Override
    public void notifyAll(T object) {
        notifiers.forEach(notifier -> {
            try {
                notifier.notify(object);
            } catch (Exception exception) {
                log.error("Exception during notifying", exception);
            }
        });
    }

    @Override
    public boolean add(T t) {
        final boolean addResult = super.add(t);
        notifyAll(t);
        return addResult;
    }
}
