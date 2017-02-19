package org.ivt.agregator.converters;

import org.ivt.agregator.entity.Event;

public interface EventConverter<T> {

    Event convert(T fromClass);
}
