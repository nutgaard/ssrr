package no.utgdev.ssrr.filter;


import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuadFunction<S, T, U, V, R> {
    R apply(S s, T t, U u, V v);

    default <W> QuadFunction<S, T, U, V, W> andThen(Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (S s, T t, U u, V v) -> after.apply(apply(s, t, u, v));
    }
}
