### What's wrong with statics/singletons? ###

Check out [Why Singletons Are Controversial](http://code.google.com/p/google-singleton-detector/wiki/WhySingletonsAreControversial) or the [Portland Pattern Repository's Singleton page](http://c2.com/cgi/wiki?search=Singleton).

### How do I see the graph? ###

The only graph format currently supported is GraphML, which can be viewed using a program called yEd.  For more information, see [Usage](http://code.google.com/p/google-singleton-detector/wiki/Usage).

### How do I read the graph? ###

See [Understanding the Graph](UnderstandingTheGraph.md).

### What's with all these silly words? ###

Actually, we should probably apologize for that.  These were the names we started using when we were developing this app and just needed something to call them, and they've kind of stuck around.  They're not hard to pick up, and they're shorter than saying "a class that has this type of global state with these restrictions."

### Aren't `_`ingletons okay to use? ###

Well, each of these types of `_`inglton could be fine if used properly, but the problem is they're often misused.  You'll notice that we've color coded each class in a natural way; the red classes (Singletons) are most likely to be dangerous, whereas orange classes (Hingletons) are less likely to be dangerous, all the way down to green classes (Fingletons) which can often be fine.  It's up to you to decided what types of static state you really hate and what types you can live with; you can disable the detection of each type to suite your needs.

### GSD didn't detect <some class>.  Why not? ###

Since GSD is still in development, it's not perfect.  If you know a class should have been tagged as a `_`ingleton but wasn't, check the [known limitations](Limitations.md) wiki.  If your situation is not listed there, please report it as an issue and give an example of the type of class GSD is missing.  Contributions are always appreciated, so if you can fix the problem yourself you're welcome to submit a patch.