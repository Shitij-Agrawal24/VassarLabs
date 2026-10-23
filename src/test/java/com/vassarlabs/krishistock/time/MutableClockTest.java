package com.vassarlabs.krishistock.time;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

class MutableClockTest {

    private static final Instant START = Instant.parse("2026-07-01T04:30:00Z");

    @Test
    void startsAtTheGivenInstant() {
        MutableClock clock = MutableClock.startingAt(START);

        assertEquals(START, clock.instant());
        assertEquals(START.toEpochMilli(), clock.millis());
    }

    @Test
    void advanceMovesTimeForward() {
        MutableClock clock = MutableClock.startingAt(START);

        clock.advance(Duration.ofMinutes(10));

        assertEquals(START.plusSeconds(600), clock.instant());
    }

    @Test
    void setReplacesTheCurrentInstant() {
        MutableClock clock = MutableClock.startingAt(START);
        Instant later = START.plus(Duration.ofDays(1));

        clock.set(later);

        assertEquals(later, clock.instant());
    }

    @Test
    void zonedViewSharesTheSameInstant() {
        MutableClock clock = MutableClock.startingAt(START);
        Clock ist = clock.withZone(ZoneId.of("Asia/Kolkata"));

        clock.advance(Duration.ofSeconds(1));

        assertEquals(clock.instant(), ist.instant());
        assertEquals(ZoneId.of("Asia/Kolkata"), ist.getZone());
    }
}
