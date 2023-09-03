# `org.gecko.playground.exchange.impl`

Simple *Exchange* implementation.

The *ExchangeImpl* injects all *ExchangeListeners* in therefor acts as a whiteboard. All listeners are notified, when a new order arrives or was removed.
