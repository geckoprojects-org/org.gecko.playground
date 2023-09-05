# aQute Tracing System Fragment

[Github](https://github.com/aQute-os/biz.aQute.osgi.util/tree/master/biz.aQute.trace)
[Maven](https://repo1.maven.org/maven2/biz/aQute/biz.aQute.trace.runpath/)

This is a small tool that creates metrics for typical startup and teardown methods:

* Activator#start 
* Activator#stop
* Component#activate
* Component#deactivate

This is an example how to repackage it and making it a framework fragment

## The Framework Fragment

The *org.gecko.playground.tracing.fragment* be placed in the application. This is a Framework fragment. This means a fragment for the system bundle.
Its activator should be called as well. So with this the tracer is started very early.

It is important to know, that this fragment does not import foreign packages, except `org.osgi.framework`. It is also not allowed to export something. 


## Gogo Commands

Further, to get some Gogo commands, the bundle *biz.aQute.trace.gui* can also be placed in the application (startlevel is unimportant here) you can then use the commands:

* *tracer* - show table of the measurements in the console
* *dump (html | json | text)* - create a currensponding dump in the console

E.g. the HTML dump creates a HTML page with all results.

The *bndrun* shows, how to start it.