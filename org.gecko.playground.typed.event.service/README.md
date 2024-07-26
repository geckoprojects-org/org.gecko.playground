# Typed Event Service

This project is dedicated to test the `TypedEventService` specifications.

## Dependencies

Used `TypedEventBus` implementation:

```
<!-- https://mvnrepository.com/artifact/org.apache.aries.typedevent/org.apache.aries.typedevent.bus -->
<dependency>
    <groupId>org.apache.aries.typedevent</groupId>
    <artifactId>org.apache.aries.typedevent.bus</artifactId>
    <version>0.0.2</version>
</dependency>

```

which at runtime requires:

```
<!-- https://mvnrepository.com/artifact/com.liferay/org.apache.aries.component.dsl -->
<dependency>
    <groupId>com.liferay</groupId>
    <artifactId>org.apache.aries.component.dsl</artifactId>
    <version>1.2.2.LIFERAY-PATCHED-1</version>
</dependency>
```

## Content

+ `EventData`: simple Java Pojo for a typed event data;
+ `EventSender`: osgi command component, which allows to send typed and untyped events to a certain topic;
+ `TypedEventHandlerImpl`: implementation of `TypedEventHandler` which listens to topic `typed/evt/service/typed`;
+ `UntypedEventHandlerImpl`: implementation of `UntypedEventHandler` which listens to topic `typed/evt/service/untyped`
+ `UnhandledEventHandlerImpl`: implementation of `UnhandledEventHandler` which logs when an event has been posted to a topic for which no handler is registered.

## Usage

### Send a Typed Event

From the gogo shell, once launched the application:

```
sendTypedEvent typed/evt/service/typed "A msg for a typed handler"
```

Expected output:

```
g! ----------------------
Received TYPED event
Message is: A msg for a typed handler
----------------------
```

### Send an Untyped Event

From the gogo shell, once launched the application:

```
sendUntypedEvent /typed/evt/service/untyped "a message for an untyped handler"
```

Expected output:

```
----------------------
Received UNTYPED event
g! Property: message  Value: a message for an untyped handler
----------------------
```

### Unhandled Event

From the gogo shell, once launched the application:

```
sendUntypedEvent not/an/interested/topic blabla
Unhandled Event Handlers are being used for event sent to topic not/an/interested/topic
g! ----------------------
Received UNHANDLED event
Property: message  Value: blabla
----------------------
```

or also `sendTypedEvent`. The important part is that the topic is not one of those registered by the handlers.

Expected output:

```
Unhandled Event Handlers are being used for event sent to topic not/an/interested/topic
----------------------
g! Received UNHANDLED event for topic not/an/interested/topic
Property: message  Value: blabla
----------------------
```

