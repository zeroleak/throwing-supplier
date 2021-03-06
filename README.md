[![Build Status](https://travis-ci.org/zeroleak/throwing-supplier.svg?branch=master)](https://travis-ci.org/zeroleak/throwing-supplier)
[![](https://jitpack.io/v/zeroleak/throwing-supplier.svg)](https://jitpack.io/#zeroleak/throwing-supplier)


# Throwing-supplier
Guava cache supplier extension to memoize exceptions, fallback, expiration control.

## ThrowingSupplier
Use ```ThrowingSupplier``` to memoize exceptions with Guava lazy-load / cache supplier, when failing after N attempts.

Exception thrown from your supplier will be raised when accessing memoized data.


## LastValueFallbackSupplier
Use ```LastValueFallbackSupplier``` for to memoize last non-throwing value when exception are raised by your supplier.

Exception thrown from your supplier will be catched when accessing memoized data, and last non-throwing value will be returned. If there is no such value, Exception will be raised.

## ExpiringMemoizingSupplierUtil
Force ExpiringMemoizingSupplier expiration.



## Install
Install with Maven or Gradle from [JitPack](https://jitpack.io/#zeroleak/throwing-supplier)

## Usage
See [JavaExample.java](src/test/java/com/zeroleak/throwingsupplier/JavaExample.java)


## Requirements
Java >= 6

