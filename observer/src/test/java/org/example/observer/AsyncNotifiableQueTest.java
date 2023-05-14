package org.example.observer;

import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.awaitility.Awaitility.await;

/**
 * @see AsyncNotifiableQue
 */
class AsyncNotifiableQueTest {

    @Test
    void addObjectWithNotificationTest() {
        final LogCaptor logCaptor = LogCaptor.forClass(LogNotifier.class);
        final AsyncNotifiableQue<String> strings = new AsyncNotifiableQue<>();
        final int notifiersCount = 10;
        final int addedElementsCount = 20;
        final int minExpectedCount = notifiersCount * addedElementsCount;
        generateLogNotifier(notifiersCount).forEach(strings::addNotifier);

        IntStream.range(0, addedElementsCount)
                .mapToObj(String::valueOf)
                .forEach(strings::add);

        assertThatCode(() -> generateLogNotifier(notifiersCount).forEach(strings::addNotifier))
                .withFailMessage("Shouldn't throw ConcurrentModificationException because it's CopyOnWriteArrayList")
                .doesNotThrowAnyException();
        await()
                .atMost(30, TimeUnit.SECONDS)
                .untilAsserted(() -> assertThat(logCaptor.getLogs())
                        .withFailMessage("Found %s logs but expected more then %s",
                                logCaptor.getLogs().size(), minExpectedCount)
                        .hasSizeGreaterThan(minExpectedCount));
    }

    private List<LogNotifier<String>> generateLogNotifier(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> new LogNotifier<String>())
                .toList();
    }
}