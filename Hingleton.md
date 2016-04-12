# Hingleton #

## Description ##
Derived from “helper singleton,” a Hingleton is a class which turns another class into a singleton by enforcing that class's singularity.  The offending class is the Hingleton, which "hingles" the other class.

## Criteria ##
Hingletons have a public static method that returns an instance of a non-primitive object, and they have at least one private static non-final field of the same type.

## Example ##
```
public class Hingleton {
  private static Foo instance;

  public static Foo getInstance() {
    if (instance == null) {
      instance = new Foo();
    }
    return instance;
  }
}
```
(Hingleton hingles Foo)

For the purposes of detection, we are not interested in whether or not there is:

  * a private constructor to either class
  * synchronized around the 'new instance' logic