# Building GSD #

Building GSD currently requires [ant 1.7](http://ant.apache.org/) and [Java 1.5](http://java.sun.com/javase/downloads/index.jsp).

To build the project, enter `ant` into the directory you unzipped the source
to.  This will create the directory `target` with a gsd-X.X.X.zip file inside.
Other ant commands:

```
  ant clean         - Remove generate files
  ant compile       - Build source
  ant compile-tests - Build tests
  ant test          - Build and run tests
  ant jar           - Build the distributable jar
  ant zip           - Build the distributable zip
```

For more information on how to run the program, see [Usage](Usage.md).