# Development instructions


## Build
Build with maven:

```
cd throwing-supplier
mvn clean install -Dmaven.test.skip=true
```

## Release

```
mvn release:prepare
mvn release:perform -Darguments="-Dmaven.deploy.skip=true"
```

This will:
- increment module version
- tag and upload release on GitHub
- NOT upload it to any maven repository, it's already available on JitPack.
