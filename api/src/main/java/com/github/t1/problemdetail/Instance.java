package com.github.t1.problemdetail;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated method or field is used for the <code>instance</code> field of the problem detail.
 * Note that this value should be different for every occurrence.
 * <p>
 * By default, an <code>URN</code> with <code>urn:uuid:</code> and a random {@link java.util.UUID}
 * is generated, that's also logged with the stack trace.
 */
@Retention(RUNTIME)
@Target({METHOD, FIELD})
public @interface Instance {}
