package com.zeroleak.throwingsupplier;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

public class JavaExample {

  public void example() throws Exception {

    // compute memoized data with ThrowingSupplier
    ThrowingSupplier<Object, Exception> catchingSupplier =
        new ThrowingSupplier<Object, Exception>() {
          @Override
          public Object getOrThrow() throws Exception {

            if (true) {
              // you can throw exception directly from supplier
              throw new Exception();
            }

            // return data to be memoized
            return "data";
          }
        };

    // use supplier with Guava
    Supplier<Throwing<Object, Exception>> supplier = Suppliers.memoize(catchingSupplier);

    // get memoized value
    supplier.get().getOrThrow(); // will return "data" or throw Exception
  }
}
