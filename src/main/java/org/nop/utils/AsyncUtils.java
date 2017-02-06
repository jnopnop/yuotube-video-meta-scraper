package org.nop.utils;

import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AsyncUtils {

    public static <T> CompletableFuture<T> toCompletableFuture(final ListenableFuture<T> listenableFuture) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        listenableFuture.addCallback(completableFuture::complete, completableFuture::completeExceptionally);
        return completableFuture;
    }

    public static <T, U> T mapToAsyncResult(final CompletableFuture<U> completableFuture, final Supplier<? extends T> resultSupplier,
                                       final BiConsumer<? super T, ? super U> successConsumer,
                                       final BiConsumer<? super T, ? super Throwable> errorConsumer) {
        T returnResult = resultSupplier.get();
        completableFuture.whenComplete((successResult, error) -> {
            //TODO: Make this functional
            if (error != null) {
                errorConsumer.accept(returnResult, error);
            } else {
                successConsumer.accept(returnResult, successResult);
            }
        });
        return returnResult;
    }
}
