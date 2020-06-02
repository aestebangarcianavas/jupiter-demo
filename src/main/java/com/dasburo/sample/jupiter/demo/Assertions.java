package com.dasburo.sample.jupiter.demo;

import org.apache.commons.lang3.StringUtils;

/**
 * This utility class provides assertions.
 *
 * @author Ana Esteban Garcia-Navas(<a href="mailto:ags@dasburo.com">anaesteban</a>)
 */
public final class Assertions {

    private Assertions() {
    }

    /**
     * Checks that the specified object reference is not {@code null} and throws a customized
     * {@link IllegalArgumentException} if it is. This method is designed primarily for doing
     * parameter validation in methods and constructors with multiple parameters, as demonstrated
     * below:
     *
     * <blockquote>
     * <pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Assertions.isNotNull(bar, "bar must not be null");
     *     this.baz = Assertions.isNotNull(baz, "baz must not be null");
     * }
     * </pre>
     * </blockquote>
     *
     * @param obj     the object to check
     * @param message detail message to be used in the event that a IllegalArgumentException is
     *                throw
     * @param <T>     the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws IllegalArgumentException if {@code obj} is {@code null}
     */
    public static <T> T isNotNull(T obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }

    /**
     * Checks that the specified {@link String} reference is not not {@code blank } @see
     * {@link StringUtils#isBlank(CharSequence)} and throws a customized
     * {@link IllegalArgumentException} if it is. This method is designed primarily for doing
     * parameter validation in methods and constructors with multiple parameters, as demonstrated
     * below:
     *
     * <blockquote>
     * <pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = Assertions.isNotBlank(bar, "bar must not be null");
     *     this.baz = Assertions.isNotBlank(baz, "baz must not be null");
     * }
     * </pre>
     * </blockquote>
     *
     * @param obj     the object to check
     * @param message detail message to be used in the event that a IllegalArgumentException is
     *                throw
     * @return {@code obj} if not {@code empty}
     * @throws IllegalArgumentException if {@code obj} is {@code empty}
     */
    public static String isNotBlank(String obj, String message) {
        if (StringUtils.isBlank((CharSequence) obj)) {
            throw new IllegalArgumentException(message);
        }
        return obj;
    }
}
