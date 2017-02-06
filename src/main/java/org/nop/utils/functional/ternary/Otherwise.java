package org.nop.utils.functional.ternary;

@FunctionalInterface
public interface Otherwise {

    void otherwise(Action consumer);
}
