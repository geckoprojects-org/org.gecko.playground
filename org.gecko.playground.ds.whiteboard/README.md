# `org.gecko.playground.whiteboard`

This tutorial shows how to work withthe whiteboard pattern.

This example uses the following other projects:

* *org.gecko.playground.exchange.api* - To get the *ExchangeListener* API
* *org.gecko.playground.exchange.impl* - To run the application
* *org.gecko.playground.exchange.client* - To run the Gogo commands to ask for an order

So, the client provides a command `askOrder <symbol> <int:amount> <long:price>`.

When placing an order, they are forwarded to the *Exchange*. 

We register the *LoggingExchangeListener* and the *SysoutExchangeListener* as service. The *ExchangeImpl*  injects all service of this abstract class *ExchangeListener*. Each time a new order is placed, all injected *ExchangeListener* are notified using the corresponding callback.

When you run the application ans placing an order, the console printsout something like this:

```
g! ConsoleLog created

g! askOrder TUT 123 222333
Add listener to exchange: LoggingExchangeListener@360c2b09
Add listener to exchange: SysoutExchangeListener@26f7f0a4
Ask for order of 123 for TUT at the price of 222333 cent.
Order was submitted ...

LOG : Log Exchange Listener: Order submittedOrder [id=08a3f8cb-5186-4d08-9916-ef30b3ab0978, customerId=0, symbol=TUT, side=Ask, quantity=123, initialQuantity=123, price=222333, expiry=0]

Sysout: Order submittedOrder [id=08a3f8cb-5186-4d08-9916-ef30b3ab0978, customerId=0, symbol=TUT, side=Ask, quantity=123, initialQuantity=123, price=222333, expiry=0]

```

As we see the two exchange listeners have been notified by the exchange.

