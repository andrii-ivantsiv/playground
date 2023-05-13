package org.example.observer;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotifiableQueTest {

    @Test
    void addObjectWithNotificationTest() {
        final LogCaptor logCaptor = LogCaptor.forClass(LogNotifier.class);
        final NotifiableQue<String> strings = new NotifiableQue<>();
        final LogNotifier<String> notifier = new LogNotifier<>();
        final String expectedValue = "test";
        strings.addNotifier(notifier);

        strings.add(expectedValue);

        assertThat(logCaptor.getLogs())
                .hasSize(1)
                .first()
                .matches(log -> log.contains(expectedValue));
    }
}