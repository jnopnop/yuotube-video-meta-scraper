package org.nop.web.controller;

import org.nop.utils.AsyncUtils;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

public class AbstractAsyncController {

    protected <T> DeferredResult<T> respond(final CompletableFuture<T> completableFuture) {
        return AsyncUtils.mapToAsyncResult(completableFuture, DeferredResult::new,
                DeferredResult::setResult, DeferredResult::setErrorResult);
    }
}
