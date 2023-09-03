# `org.gecko.playground.ds.greedy`

This tutorial shows how to work with OSGi *Declarative Service* references using greediness.

The *GreedyConsumer* references to an *Important*-Service. We have the less important *ImportantService* implementation and the *VeryImportantService* implementation, that is disabled.

So on start you see that the *ImportantService* was referenced:

```
Welcome to Apache Felix Gogo

g! Activate Greedy Consumer
Set less important service: true, very: false from org.gecko.playground.ds.greedy.impl.ImportantService@d35dea7
```



If you activate the *VeryImportantService* you see, that because of the defined service rank, this instance is more important. The greediness configuration should now take the better version:

```
g! enable VIS
true
g! Set VERY important service: true, very: true from org.gecko.playground.ds.greedy.very.VeryImportantService@4396e1f0
Unset less important service: true, very: false from org.gecko.playground.ds.greedy.impl.ImportantService@d35dea7
```

At first the new instance was set. Afterwards, the old instance has been unsetted.
