package org.example.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogNotifier<T> implements Notifier<T> {
    @Override
    public void notify(T t) {
        log.info("Object [{}] received", t);
    }
}
