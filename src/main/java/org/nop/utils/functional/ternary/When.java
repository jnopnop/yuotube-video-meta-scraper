package org.nop.utils.functional.ternary;


import java.util.function.Predicate;

public class When<T> implements Then, Otherwise {

    private final boolean result;
    private Action targetAction;

    protected When(final T data, final Predicate<T> condition) {
        result = condition.test(data);
    }

    public static <T> Then when(final T data, final Predicate<T> p) {
        return new When<>(data, p);
    }

    @Override
    public Otherwise then(final Action then) {
        if (result) {
            targetAction = then;
        }
        return this;
    }

    @Override
    public void otherwise(final Action otherwise) {
        if (targetAction == null) {
            targetAction = otherwise;
        }
        targetAction.execute();
    }

}
