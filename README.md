### Fixing the immutables gson jar with dependencies bug
This problem occurs when:
1. We depend on multiple projects that provide Immutables objects and Gson type adapters.
2. We package our project using the maven assembly plugin `jar-with-dependencies` assembly descriptor
3. We load all `TypeAdapterFactories` using a `ServiceLoader`.

### Symptoms
The `maven-assembly-plugin` only writes to the `META-INF/services/com.google.gson.TypeAdapterFactory` using the `jar-with-dependencies` assembly descriptor for the first dependency. 
This causes the `ServiceLoader` to miss all `TypeAdapterFactories` not defined in the first dependency. 

### Quick Fix
The problem can be fixed through the maven assembly plugin. 
By default, the `jar-with-dependencies` assembly descriptor does not merge `META-INF/services` files.
By creating our own assembly descriptor we can configure the plugin to merge the files. 
We can package the custom assembly descriptor with one of the Immutables data providers and import it in the assembly plugin.

To test the project, run:
```sh
mvn clean install
java -jar gson-parser/target/gson-parser-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Permanent fixes
- Mention this issue in the immutables gson documentation.
- Immutables Gson should provide an alternative to the `ServiceLoader`, like loading the `TypeAdapterFactories` through an annotation processor.
- Immutables Gson should include fixed assembly descriptors that users can just plug into their assembly plugins.