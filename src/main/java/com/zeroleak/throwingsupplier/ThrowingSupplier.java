package com.zeroleak.throwingsupplier;

import com.google.common.base.Supplier;

public abstract class ThrowingSupplier<T, E extends Exception> implements Supplier<Throwing<T, E>> {
  public abstract T getOrThrow() throws Exception;

  @Override
  public Throwing<T, E> get() {
    try {
      T value = getOrThrow();
      return new Throwing<T, E>(value);
    } catch (Exception e) {
      return new Throwing(e);
    }
  }
}
