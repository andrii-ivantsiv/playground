package org.example.observer;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class AsyncNotifiableQue<T> extends ArrayDeque<T> implements Notifiable<T> {
    // private final List<Notifier<T>> notifiers = new ArrayList<>(); // not thread safe when traverse and add
    private final CopyOnWriteArrayList<Notifier<T>> notifiers = new CopyOnWriteArrayList<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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
        log.debug("Start notify {} notifiers", notifiers.size());
        executorService.execute(() ->
                notifiers.forEach(notifier -> {
                    try {
                        notifier.notify(object);
                        log.debug("Notifier has been notified {}", object);
                    } catch (Exception exception) {
                        log.error("Exception during async notifying", exception);
                    }
                })
        );
        log.debug("End notify notifiers");
    }

    @Override
    public boolean add(T object) {
        final boolean addResult = super.add(object);
        notifyAll(object);
        return addResult;
    }
}
