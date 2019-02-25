package com.zeroleak.throwingsupplier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ThrowingSupplierTest {

  @Test
  public void testValue() throws Exception {

    // supplier returning value
    ThrowingSupplier<Integer, IllegalArgumentException> throwingSupplier =
        new ThrowingSupplier<Integer, IllegalArgumentException>() {
          @Override
          public Integer getOrThrow() throws IllegalArgumentException {
            return 1234;
          }
        };
    Supplier<Throwing<Integer, IllegalArgumentException>> supplier =
        Suppliers.memoize(throwingSupplier);

    // verify value returned
    Assertions.assertEquals(new Integer(1234), supplier.get().getOrThrow());
  }

  @Test
  public void testException() {

    // supplier throwing exception
    ThrowingSupplier<Integer, IllegalArgumentException> catchingSupplier =
        new ThrowingSupplier<Integer, IllegalArgumentException>() {
          @Override
          public Integer getOrThrow() throws IllegalArgumentException {
            throw new IllegalArgumentException("foo");
          }
        };
    final Supplier<Throwing<Integer, IllegalArgumentException>> supplier =
        Suppliers.memoize(catchingSupplier);

    // verify exception thrown
    Assertions.assertThrows(
        IllegalArgumentException.class,
        new Executable() {
          @Override
          public void execute() throws Throwable {
            supplier.get().getOrThrow();
          }
        },
        "foo");
  }
}
