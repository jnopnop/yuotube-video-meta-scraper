package org.nop.utils;

import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static org.nop.utils.functional.ternary.When.when;

public class AsyncUtils {

    /**
     * Convert ListenableFuture to native CompletableFuture, that is mapped onto corresponding success and error
     * callbacks.
     * @param listenableFuture target listenableFuture
     * @param <T> result type
     * @return completable future
     */
    public static <T> CompletableFuture<T> toCompletableFuture(final ListenableFuture<T> listenableFuture) {
        final CompletableFuture<T> completableFuture = new CompletableFuture<>();
        listenableFuture.addCallback(completableFuture::complete, completableFuture::completeExceptionally);
        return completableFuture;
    }

    /**
     * Obtains result from completable future which will be processed when completable future is finished.
     * @param completableFuture asynchronous computation
     * @param resultSupplier supplies the result that will be returned i.e. <em>target result</em>
     * @param successConsumer bi-function that takes <em>target result</em> and the result of async computation
     * @param errorConsumer bi-function that takes <em>target result</em> and an in instance of Throwable in case of error
     * @param <T> <em>target result</em> type
     * @param <U> async computation result type
     * @return <em>target result</em>
     */
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
