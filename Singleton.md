# Singleton #

## Description ##
A class for which there should only be one instance in the entire system at any given time.  This program detects singletons which enforce their own singularity, which means they keep one static instance of themselves and pass it around through a static getter method.

## Criteria ##
Singletons have a public static method that returns an instance of themselves, and they have at least one private static non-final field of the same type.

## Example ##
```
public class Singleton {
  private static Singleton instance;

  public static Singleton getInstance() {
    if (instance == null) {
      instance = new Singleton();
    }
    return instance;
  }
}
```

For the purposes of detection, we are **not** interested in whether or not there is:

  * a private constructor
  * synchronized around the new instance logic

Why?  We want to be able to detect badly written Singletons too.