# Java Swing AlphaSlider

An AlphaSlider enables users to select from a list of values, which may or may not be numeric. This is in contrast to the Java Swing JSlider class that deals solely with numeric values.

## Building

Use the following command to build the AlphaSlider with Maven:

```
mvn clean compile
```

To create a JAR file, use this command:

```
mvn package
```

If you want to add the AlphaSlider JAR to your Maven repository, use this command:

```
mvn install
```

## Testing

Use this command to run the project's JUnit tests:

```
mvn test
```

## JavaDoc

After building the project, use the following command to generate the JavaDoc for the AlphaSlider:

```
mvn javadoc:javadoc
```

Maven will place the JavaDoc in the 'target/site/apidocs’ folder.
