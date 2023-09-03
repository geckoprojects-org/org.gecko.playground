# `org.gecko.playground.ds.dynamics`

This is an example for shows the *dynamic Reference Policy* in components.

The *ExchangeComponent* has a dynamic reference to the exchange service. It will become active, even, if the exchange service is not available.

Also, if the *ExchangeComponent* is active and the exchange service goes away, it still stays active:

```
g! Dynamic set exchange org.gecko.playground.exchange.impl.ExchangeImpl@10aa41f2
Activate dynamic exchange component
There are 0 orders
g! stop 5
Dynamic unset exchange org.gecko.playground.exchange.impl.ExchangeImpl@10aa41f2
```

Now the exchange service is not there. And the component still exists.

If we now deactivate *ExchangeComponent* an re-activate it, we will see a different log-out, because the exchange service is not available:

```
g! disable org.gecko.playground.ds.dynamics.ExchangeComponent
De-Activate dynamic exchange component
true
g! enable org.gecko.playground.ds.dynamics.ExchangeComponent
true
g! Activate dynamic exchange component
Exchange not available!
```

