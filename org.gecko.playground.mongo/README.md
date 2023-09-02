# `org.gecko.playground.mongo`

This project shows how to use a mongo-backed service to store and retrieve persons from an EMF model *org.gecko.playground.model*.

The console *help* shows the commands:

* `persistence:getPerson`
* `persistence:savePerson`

Here is an example:

```
g! savePerson Emil Tester

Sept. 02, 2023 12:31:33 PM com.mongodb.diagnostics.logging.JULLogger log
INFORMATION: Opened connection [connectionId{localValue:2, serverValue:13}] to mongodb:27017

Saved Person in db with id 64f30f04f4bcc54824b1b8e5

g! getPerson 64f30f04f4bcc54824b1b8e5
Retrieved Person with id 64f30f04f4bcc54824b1b8e5 from db: Emil Tester
```

This service is also used by the *playground.e4.rcp* example and the *playground.vaadin* applications as part of a more complex, re-usable part. 

