# `org.gecko.playground.ds`

This tutorial shows how to work with OSGi *Declarative Service* and how many cool things you can do with them.

## Configurable Services (`org.gecko.plaground.ds.config`)

First of all you can make your service *configurable*, meaning you can provide a set of properties that will customize the instance of your service, without having to repeat the common logic. 

As soon as the configuration is modified, or even removed, the service instance associated to it will be modified or removed accordingly. 

The configuration can be defined in a simple `.json` file, and can then be injected in the activate/modified/deactivate methods, so you have access to it.



## Component Factory (`org.gecko.playground.ds.factory`)



## Greedy Policy (`org.gecko.playground.ds.greedy`)



## Prototype Services (`org.gecko.playground.ds.prototype`)

By default, when you define a service, this will be a singleton, meaning, every time you want to reference to that service within the lifetime of an application, you will get the same instance.

If instead you want to get a new instance every time, you can define your service as a *prototype*. 

