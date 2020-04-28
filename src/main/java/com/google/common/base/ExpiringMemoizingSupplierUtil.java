package com.google.common.base;

public class ExpiringMemoizingSupplierUtil {

    /**
     * Force a ExpiringMemoizingSupplier to expire now.
     */
    public static void expire(Supplier supplier) {
        ((Suppliers.ExpiringMemoizingSupplier)supplier).expirationNanos = 0;
    }

}
