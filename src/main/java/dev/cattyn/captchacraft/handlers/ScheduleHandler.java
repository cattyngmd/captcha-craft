package dev.cattyn.captchacraft.handlers;

import dev.cattyn.captchacraft.config.Config;

import java.util.concurrent.atomic.AtomicInteger;

public class ScheduleHandler {
    private final AtomicInteger ticksLeft = new AtomicInteger(-1);

    public void schedule(SolverHandler solver) {
        if (solver.isSolving() || !Config.enabled) return;
        ticksLeft.compareAndSet(-1, Math.max(Config.delay, 0));
    }

    public boolean schedulePassed() {
        int currentTicks = ticksLeft.get();

        if (currentTicks == 0) {
            ticksLeft.set(-1);
            return true;
        }

        if (currentTicks > 0)
            ticksLeft.decrementAndGet();
        return false;
    }
}
