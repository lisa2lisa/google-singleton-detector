# Mingleton #

## Description ##
Derived from "method singleton,” a Mingleton is a class which has any static method that returns some state without taking any parameters.  Many will consider these edge cases and not want to report on them.

## Criteria ##
Mingletons have a public static method that takes no parameters and returns a non-primitive object.

## Example ##
```
public class Mingleton {
  public static Foo getFoo() {
    return Foo.getDefault(); 
    // or return new Foo();
  }
}
```