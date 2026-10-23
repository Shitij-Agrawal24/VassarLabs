# KrishiStock starter

Starter project for the Vassar Labs DeepTech Java code-pairing round on Saturday 24 Oct 2026.

The problem is not in this repository yet. It is released at **11:05 IST** as the tag `problem`. Until then, use this repo only to check that your environment works.

## Before the day

```bash
java -version        # must report 21 or newer
./mvnw verify        # downloads Maven on first run, then builds and runs the tests
```

`./mvnw verify` must finish with `BUILD SUCCESS`. Send the last 20 lines of its output to the coordinator by 18:00 on Friday 23 Oct. On Windows, use `mvnw.cmd verify`.

No database, cache or other server is needed. Everything runs in memory with Java and Maven.

## What is here

| Path | Purpose |
| --- | --- |
| `mvnw`, `mvnw.cmd`, `.mvn/` | Maven wrapper (Maven 3.9.11). No local Maven install needed. |
| `pom.xml` | Java 21, JUnit 5. Add any standard Java library you need. |
| `src/main/java/com/vassarlabs/krishistock/time/MutableClock.java` | A `java.time.Clock` that tests can set and move forward. |
| `src/test/java/...` | JUnit 5 tests. Put your own tests here. |
| `data/seed/` | Seed data as CSV files, and `expected_report.csv`. See `data/seed/README.md`. |
| `SUBMISSION.md` | Template you fill in before code freeze. |

## Time

Never read the system time directly (`Instant.now()`, `System.currentTimeMillis()`, `LocalDateTime.now()`). Inject a `java.time.Clock`: use `Clock.systemUTC()` in real code and `MutableClock` in tests.

```java
MutableClock clock = MutableClock.startingAt(Instant.parse("2026-07-10T06:30:00Z"));
clock.advance(Duration.ofMinutes(10));
```

## On the day

- Push at least every 30 minutes. The server-side push time is the time of record.
- 12:40: push tag `cp1`.
- 13:10: code freeze, push tag `final`. `SUBMISSION.md` must be complete.
- 13:25: push `DESIGN.md` at the repo root.

```bash
git tag cp1 && git push origin cp1
git tag final && git push origin final
```

If you cannot push, zip the project without `target/` and upload it with a screenshot of `git log` to the fallback link you were given, by 13:15.
