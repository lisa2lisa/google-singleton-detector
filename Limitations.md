# Limitations #

Google Singleton Detector is still in its early stages, and has a few limitations at the moment.  If you find new limitations in your own use, please add them to this list.

## Final Singletons/Hingletons ##

To detect a singleton, GSD looks for a private static non-final field of the class as well as a public static method which returns the class.  Specifically, we don't look at final fields in an effort to avoid tagging "magic values" as singletons, as most singletons are initialized in the getInstance() method (and therefore can't be final).  However, in some cases programmers do make singletons final and initialize them in a constructor or static initializer.  An example from hibernate-3.2:

```
public class SessionFactoryObjectFactory implements ObjectFactory {
  private static final SessionFactoryObjectFactory INSTANCE; //to stop the class from being unloaded
  private static final Log log;

  static {
    log = LogFactory.getLog(SessionFactoryObjectFactory.class);
    INSTANCE = new SessionFactoryObjectFactory();
    log.debug("initializing class SessionFactoryObjectFactory");
  }
}
```

where `SessionFactoryObjectFactory` is not tagged as a singleton because `INSTANCE` is final.  The same logic handles hingletons, so a final hingleton field will also be missed.

## Nested Singletons ##

Since we currently look for fields of the same type as the class they're in, we don't catch instances where the singleton is nested inside of another class, like a inner-class.  An example from jetty-5.1.4rc1:

```
public class IO extends BoundedThreadPool {
  private static class Singleton {
    static final IO __instance=new IO();
    static {
      try{__instance.start();}
      catch(Exception e){Log.warn(e); System.exit(1);}
    }
  }

  public static IO instance() {
    return Singleton.__instance;
  }
}
```

This isn't tagged as a singleton because the field is of a type `Singleton`, not `IO`.  It also isn't tagged as a hingleton, because instance() returns `IO`, not 'Singleton'.