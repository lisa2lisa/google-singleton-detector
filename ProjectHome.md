# Google Singleton Detector #

[Building GSD](Building.md) | [Running GSD](Usage.md) | [Known Limitations](Limitations.md) | [FAQ](FAQ.md)

The Google Singleton Detector, or GSD, is a tool which analyzes Java bytecode and detects the use of Singletons.

It's not quite as simple as that, however.  First, GSD doesn't only detect singletons; it detects four different types of global state, including singletons, hingletons, mingletons and fingletons (see the [usage section](Usage.md) for descriptions).  Second, it outputs a graph with all these different types of static state highlighted, and shows all the classes that are directly dependent on them.  The point of this tool is to allow you to see all of the uses of global state inside a project, as well as how they are all interrelated.  Hopefully you'll be able to locate global state that is heavily depended on and remove it.

But wait, why would I want to remove my global state and/or singletons?  In a nutshell, because they can make testing difficult and hide problems with your design.  Again, it's more complicated than that, so check out the [FAQ](http://code.google.com/p/google-singleton-detector/wiki/FAQ) for more info.

Great, ready to get rid of some singletons?  Head over to the [downloads](http://code.google.com/p/google-singleton-detector/downloads/list) section to get the latest release, or checkout code from the [SVN repository](http://code.google.com/p/google-singleton-detector/source) and [build](http://code.google.com/p/google-singleton-detector/wiki/Building) it yourself.

Keep in mind this is still an early build; if you find that some classes are not being labeled properly, check the known [limitations](Limitations.md) and file an [issue](http://code.google.com/p/google-singleton-detector/issues/list) if appropriate.

# Where to next? #

GSD was build in order to help identify hard to test code which is due to singletons design pattern. We now have a general form of "Testability" available here at [Testability Explorer](http://code.google.com/p/testability-explorer/). You can see a demo of Testability Explorer at http://www.testabilityexplorer.org/.
