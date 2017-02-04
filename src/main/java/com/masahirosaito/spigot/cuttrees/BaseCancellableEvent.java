package com.masahirosaito.spigot.cuttrees;

import org.bukkit.event.Cancellable;

public abstract class BaseCancellableEvent extends BaseEvent implements Cancellable {

    private boolean isCancelled = false;

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    public boolean isNotCancelled() {
        return !isCancelled;
    }

    public void cancel() {
        isCancelled = true;
    }
}
