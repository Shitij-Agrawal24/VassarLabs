package com.vassarlabs.krishistock.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A {@link Clock} whose current instant is set by the caller.
 *
 * <p>Production code should depend on {@link java.time.Clock} and never call
 * {@code Instant.now()} or {@code System.currentTimeMillis()} directly. Wire in
 * {@code Clock.systemUTC()} for real use and a {@code MutableClock} in tests, so a
 * test can move time forward deterministically.
 *
 * <p>Thread-safe: {@link #set} and {@link #advance} may be called while other
 * threads read {@link #instant()}.
 */
public final class MutableClock extends Clock {

    private final AtomicReference<Instant> now;
    private final ZoneId zone;

    private MutableClock(AtomicReference<Instant> now, ZoneId zone) {
        this.now = now;
        this.zone = zone;
    }

    /** Creates a clock fixed at {@code start}, in UTC. */
    public static MutableClock startingAt(Instant start) {
        return new MutableClock(new AtomicReference<>(Objects.requireNonNull(start)), ZoneOffset.UTC);
    }

    /** Sets the current instant. */
    public void set(Instant instant) {
        now.set(Objects.requireNonNull(instant));
    }

    /** Moves the current instant forward (or backward, for a negative duration). */
    public Instant advance(Duration by) {
        Objects.requireNonNull(by);
        return now.updateAndGet(current -> current.plus(by));
    }

    @Override
    public Instant instant() {
        return now.get();
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }

    /** Returns a view in another zone that shares this clock's instant. */
    @Override
    public Clock withZone(ZoneId newZone) {
        return new MutableClock(now, Objects.requireNonNull(newZone));
    }
}
