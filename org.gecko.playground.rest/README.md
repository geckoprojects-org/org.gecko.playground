# `org.gecko.playground.rest`

This project shows how to use Jakarta REST resources (a.k.a. JaxRS). 

It contains a JakartaRS resoure *ExampleResource* as component. There is also an plain Java JakartaRS resource *SimpleResource*, which is then programmatically registered into the OSGi JakartaRS Whiteboard using e.g. *SimpleResourceStarter*

The URLs are like this:

* *ExampleResource* - [http://localhost:8080/playground/rest/example](http://localhost:8080/playground/rest/example)
* *SimpleResource* - [http://localhost:8080/playground/rest/test/example](http://localhost:8080/playground/rest/test/example)

You can check that over the browser or using:

`wget http://localhost:8080/playground/rest/test/example`

`wget http://localhost:8080/playground/rest/example`

... returns a *HTTP 200 - OK*

You can dynamically unregister the *SimpleResource* by deactivating the *SimpleResourceStarter* (The name of the component is *SRS*, see the annotation). After that the *SimpleResource* will become unavailable.

```
g! disable SRS
```

A `wget http://localhost:8080/playground/rest/test/example` will now return *HTTP 404 - Not Found*

Re-aenabling it makes it appear again.
```
g! enable SRS
```


