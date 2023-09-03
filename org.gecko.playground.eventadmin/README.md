# `org.gecko.playground.eventadmin`

This tutorial shows how the Event Admin service works.

The *RandomPriceFeed* component gets the *EventAdmin* injected and posts events of stock information.

The *OrderEventHandler* subscribes to that topic, using the component service property `event.topics` and prints-out the order and submits it to the *Exchange*.

* Once you add the *org.gecko.playground.exchange.client* bundle to the run-configuration, you can verify the amount of order using the `orders` command.
* It is also possible to add the *org.gecko.playground.ds.whiteboard* bundle to add some *ExchangeListener* to the *Exchange* ;-)

