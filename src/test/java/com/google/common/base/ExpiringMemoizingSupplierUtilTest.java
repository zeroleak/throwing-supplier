package com.google.common.base;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExpiringMemoizingSupplierUtilTest {

  @Test
  public void testExpire() throws Exception {

    // supplier returning value
    Supplier supplier =
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

    // last memoized value
    Assertions.assertEquals(new Integer(2), supplier.get());

    // last memoized value
    Assertions.assertEquals(new Integer(2), supplier.get());

    // force expiry
    ExpiringMemoizingSupplierUtil.expire(supplier);

    // new computed value
    Assertions.assertEquals(new Integer(3), supplier.get());

    // last memoized value
    Assertions.assertEquals(new Integer(3), supplier.get());

    // last memoized value
    Assertions.assertEquals(new Integer(3), supplier.get());
  }
}
