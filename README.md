# KrishiStock starter

Starter project for the Vassar Labs DeepTech Java code-pairing round on Saturday 24 Oct 2026.

The problem is not in this repository yet. It is pushed here at **11:05 IST** as the tag `problem`. You do your work in **your own private repository**, set up from this one as described below. You cannot push to this repository.

## Before the day (by 18:00 on Friday 23 Oct)

1. On GitHub, create a new **private**, **empty** repository under your own account, for example `krishistock`. Do not add a README, licence or `.gitignore`. Do not fork this repository (a fork of a public repository is public) and do not use "Use this template" (you would not be able to pull the problem later).
2. Copy this repository into it:

   ```bash
   git clone https://github.com/VassarLabsV2/krishistock-starter.git krishistock
   cd krishistock
   git remote rename origin starter
   git remote add origin https://github.com/<your-username>/krishistock.git
   git push -u origin main
   ```

3. In your repository, go to **Settings → Collaborators → Add people** and add each GitHub account the coordinator gave you. They need at least read access.
4. Check your environment:

   ```bash
   java -version        # must report 21 or newer
   ./mvnw verify        # downloads Maven on first run, then builds and runs the tests
   ```

   `./mvnw verify` must finish with `BUILD SUCCESS`. On Windows, use `mvnw.cmd verify`.

5. Send the coordinator your repository URL and the last 20 lines of the `./mvnw verify` output.

Do not delete the repository, rewrite its history or force-push. The panel reads your commits and push times from it.

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

At **11:05**, pull the problem into your repository, and push it to your own:

```bash
git pull --no-rebase --no-edit starter main
git push
```

At **12:40**, when the broadcast says so, pull the additional requirement the same way (`git pull --no-rebase --no-edit starter main`, then `git push`).

While you work:

- Commit and push to your own repository (`git push`) at least every 30 minutes. The time GitHub receives a push is the time of record.
- 12:40: tag and push `cp1`.
- 13:10: code freeze, tag and push `final`. `SUBMISSION.md` must be complete.
- 13:25: push `DESIGN.md` at the repo root.

```bash
git tag cp1 && git push origin cp1
git tag final && git push origin final
```

Always push to `origin` (your repository). `starter` is read-only.

If you cannot push, zip the project without `target/` and upload it with a screenshot of `git log` to the fallback link you were given, by 13:15.
