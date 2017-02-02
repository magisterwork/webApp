package org.ivt.agregator.filter;

public interface AddingFilter<T> {

    boolean shouldAdd(T event);
}
