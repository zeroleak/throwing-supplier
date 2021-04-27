package com.google.common.base;

import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

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
    Assert.assertEquals(new Integer(1), supplier.get());

    // last memoized value
    Assert.assertEquals(new Integer(1), supplier.get());

    // last memoized value
    Assert.assertEquals(new Integer(1), supplier.get());

    // force expiry
    ExpiringMemoizingSupplierUtil.expire(supplier);

    // new computed value
    Assert.assertEquals(new Integer(2), supplier.get());

    // last memoized value
    Assert.assertEquals(new Integer(2), supplier.get());

    // last memoized value
    Assert.assertEquals(new Integer(2), supplier.get());

    // force expiry
    ExpiringMemoizingSupplierUtil.expire(supplier);

    // new computed value
    Assert.assertEquals(new Integer(3), supplier.get());

    // last memoized value
    Assert.assertEquals(new Integer(3), supplier.get());

    // last memoized value
    Assert.assertEquals(new Integer(3), supplier.get());
  }
}
