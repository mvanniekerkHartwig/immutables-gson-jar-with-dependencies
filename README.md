### Minimal reproduction of immutables gson jar with dependencies bug
When using multiple dependencies that all contain Immutable and Gson TypeAdapter annotated interfaces
the `META-INF/services/com.google.gson.TypeAdapterFactory` file is only written for the first dependency. 

To reproduce, run:
```sh
mvn clean install
java -jar gson-parser/target/gson-parser-1.0-SNAPSHOT-jar-with-dependencies.jar
```

The problem is caused by `META-INF/services` not merging the `TypeAdapterFactory` services, but only copying it from dependency #1.
`gson-parser/target/gson-parser-1.0-SNAPSHOT-jar-with-dependencies.jar!/META-INF/services/com.google.gson.TypeAdapterFactory`
has the following contents:

```
com.hartwig.mvn.immutables.GsonAdaptersDataOne
```

But I would expect it to have this:

```
com.hartwig.mvn.immutables.GsonAdaptersDataOne
com.hartwig.mvn.immutables.GsonAdaptersDataTwo
```