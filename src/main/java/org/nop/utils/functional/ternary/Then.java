package org.nop.utils.functional.ternary;

@FunctionalInterface
public interface Then {

    Otherwise then(Action consumer);
}
