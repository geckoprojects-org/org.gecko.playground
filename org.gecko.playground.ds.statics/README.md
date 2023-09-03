# `org.gecko.playground.ds.statics`

This tutorial shows how to work with OSGi *Declarative Services* and static references.

The example is to play with the optional reference cardinality.

We have two Components:

* *ExchangeCommand* - Gogo Command **without optional** *Exchange*-service reference: `orders`
* *ExchangeComponent* - Component **with optional** cardinality

When running the application, the console prints:

```
g! Set exchange org.gecko.playground.exchange.impl.ExchangeImpl@1f59a598
Activate exchange component
There are 0 orders
```

If you now execute the `orders` command:

```
g! orders
Order Command: Set exchange org.gecko.playground.exchange.impl.ExchangeImpl@1f59a598
Activate order command 
There are 0 orders
De-Activate order command 
Order Command: Unset exchange org.gecko.playground.exchange.impl.ExchangeImpl@1f59a598
g! 
```

If we now disable the exchange component service:

```
g! disable org.gecko.playground.exchange.impl.ExchangeImpl
true
g! De-Activate exchange component
Unset exchange org.gecko.playground.exchange.impl.ExchangeImpl@1f59a598
Activate exchange component
Exchange not available!
g! list
org.gecko.playground.exchange.impl.ExchangeImpl in bundle 5 (org.gecko.playground.exchange.impl:1.0.0.202309021301-SNAPSHOT) enabled, 0 instances.
org.gecko.playground.ds.statics.ExchangeCommand in bundle 6 (org.gecko.playground.ds.statics:1.0.0.202309031406-SNAPSHOT) enabled, 1 instance.
    Id: 1, State:UNSATISFIED REFERENCE
org.gecko.playground.ds.statics.ExchangeComponent in bundle 6 (org.gecko.playground.ds.statics:1.0.0.202309031406-SNAPSHOT) enabled, 1 instance.
    Id: 2, State:ACTIVE
```

... we see, when executing the `list` , that the *ExchangeCommand* **without** optional cardinality the references to the *Exchange*-service are *unsatisfied*.

Executing `orders`will not work anymore:

```
g! orders
gogo: CommandNotFoundException: Command not found: orders
```



The *ExchangeComponent* ***with** optional cardinality has been de-activated and activated again, but now without *Exchange*. Thats why it say exchange not available:

```
g! orders
Activate order command 
Exchange not available!
De-Activate order command 
```

Lets say, the *Exchange*-service comes back:

```
g! enable org.gecko.playground.exchange.impl.ExchangeImpl
true
g! list
org.gecko.playground.exchange.impl.ExchangeImpl in bundle 5 (org.gecko.playground.exchange.impl:1.0.0.202309021301-SNAPSHOT) enabled, 1 instance.
    Id: 3, State:SATISFIED
org.gecko.playground.ds.statics.ExchangeCommand in bundle 6 (org.gecko.playground.ds.statics:1.0.0.202309031406-SNAPSHOT) enabled, 1 instance.
    Id: 1, State:SATISFIED
org.gecko.playground.ds.statics.ExchangeComponent in bundle 6 (org.gecko.playground.ds.statics:1.0.0.202309031406-SNAPSHOT) enabled, 1 instance.
    Id: 2, State:ACTIVE
```

Every references are satisfied again, so the `orders` command will work:

```
g! orders
Order Command: Set exchange org.gecko.playground.exchange.impl.ExchangeImpl@2a88c3ec
Activate order command 
There are 0 orders
De-Activate order command 
Order Command: Unset exchange org.gecko.playground.exchange.impl.ExchangeImpl@2a88c3ec
```

But we **do not** see, that the *ExchangeComponent* **with** optional cardinality is de-activated and activated again, with the *Exchange* reference, that came back. There is **no** printout like from the beginning 

```
Set exchange org.gecko.playground.exchange.impl.ExchangeImpl@1f59a598
Activate exchange component
There are 0 orders
```

Instead the *ExchangeComponent* stays active **without** re-injecting the *Exchange* service.

An investigation leads to this:

Before deactivating the *Exchange*, the `info` command for the *ExchangeComponent* provided one reference binding. After disabling the *Exchange* and enabling it again, the `info` command for the *ExchangeComponent* still says **no active binding** for *Exchange* reference binding:

```
g! info 2
...
Component Configuration Id: 2
-----------------------------
...
References:   (total 2)
  - Exchange: org.gecko.playground.exchange.api.Exchange SATISFIED 0..1 static
    target=(*) scope=bundle (1 binding):
    * Bound to [22] from bundle 5 (org.gecko.playground.exchange.impl:1.0.0.202309021301-SNAPSHOT)
  - osgi.ds.satisfying.condition: ...
  
g! disable org.gecko.playground.exchange.impl.ExchangeImpl
true
g! De-Activate exchange component
Unset exchange org.gecko.playground.exchange.impl.ExchangeImpl@2e377400
Activate exchange component
Exchange not available!

g! enable org.gecko.playground.exchange.impl.ExchangeImpl
true
g! info 2
Component Configuration Id: 2
-----------------------------
...
References:   (total 2)
  - Exchange: org.gecko.playground.exchange.api.Exchange SATISFIED 0..1 static
    target=(*) scope=bundle (no active bindings)
  - osgi.ds.satisfying.condition: ...
```

To get the *Exchange* back, you'd need the dynamic reference policy.
