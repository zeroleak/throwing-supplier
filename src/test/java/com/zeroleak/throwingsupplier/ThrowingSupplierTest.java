package com.zeroleak.throwingsupplier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.concurrent.TimeUnit;
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

  @Test
  public void testValueAttempts() throws Exception {

    // supplier returning value
    ThrowingSupplier<Integer, IllegalArgumentException> throwingSupplier =
        new ThrowingSupplier<Integer, IllegalArgumentException>() {
          private boolean initial = true;

          @Override
          public Integer getOrThrow() throws IllegalArgumentException {
            if (initial) {
              initial = false;
              throw new IllegalArgumentException("initial attempt fails");
            }
            return 1234;
          }
        };
    Supplier<Throwing<Integer, IllegalArgumentException>> supplier =
        Suppliers.memoize(throwingSupplier.attempts(2));

    // verify value returned
    Assertions.assertEquals(new Integer(1234), supplier.get().getOrThrow());
  }

  @Test
  public void testLastValueFallback() throws Exception {

    // supplier returning value
    ThrowingSupplier<Integer, IllegalArgumentException> throwingSupplier =
        new LastValueFallbackSupplier<Integer, IllegalArgumentException>() {
          private boolean first = true;

          @Override
          public Integer getOrThrow() throws IllegalArgumentException {
            if (first) {
              System.out.println("returning value");
              first = false;
              return 1234;
            } else {
              System.out.println("returning exception");
              // you can throw exception directly from supplier
              throw new IllegalArgumentException("foo");
            }
          }
        };

    Supplier<Throwing<Integer, IllegalArgumentException>> supplier =
        Suppliers.memoizeWithExpiration(throwingSupplier, 1, TimeUnit.MICROSECONDS);

    // verify value returned
    Assertions.assertEquals(new Integer(1234), supplier.get().getOrThrow());

    synchronized (this) {
      wait(1000);
    }
    Assertions.assertEquals(new Integer(1234), supplier.get().getOrThrow());
  }

  @Test
  public void testLastValueFallbackAttempts() throws Exception {

    // supplier returning value
    ThrowingSupplier<Integer, IllegalArgumentException> throwingSupplier =
        new LastValueFallbackSupplier<Integer, IllegalArgumentException>() {
          private boolean initial = true;
          private boolean first = true;

          @Override
          public Integer getOrThrow() throws IllegalArgumentException {
            if (initial) {
              initial = false;
              throw new IllegalArgumentException("initial attempt fails");
            }
            if (first) {
              System.out.println("returning value");
              first = false;
              return 1234;
            } else {
              System.out.println("returning exception");
              // you can throw exception directly from supplier
              throw new IllegalArgumentException("foo");
            }
          }
        };

    Supplier<Throwing<Integer, IllegalArgumentException>> supplier =
        Suppliers.memoizeWithExpiration(throwingSupplier.attempts(2), 1, TimeUnit.MICROSECONDS);

    // verify value returned
    Assertions.assertEquals(new Integer(1234), supplier.get().getOrThrow());

    synchronized (this) {
      wait(1000);
    }
    Assertions.assertEquals(new Integer(1234), supplier.get().getOrThrow());
  }
}
