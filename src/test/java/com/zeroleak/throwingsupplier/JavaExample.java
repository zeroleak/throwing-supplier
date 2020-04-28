package com.zeroleak.throwingsupplier;

import com.google.common.base.ExpiringMemoizingSupplierUtil;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;

public class JavaExample {

  public void throwingExample() throws Exception {

    // compute memoized data with ThrowingSupplier
    ThrowingSupplier<Object, Exception> throwingSupplier =
        new ThrowingSupplier<Object, Exception>() {
          private boolean first = true;

          @Override
          public Object getOrThrow() throws Exception {
            if (first) {
              first = false;
              return "data";
            } else {
              // you can throw exception directly from supplier
              throw new Exception();
            }
          }
        };

    // use supplier with Guava, 2 attempts
    Supplier<Throwing<Object, Exception>> supplier =
        Suppliers.memoize(throwingSupplier.attempts(2));

    // get memoized value
    supplier.get().getOrThrow(); // will return "data"
    supplier.get().getOrThrow(); // will throw Exception
  }

  public void lastValueFallbackExample() throws Exception {

    // compute memoized data with LastValueFallbackThrowingSupplier
    ThrowingSupplier<Object, Exception> throwingSupplier =
        new LastValueFallbackSupplier<Object, Exception>() {
          private boolean first = true;

          @Override
          public Object getOrThrow() throws Exception {
            if (first) {
              first = false;
              return "data";
            } else {
              // you can throw exception directly from supplier
              throw new Exception();
            }
          }
        };

    // use supplier with Guava, 2 attempts
    Supplier<Throwing<Object, Exception>> supplier =
        Suppliers.memoize(throwingSupplier.attempts(2));

    // get memoized value
    supplier.get().getOrThrow(); // will return "data"
    supplier
        .get()
        .getOrThrow(); // will return "data" even though ThrowingSupplier returned an exception
    // (fallback to last non-throwing value)
  }

  public void expireExample() {
    Supplier<Integer> supplier =
        Suppliers.memoizeWithExpiration(
            new Supplier<Integer>() {
              private int value = 1;

              @Override
              public Integer get() throws IllegalArgumentException {
                return value++;
              }
            },
            1,
            TimeUnit.HOURS);

    // new computed value
    Assertions.assertEquals(new Integer(1), supplier.get());

    // last memoized value
    Assertions.assertEquals(new Integer(1), supplier.get());

    // last memoized value
    Assertions.assertEquals(new Integer(1), supplier.get());

    // force expiry
    ExpiringMemoizingSupplierUtil.expire(supplier);

    // new computed value
    Assertions.assertEquals(new Integer(2), supplier.get());
  }
}
