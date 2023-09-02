# `org.gecko.playground.xslt`

This project shows how to use XSLT transformation with different implementations.

The *launch-saxon.bndrun* uses Saxon as engine *org.gecko.playground.saxon*, whereas the *launch.bndrun* uses the default transformator factory.

The *ExampleXSLT* component shows how to deal with certain *Java - ServiceLoader* incompatibilities. 

This goes back to the Jakarta API's, that have no consistent internal search order over all API's. This leads to the situation, like in this case, that implementations can just be found in a monolithic classpath and not out of the box in OSGi.

JAXB as example, can be found in OSGi. 
