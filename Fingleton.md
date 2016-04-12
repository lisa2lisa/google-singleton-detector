# Fingleton #

## Description ##
Derived from "field singleton,” a Fingleton is a class which contains static fields.  Many will consider these edge cases and not want to report on them.

## Criteria ##
Fingletons have at least one public static field.

## Example ##
```
public class Fingleton {
  public static Foo instance;
}
```

If the _public static_ were for a field of type int or a String, and it were final then this would be a constant (and harmless)

If it were public static and final and making a complex object, then it is not clear whether its harmless or not.  The complexity of the class is important, but were making a blanket assumption here - Foo (as above) _could_ _be_ a very stateful and method-packed class.  If it is then a system that is engineered in this way is no better than one that has fist-class singletons.