# aQute Tracing

[Github](https://github.com/aQute-os/biz.aQute.osgi.util/tree/master/biz.aQute.trace)
[Maven](https://repo1.maven.org/maven2/biz/aQute/biz.aQute.trace.runpath/)

This is a small tool that creates metrics for typical startup and teardown methods:

* Activator#start 
* Activator#stop
* Component#activate
* Component#deactivate

## In bndtools

Just follow the instruction on Github.

## Non bnd-Environment

In non-bnd environment there is the *org.gecko.playground.tracing.fragement*. It should be placed in the application. This is a Framework frageent, means a fragment for the system bundle.
Its activator should be called as well.

Refer to that project

## Gogo Commands

Further, to get some Gogo commands, the bundle *biz.aQute.trace.gui* can also be placed in the application (startlevel is unimportant here) you can then use the commands:

* *tracer* - show table of the measurements in the console
* *dump (html | json | text)* - create a currensponding dump in the console

E.g. the HTML dump creates a HTML page with all results.