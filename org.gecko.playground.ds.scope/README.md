# `org.gecko.playground.ds.proto`

This tutorial shows how to work with OSGi *Declarative Service* using scoped services.

As example there are two counter components for singleton services and two counter components for protoytpe services.

Running the application prints something like this:

```
Counter 1: 1 with instance org.gecko.playground.ds.scope.CounterService@3be8df3
Counter 1: 2 with instance org.gecko.playground.ds.scope.CounterService@3be8df3
Counter 1: 3 with instance org.gecko.playground.ds.scope.CounterService@3be8df3
Counter 2: 4 with instance org.gecko.playground.ds.scope.CounterService@3be8df3
Counter 2: 5 with instance org.gecko.playground.ds.scope.CounterService@3be8df3
Counter 2: 6 with instance org.gecko.playground.ds.scope.CounterService@3be8df3
Proto Counter 1: 1 with instance CounterImpl@12933a4
Proto Counter 1: 2 with instance CounterImpl@12933a4
Proto Counter 1: 3 with instance CounterImpl@12933a4
Proto Counter 2: 1 with instance CounterImpl@50bd50fd
Proto Counter 2: 2 with instance CounterImpl@50bd50fd
Proto Counter 2: 3 with instance CounterImpl@50bd50fd
```

The two singleton counter components 1 and 2  share the same counter service instance.

The proto counter 1 and 2 getting an own instance of the *CounterImpl*, that is registered as prototype servicec factory in the *CounterPrototypeCreator*.
