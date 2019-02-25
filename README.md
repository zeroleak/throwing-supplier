[![Build Status](https://travis-ci.org/Samourai-Wallet/boltzmann-java.svg?branch=develop)](https://travis-ci.org/Samourai-Wallet/boltzmann-java)
[![](https://jitpack.io/v/Samourai-Wallet/boltzmann-java.svg)](https://jitpack.io/#Samourai-Wallet/boltzmann-java)

# Throwing-supplier

Memoize exceptions with Guava lazy-load / cache supplier.

Exception thrown from supplier will be raised when accessing memoized data.


## Usage
See [src/test/java/com/zeroleak/throwingsupplier/JavaExample.java](src/test/java/com/zeroleak/throwingsupplier/JavaExample.java)


## Requirements
Java >= 6


## Build instructions
Build with maven:

```
cd throwing-supplier
mvn clean install -Dmaven.test.skip=true
```
