package org.example.observer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogNotifier<T> implements Notifier<T> {
    @Override
    public void notify(T t) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("Object [{}] received", t);
    }
}
