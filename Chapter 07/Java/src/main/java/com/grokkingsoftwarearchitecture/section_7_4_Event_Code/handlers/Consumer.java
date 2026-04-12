package chapter07.eventcode.handlers;

import chapter07.eventcode.shared.Event;
import java.util.concurrent.CompletableFuture;

/**
 * THE HANDLER CONTRACT: The abstraction for all background workers.
 * It ensures every consumer has a standard entry point for incoming messages.
 */
public interface Consumer<T extends Event> {
    CompletableFuture<Void> handleAsync(T event);
}