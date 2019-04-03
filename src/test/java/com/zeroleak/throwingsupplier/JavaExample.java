package com.zeroleak.throwingsupplier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

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

    // use supplier with Guava
    Supplier<Throwing<Object, Exception>> supplier = Suppliers.memoize(throwingSupplier);

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

    // use supplier with Guava
    Supplier<Throwing<Object, Exception>> supplier = Suppliers.memoize(throwingSupplier);

    // get memoized value
    supplier.get().getOrThrow(); // will return "data"
    supplier
        .get()
        .getOrThrow(); // will return "data" even though ThrowingSupplier returned an exception
    // (fallback to last non-throwing value)
  }
}
