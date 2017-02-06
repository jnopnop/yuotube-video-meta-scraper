package org.nop.utils;

import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static org.nop.utils.functional.ternary.When.when;

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
        completableFuture.whenComplete(
                (successResult, error) -> when(error, Objects::nonNull)
                    .then(() -> errorConsumer.accept(returnResult, error))
                    .otherwise(() -> successConsumer.accept(returnResult, successResult)));
        return returnResult;
    }
}
